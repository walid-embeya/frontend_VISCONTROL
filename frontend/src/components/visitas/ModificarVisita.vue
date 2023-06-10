<script>
import Modelo from '@/components/Model.vue'
import { mapActions, mapState } from 'pinia'
import { visitasStore } from '@/stores/visitas'
import { personasStore } from '@/stores/personas'
import Calendar from 'primevue/calendar'


export default {
  components: { Modelo, Calendar },   ///// registro local de los componentes
  data() {
    return {
      //// para recuperar el anfitrion seleccionado
      anfitrionParaAnadir: null,

      //// para select multiple (seleccionar uno o muchos invitados)
      invitadosElegidos: [],

      // la visita que queremos modificar
      visitaParaModificar: {
        fechaInicio: new Date(),
        fechaFin: new Date(),
        actuaciones: '',
        actividad: '',
        anfitrion: ''
      },

      // los invitados que vamos a añadir a la visita
      invitados: {
        listaInvitados: []
      },
    }
  },

  computed: {
    ...mapState(personasStore, ['anfitrionesApi', 'invitadosApi']),
    ...mapState(visitasStore, ['visitaApi']),
  },

  methods: {
    ...mapActions(personasStore, ['getInvitadosApi', 'getAnfitrionesApi']),
    ...mapActions(visitasStore, ['getVisitaPorId', 'putVisita', 'addInvitadosToVisita']),


    agregarVisita() {

      console.log(this.anfitrionParaAnadir._links.self.href)
      this.visitaParaModificar.anfitrion = this.anfitrionParaAnadir._links.self.href


      console.log("vamos a modificar la visita", JSON.stringify(this.visitaParaModificar))

      this.putVisita(this.visitaParaModificar)

      /////// Desactivar campos de visita para añadir invitados
    },

    anadirInvitados() {
      //console.log("array invitados elegidos", this.invitadosElegidos)

      this.invitadosElegidos.forEach(inv => this.invitados.listaInvitados.push(inv._links.self.href))

      console.log("invitados para anadir :", this.invitados.listaInvitados)

      this.addInvitadosToVisita(this.invitados, this.idVisita)
    },

    darAltaAnfitrion() {
      this.$router.push({ name: 'nuevoanfitrion' })
    },
  },

  async created() {

    this.getInvitadosApi()
    this.getAnfitrionesApi()

    await this.getVisitaPorId(this.$route.params.identificador)

    this.visitaParaModificar = { ...this.visitaApi }   ///// clonar
  }
}
</script>


<template>
  <Modelo titulo="MODIFICAR VISITA">
    <form class="p-1 border rounded" style="background-color: rgb(16, 70, 151);">

      <!-- datos visita -->

      <div class="container alert alert-secondary border rounded mb-1 pt-2 pb-0">
        <div class="row">
          <div class="col-md-3">
            <label for="fechainicio" class="form-label fs-5 fw-bold">Fecha/Hora Inicio</label>
            <Calendar v-model="visitaParaModificar.fechaInicio" :show-time="true" dateFormat="dd/mm/yy"
              :style="{ 'font-size': '16px', 'width': '200px', 'height': '40px' }"></Calendar>
          </div>
          <div class="col-md-3">
            <label for="fechafin" class="form-label fs-5 fw-bold">Fecha/Hora Fin</label>
            <Calendar v-model="visitaParaModificar.fechaFin" :show-time="true" dateFormat="dd/mm/yy"
              :style="{ 'font-size': '14px', 'width': '200px', 'height': '40px' }"></Calendar>
          </div>
          <div class="col-md-6">
            <label for="actividad" class="form-label fs-5 fw-bold">Actividad</label>
            <input type="text" class="form-control" id="actividad" v-model="visitaParaModificar.actividad"
              placeholder="actividad" required>
          </div>
        </div>

        <div class="row my-2">
          <div class="col-md-6">
            <label for="actuacion" class="form-label fs-5 fw-bold">Descripción</label>
          </div>

          <div class="col-md-6">
            <label for="anfitrion" class="form-label fs-5 fw-bold">Anfitrión</label>
          </div>
        </div>


        <div class="row mb-2">
          <div class="col-md-6">
            <textarea class="form-control" id="actuacion" rows="3" v-model="visitaParaModificar.actuaciones"
              placeholder="descripción de la visita" style="resize: none;"></textarea>
          </div>

          <div class="col-md-5">
            <select class="form-select me-2" v-model="anfitrionParaAnadir">
              <option v-for="anf of anfitrionesApi" :value="anf">{{ anf.nombre }} {{ anf.apellidos }}</option>
            </select>
          </div>

          <div class="col-md-1">
            <button type="button" @click="darAltaAnfitrion" class="btn btn-primary" data-bs-toggle="tooltip"
              data-bs-placement="right" title="hacer clic para dar de alta un nuevo anfitrión">
              <font-awesome-icon :icon="['fas', 'circle-plus']" size="lg" style="color: #ffffff;" />
            </button>
          </div>
        </div>

      </div>

      <!-- buton de guardar visita (Primer Endpoint) -->

      <div class="d-flex justify-content-center border rounded alert alert-primary p-2 m-0"
        style="background-color: rgb(56, 56, 58);">

        <button type="button" class="btn btn-light" @click.prevent="agregarVisita">
          <font-awesome-icon icon="fa-solid fa-floppy-disk" size="lg" class="me-2" />Actualizar Visita</button>
      </div>

    </form>

    <form class="border rounded mb-1 p-1" style="background-color: rgb(16, 70, 151);">

      <!-- datos visitantes -->
      <div class="container alert alert-secondary border rounded mb-1 pt-2 pb-0">
        <div class="row">
          <div class="col-md-4">
            <label for="nombreinvitado" class="col-form-label fs-5 fw-bold">Selección de invitados</label>
          </div>
        </div>

        <div class="row mb-3">
          <!-- select multiple -->
          <div class="col-md-5">
            <select class="form-select" multiple v-model="invitadosElegidos">
              <option v-for="inv of invitadosApi" :value="inv">{{ inv.dni }} - {{ inv.nombre }} {{ inv.apellidos }}
              </option>
            </select>
          </div>

          <!-- tabla para lista de invitados (se puede solo eliminar invitado) -->
          <div class="col-md-7 alert alert-warning">
            <table class="table table-hover table-striped">
              <thead>
                <tr>
                  <th scope="col">DNI</th>
                  <th scope="col">Nombre</th>
                  <th scope="col">Apellidos</th>
                  <!-- <th scope="col">Empresa</th>     -->

                </tr>
              </thead>
              <tbody>
                <tr v-for="elemento of invitadosElegidos">
                  <td>{{ elemento.dni }}</td>
                  <td>{{ elemento.nombre }}</td>
                  <td>{{ elemento.apellidos }}</td>
                </tr>
                <!-- <font-awesome-icon icon="fa-sharp fa-solid fa-circle-minus" style="color: #e01b24;" /> -->
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <!-- botones de guardar invitados y fionalizar (Segundo Endpoint)-->

      <div class="d-flex justify-content-center border rounded mb-0 p-3" style="background-color: rgb(56, 56, 58);">
        <button type="button" class="btn btn-success d-inline me-1" @click.prevent="anadirInvitados">
          <font-awesome-icon icon="fa-solid fa-user-plus" class="me-2" />Añadir Invitados</button>
      </div>
    </form>
  </Modelo>
</template>



