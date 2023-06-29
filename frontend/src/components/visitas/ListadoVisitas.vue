<script>
import Modelo from '@/components/Model.vue'
import Visita from './Visita.vue'
import { mapActions, mapState } from 'pinia'
import { visitasStore } from '@/stores/visitas'
import ConfirmDialog from 'primevue/confirmdialog'
import Toast from 'primevue/toast'
import { useToast } from 'primevue/usetoast'
import ProgressSpinner from 'primevue/progressspinner'

export default {
  components: { Modelo, Visita, ConfirmDialog, Toast, ProgressSpinner },   ///// registro local de los componentes
  data() {
    return {
      ordenAscendente: true,
      ordenarPor: 'fechaInicio',
      filtroPendiente: true,
    }
  },
  computed: {
    ...mapState(visitasStore, ['visitasApi', 'visitasPendientesApi']),
    visitasParaMostrar() {
      return this.filtroPendiente ? this.visitasPendientes : this.visitasGlobales
    },
    visitasPendientes() {
      if (this.visitasApi) {
        let fechaSistema = new Date()
        let listaPendiante = this.visitasApi.filter(v => new Date(v.fechaFin) >= fechaSistema)
        if (this.ordenAscendente) {
          return listaPendiante.sort((a, b) => { return new Date(a[this.ordenarPor]) - new Date(b[this.ordenarPor]) })
        }
        else {
          return listaPendiante.sort((a, b) => { return new Date(b[this.ordenarPor]) - new Date(a[this.ordenarPor]) })
        }
      }
      else
        return []
    },
    visitasGlobales() {
      if (this.visitasApi) {
        if (this.ordenAscendente) {
          return this.visitasApi.sort((a, b) => { return new Date(a[this.ordenarPor]) - new Date(b[this.ordenarPor]) })
        }
        else {
          return this.visitasApi.sort((a, b) => { return new Date(b[this.ordenarPor]) - new Date(a[this.ordenarPor]) })
        }
      }
      else
        return []
    },
  },
  methods: {
    ...mapActions(visitasStore, ['getVisitasApi', 'deleteVisita']),
    sortByStartDate() {
      this.ordenarPor = 'fechaInicio'
      this.ordenAscendente = !this.ordenAscendente
    },
    sortByActivity() {
      this.ordenarPor = 'actividad'
      this.ordenAscendente = !this.ordenAscendente // Cambiar el orden ascendente/descendente
      const orden = this.ordenAscendente ? 1 : -1 // Determinar el orden ascendente o descendente
      return this.visitasParaMostrar.sort((a, b) => {
        if (a[this.ordenarPor] < b[this.ordenarPor]) return -1 * orden
        if (a[this.ordenarPor] > b[this.ordenarPor]) return 1 * orden
        return 0
      })
    },
    borrarVisita(visita) {
      this.$confirm.require({
        message: '¿Está seguro de borrar la visita número ' + visita.id + ' con sus invitados?',
        header: 'Confirmación de borrado',
        icon: 'pi pi-info-circle',
        acceptClass: 'p-button-danger',
        acceptLabel: 'Sí',
        accept: () => {
          this.deleteVisita(visita).then((r) => {
            let indexToRemove = this.visitasApi.indexOf(visita)
            this.visitasApi.splice(indexToRemove, 1)
          })
            .catch((error) => {
              console.error("A la hora de borrar la visita, Se ha producido un error : ", error)
            })
          this.$toast.add({ severity: 'success', summary: 'Borrado', detail: visita.id, life: 3000 })
        },
        reject: () => {
          this.$toast.add({ severity: 'error', summary: 'Borrado', detail: 'Cancelado', life: 3000 })
        }
      })
    },
    editarVisita(visita) {
      this.$router.push({ name: 'modificarvisita', params: { identificador: visita.id } })
    },
    mostrarAnfitrion(visita) {
      //// recuperar el ID del anfitrion para pasarlo como parametro a la ruta
      let array = visita._links.anfitrion.href.split('/')
      let idAnfitrion = array[array.length - 1]
      this.$router.push({ name: 'personainfo', params: { identificador: idAnfitrion } })
    }
  },
  created() {
    this.toast = useToast()
    this.getVisitasApi()
    // console.log('visitasApi JSON', JSON.stringify(this.visitasApi, null, 2))
  }
}
</script>

<template>
  <Modelo titulo="LISTADO VISITAS">
    <Toast />
    <ConfirmDialog></ConfirmDialog>
    <div class="container">
      <div class="row">
        <div class="col-md-3">
          <label class="radio-inline me-5 fs-5 fw-bold pt-2 tipo_visita">
            <input class="me-2" type="radio" name="optradio" checked @click="filtroPendiente = true">PENDIENTES
          </label>
          <label class="radio-inline me-4 fs-5 pt-2 fw-bold tipo_visita">
            <input class="me-2" type="radio" name="optradio" @click="filtroPendiente = false">GLOBAL
          </label>
        </div>
        <div class="col-md-6 d-flex justify-content-center p-3 mb-3">
          <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
            <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off"
              @click="sortByStartDate" checked>
            <label class="btn btn-outline-info mt-1 fw-bold" for="btnradio1"><font-awesome-icon icon="fa-solid fa-sort"
                class="me-2" />Ordenar por fecha de inicio</label>
            <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off"
              @click="sortByActivity">
            <label class="btn btn-outline-info mt-1 fw-bold" for="btnradio2"><font-awesome-icon icon="fa-solid fa-sort"
                class="me-2" />Ordenar por actividad</label>
          </div>
        </div>
        <div class="col-md-3 d-flex justify-content-end p-3 mb-3">
          <router-link :to="{ name: 'nuevavisita' }" class="btn btn-success my-1">
            <font-awesome-icon :icon="['fas', 'circle-plus']" class="me-2" />Nueva Visita</router-link>
        </div>
      </div>
    </div>
    <div v-if="visitasParaMostrar.length == 0" class="container border rounded mb-0 alert alert-warning text-center">
      <ProgressSpinner />
    </div>
    <div v-else>
      <div class="alert alert-light border rounded mb-4 p-2 lista-visitas">
        <Visita v-for="visita of visitasParaMostrar" :visita="visita" @borrarVisita="borrarVisita"
          @editarVisita="editarVisita" @mostrarAnfitrion="mostrarAnfitrion" class="py-3 px-3 mb-1"></Visita>
      </div>
      <p><strong>Total : </strong> {{ visitasParaMostrar.length }} visitas</p>
    </div>
  </Modelo>
</template>

<style>
.lista-visitas {
  height: 600px;
  overflow-y: scroll;
}

@media (max-width: 768px) {
  .tipo_visita {
    font-size: 1vh;
  }
}
</style>
