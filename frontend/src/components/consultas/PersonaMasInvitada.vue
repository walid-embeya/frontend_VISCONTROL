<script>
// import Modelo from '@/components/Model.vue'
import { mapActions, mapState } from 'pinia'
import { personasStore } from '@/stores/personas'
import { llamadaAPI } from '@/stores/api-service'
import { timestampToFecha, timestampToHora } from '@/utils/utils'
import Dialog from 'primevue/dialog'
import ProgressSpinner from 'primevue/progressspinner'

export default {
  components: { Dialog, ProgressSpinner },   ///// registro local de los componentes
  data() {
    return {
      anfitrion: '',
      visitasInvitadoMostrado: null,
      ////// para dialog primevue
      visible: false,
      mensajeDialog: '',
      ////// para mostrar y ocultar componente de resultado de consulta
      mostrarSegundoForm: false,
    }
  },
  computed: {
    ...mapState(personasStore, ['anfitrionesApi', 'huespedMasInvitado', 'visitasPersona']),
    fechaHoy() {
      return new Date()
    },
  },
  methods: {
    ...mapActions(personasStore, ['getAnfitrionesApi', 'getPersonaMasInvitado', 'getVisitasPersona']),
    async mostrarResultadoConsulta() {
      if (this.anfitrion) {
        await this.getVisitasPersona(this.anfitrion.id)
        if (this.visitasPersona) {
          this.getPersonaMasInvitado(this.anfitrion.id).then(r => {
            this.visitasInvitadoMostrado = null
            if (this.huespedMasInvitado) {
              this.mostrarSegundoForm = true
              //// recuperar las visitas planificadas por este anfitrion y asistidas por de este invitado                      
              this.getVisitasPersona(this.huespedMasInvitado.id).then(r => {
                this.visitasInvitadoMostrado = []
                this.visitasPersona.forEach(v => {
                  ////// recuperar el ID del anfitrion de cada visita
                  llamadaAPI('get', null, v._links.self.href).then((response) => {
                    let linkAnfitrion = response.data._links.anfitrion.href
                    let array = linkAnfitrion.split('/')
                    let idAnfitrion = array[array.length - 1]
                    if (idAnfitrion == this.anfitrion.id) {
                      this.visitasInvitadoMostrado.push(v)
                    }
                  })
                })
              })
            }
          })
        }
        else {
          ///// para Dialog primevue
          this.visible = true
          this.mensajeDialog = 'No hay resultado para este anfitrión'
          this.mostrarSegundoForm = false
        }
      }
      else {
        ///////  no hay eleccion de un anfitrion
        this.mostrarSegundoForm = false
      }
    },
    estadoVisita(visita) {
      return new Date(visita.fechaFin) > this.fechaHoy ? 'Pendiente' : 'Hecha'
    },
    fecha(d) {
      return timestampToFecha(new Date(d))
    },
    hora(d) {
      return timestampToHora(new Date(d))
    },
  },
  created() {
    this.getAnfitrionesApi()
  },
};
</script>

