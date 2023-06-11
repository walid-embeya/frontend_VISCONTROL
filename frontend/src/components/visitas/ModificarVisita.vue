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
      anfitrionParaModificar: null,

      //// invitados de visita recuperados del store (para el elemento select multiple)
      invitadosElegidos: [],

      // la visita que queremos modificar
      visitaParaModificar: {
        fechaInicio: null,
        fechaFin: null,
        actuaciones: '',
        actividad: '',
        anfitrion: ''
      },

      // los invitados que vamos a añadir a la visita
      invitadosParaAnadir: {
        listaInvitados: []
      },
    }
  },

  computed: {
    ...mapState(personasStore, ['anfitrionesApi', 'invitadosApi', 'personaApi' ]),
    ...mapState(visitasStore, ['visitaApi']),
  },

  methods: {
    ...mapActions(personasStore, [ 'getInvitadosApi', 'getAnfitrionesApi', 'getPersonaPorId' ]),
    ...mapActions(visitasStore, [ 'getVisitaPorId', 'putVisita', 'addInvitadosToVisita', 'getInvitadosVisita' ]),


    modificarVisita() {

      //console.log(this.anfitrionParaModificar._links.self.href)
      this.visitaParaModificar.anfitrion = this.anfitrionParaModificar._links.self.href

      //console.log("vamos a modificar la visita", JSON.stringify(this.visitaParaModificar))
      this.putVisita(this.visitaParaModificar)
    },

    anadirInvitados() {
      console.log("array invitados elegidos", this.invitadosElegidos)

      this.invitadosElegidos.forEach(inv => this.invitadosParaAnadir.listaInvitados.push(inv._links.self.href))

     // console.log("invitados para anadir :", this.invitadosParaAnadir.listaInvitados)
      console.log("invitados para anadir ", JSON.stringify(this.invitadosParaAnadir))
      this.addInvitadosToVisita(this.invitadosParaAnadir, this.$route.params.identificador)
    },

    darAltaAnfitrion() {
      this.$router.push({ name: 'nuevoanfitrion' })
    },
  },

  mounted() {
    this.getInvitadosApi()
    this.getAnfitrionesApi()
  },

  async created() {
    ////// recuperar la visita
    await this.getVisitaPorId(this.$route.params.identificador)
    this.visitaParaModificar = { ...this.visitaApi }   ///// clonar
    this.visitaParaModificar.fechaInicio = new Date(this.visitaParaModificar.fechaInicio)
    this.visitaParaModificar.fechaFin = new Date(this.visitaParaModificar.fechaFin)

    ////// recuperar el anfitrion de la visita
    let array = this.visitaApi._links.anfitrion.href.split('/')
    let idAnfitrion = array[array.length - 1]
    await this.getPersonaPorId(idAnfitrion)
    this.anfitrionParaModificar = this.personaApi
    
     this.getInvitadosVisita(this.$route.params.identificador).then(invitados => {
      this.invitadosElegidos = invitados
    })
  },

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
            <select class="form-select me-2" v-model="anfitrionParaModificar">
              <option value="" disabled>--seleccionar un anfitrión--</option>
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
        style="background-color: rgb(214, 214, 226);">
        <button type="button" class="btn btn-success me-2" @click.prevent="modificarVisita">
          <font-awesome-icon icon="fa-solid fa-file-pen" size="lg" class="me-2" />Modificar Visita</button>
        <button type="button" class="btn btn-secondary" @click="this.$router.go(-1)">
          <font-awesome-icon icon="fa-solid fa-xmark" size="lg" class="me-2" />Cancelar</button>
      </div>

    </form>

    <form class="border rounded mb-1 p-1" style="background-color: rgb(16, 70, 151);">
      <!-- datos visitantes -->
      <div class="container alert alert-secondary border rounded mb-1 pt-2 pb-0">      
        <div class="row">
          <div class="col-md-4 d-flex justify-content-center">
            <label for="nombreinvitado" class="col-form-label fs-5 fw-bold">Lista de invitados</label>
          </div>
          <div class="col-md-3"></div>
          <div class="col-md-5">
            <label for="nombreinvitado" class="col-form-label fs-5 fw-bold">Invitados Seleccionados</label>
          </div>
        </div>

        <div class="row mb-3">
          <!-- select multiple -->
          <div class="col-md-5">        
            <select class="form-select" multiple v-model="invitadosElegidos" data-bs-toggle="tooltip"
              data-bs-placement="left" title="Para elegir un invitado, pulsa CTRL y haz clic para añadirlo a la visita">
              <option v-for="inv of invitadosApi" :value="inv">{{ inv.dni }} - {{ inv.nombre }} {{ inv.apellidos }}
              </option>
            </select>
          </div>

          <!-- tabla para lista de invitados (se puede solo eliminar invitado) -->
          <div class="col-md-7 alert alert-light">
            <table class="table table-hover table-striped">
              <thead>
                <tr>
                  <th scope="col" class="table-dark">DNI</th>
                  <th scope="col" class="table-dark">Nombre</th>
                  <th scope="col" class="table-dark">Apellidos</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="elemento of invitadosElegidos">
                  <th scope="row">{{ elemento.dni }}</th>
                  <td>{{ elemento.nombre }}</td>
                  <td>{{ elemento.apellidos }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <!-- botones de guardar invitados y finalizar visita (Segundo Endpoint)-->
      <div class="d-flex justify-content-center border rounded mb-0 p-3" style="background-color: rgb(62, 62, 125);">
        <button type="button" class="btn btn-light d-inline me-1" @click.prevent="anadirInvitados">
          <font-awesome-icon icon="fa-solid fa-user-plus" class="me-2" />Actualizar Lista Invitados</button>
      </div>
    </form>
  </Modelo>
</template>

<style>
tr {
  text-align: center
}
</style>