<template>
  <div>
    <h1 class="titulo">EL HUÉSPED MÁS INVITADO POR UN ANFITRIÓN</h1>
    <!-- <Modelo titulo="EL HUÉSPED MÁS INVITADO POR UN ANFITRIÓN"> -->
    <Dialog v-model:visible="visible" modal header="Mensaje" :style="{ width: '40%' }">
      <p>
        <font-awesome-icon icon="fa-solid fa-message" size="lg" class="me-2" />
        {{ mensajeDialog }}
      </p>
      <template #footer>
        <div class="d-flex justify-content-center">
          <button class="btn btn-secondary" @click="visible = false">OK</button>
        </div>
      </template>
    </Dialog>
    <!-- para elegir un anfitrion -->
    <div class="d-flex flex-row alert alert-dark border rounded mb-0">
      <div class="me-3">
        <label for="anfitrionvisita" class="form-label fs-5 fw-bold">Anfitrión</label>
      </div>
      <div>
        <select id="anfitrionVisita" class="form-select me-5" v-model="anfitrion" @change="mostrarResultadoConsulta"
          required>
          <option value="" disabled>--seleccionar un anfitrión--</option>
          <option v-for="anf of anfitrionesApi" :value="anf">{{ anf.nombre }} {{ anf.apellidos }}</option>
        </select>
      </div>
    </div>
    <form v-if="mostrarSegundoForm" class="p-2 border rounded datos-invitado">
      <!-- informaciones communes de persona -->
      <div v-if="huespedMasInvitado" class="container alert alert-dark border rounded mb-1">
        <div class="row mb-3">
          <div class="col-md-4">
            <label for="dni" class="form-label">DNI</label>
            <input type="text" class="form-control" id="dni" v-model="huespedMasInvitado.dni" readonly>
          </div>
          <div class="col-md-4">
            <label for="nombre" class="form-label">Nombre</label>
            <input type="text" class="form-control" id="nombre" v-model="huespedMasInvitado.nombre" readonly>
          </div>
          <div class="col-md-4">
            <label for="apellidos" class="form-label">Apellidos</label>
            <input type="text" class="form-control" id="apellidos" v-model="huespedMasInvitado.apellidos" readonly>
          </div>
        </div>
        <div class="row">
          <div class="col-md-4">
            <label for="telefono" class="form-label">Telefono</label>
            <input type="text" class="form-control" id="telefono" v-model="huespedMasInvitado.telefono" readonly>
          </div>

          <div class="col-md-4">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" v-model="huespedMasInvitado.email" readonly>
          </div>
        </div>
      </div>
      <!-- informaciones propias del invitado -->
      <div v-if="huespedMasInvitado" class="container alert alert-dark border rounded mb-1">
        <div class="row mb-0">
          <div class="col-md-4">
            <label for="matricula" class="form-label">Matricula</label>
            <input type="text" class="form-control" id="matricula" v-model="huespedMasInvitado.matricula" readonly>
          </div>
          <div class="col-md-4">
            <label for="empresa" class="form-label">Empresa</label>
            <input type="text" class="form-control" id="empresa" v-model="huespedMasInvitado.empresa" readonly>
          </div>
          <div class="col-md-4 align-self-end">
            <div class="form-check">
              <input v-model="huespedMasInvitado.autorizacion" class="form-check-input" type="checkbox" id="autorizacion"
                value="option1">
              <label class="form-check-label" for="autorizacion">Autorizado</label>
            </div>
          </div>
        </div>
      </div>
      <div v-if="visitasInvitadoMostrado" class="container border rounded alert alert-light mb-0">
        <p class="fs-3 fw-bold text-center text-danger">Lista de Visitas</p>
        <div class="lista-visitas">
          <table class="table table-striped table-hover">
            <thead class="table-dark">
              <tr class="cabezera">
                <th scope="col">Fecha/Hora Inicio</th>
                <th scope="col">Fecha/Hora Fin</th>
                <th scope="col">Actividad</th>
                <th scope="col">Estado</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="visita of visitasInvitadoMostrado">
                <th scope="row">{{ fecha(visita.fechaInicio) }} a las {{ hora(visita.fechaInicio) }}</th>
                <td>{{ fecha(visita.fechaFin) }} a las {{ hora(visita.fechaFin) }}</td>
                <td>{{ visita.actividad }}</td>
                <td v-if="estadoVisita(visita) == 'Pendiente'" class="fw-bold text-danger">{{ estadoVisita(visita) }}</td>
                <td v-else>{{ estadoVisita(visita) }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <!-- boton de cerrar -->
      <div class="d-flex justify-content-center border rounded mb-0 p-2 barra-footer">
        <button type="submit" class="btn btn-secondary" @click="this.$router.push({ name: 'home' })"><font-awesome-icon
            icon="fa-solid fa-xmark" size="lg" class="me-2" />Cerrar</button>
      </div>
    </form>
    <!-- </Modelo> -->
  </div>
</template>

<style>
.datos-invitado {
  background-color: rgb(143, 170, 211);
}

.lista-visitas {
  height: 200px;
  overflow-y: scroll;
}

.barra-footer {
  background-color: rgb(236, 231, 224);
}
</style>