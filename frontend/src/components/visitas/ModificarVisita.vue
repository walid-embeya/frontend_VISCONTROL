<script>
import { mapActions, mapState } from 'pinia'
import { visitasStore } from '@/stores/visitas'
import { personasStore } from '@/stores/personas'
import Calendar from 'primevue/calendar'
import { llamadaAPI } from '@/stores/api-service'
import Dialog from 'primevue/dialog'

export default {
  components: { Calendar, Dialog },
  data() {
    return {
      ////// para dialog primevue
      visible: false,
      mensajeDialog: '',
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
    ...mapState(personasStore, ['anfitrionesApi', 'invitadosApi', 'personaApi']),
    ...mapState(visitasStore, ['visitaApi']),
  },
  methods: {
    ...mapActions(personasStore, ['getInvitadosApi', 'getAnfitrionesApi', 'getPersonaPorId']),
    ...mapActions(visitasStore, ['getVisitaPorId', 'putVisita', 'addInvitadosToVisita', 'getInvitadosVisita']),
    modificarVisita() {
      this.visitaParaModificar.anfitrion = this.anfitrionParaModificar._links.self.href
      this.putVisita(this.visitaParaModificar)
      ///// para Dialog primevue
      this.mensajeDialog = 'Datos visita modificados con éxito.'
      this.visible = true
    },
    anadirInvitados() {
      this.invitadosElegidos.forEach(inv => this.invitadosParaAnadir.listaInvitados.push(inv._links.self.href))
      this.addInvitadosToVisita(this.invitadosParaAnadir, this.$route.params.identificador)
      ///// para Dialog primevue
      this.mensajeDialog = 'Lista de invitados actualizada con éxito.'
      this.visible = true
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
    await llamadaAPI("get", null, this.visitaApi._links.anfitrion.href).then((response) => {
      this.anfitrionParaModificar = response.data
    })
    this.getInvitadosVisita(this.$route.params.identificador).then(invitados => {
      this.invitadosElegidos = invitados
    })
  },
}
</script>

<template>
  <div>
    <h1 class="titulo">MODIFICAR VISITA</h1>
    <Dialog v-model:visible="visible" modal header="Confirmación" :style="{ width: '40%' }">
      <p>
        <font-awesome-icon icon="fa-solid fa-check" size="lg" style="color: #26a269;" class="me-2" />
        {{ mensajeDialog }}
      </p>
      <template #footer>
        <div class="d-flex justify-content-center">
          <button class="btn btn-secondary" @click="visible = false">OK</button>
        </div>
      </template>
    </Dialog>
    <form class="p-1 border rounded">
      <!-- datos visita -->
      <div class="container alert alert-secondary border rounded mb-1 pt-2 pb-0">
        <div class="row">
          <div class="col-md-3">
            <label for="fechainicio" class="form-label fs-5 fw-bold">Fecha/Hora Inicio</label>
            <Calendar v-model="visitaParaModificar.fechaInicio" :show-time="true" dateFormat="dd/mm/yy"
              class="calendar-style" />
          </div>
          <div class="col-md-3">
            <label for="fechafin" class="form-label fs-5 fw-bold">Fecha/Hora Fin</label>
            <Calendar v-model="visitaParaModificar.fechaFin" :show-time="true" dateFormat="dd/mm/yy"
              class="calendar-style" />
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
            <textarea class="form-control zona-area" id="actuacion" rows="3" v-model="visitaParaModificar.actuaciones"
              placeholder="descripción de la visita"></textarea>
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
      <div class="d-flex justify-content-center border rounded p-2 m-0 botones-visita">
        <button type="button" class="btn btn-success me-2" @click.prevent="modificarVisita">
          <font-awesome-icon icon="fa-solid fa-file-pen" size="lg" class="me-2" />Modificar Visita</button>
        <button type="button" class="btn btn-secondary" @click="this.$router.go(-1)">
          <font-awesome-icon icon="fa-solid fa-xmark" size="lg" class="me-2" />Cerrar</button>
      </div>
    </form>
    <form class="border rounded mb-1 p-1 datos-visita">
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
      <div class="d-flex justify-content-center border rounded mb-0 p-3 botones-visita">
        <button type="button" class="btn btn-light d-inline me-1" @click.prevent="anadirInvitados">
          <font-awesome-icon icon="fa-solid fa-user-plus" class="me-2" />Actualizar Lista Invitados</button>
      </div>
    </form>
  </div>
</template>

<style>
.zona-area {
  resize: none;
}

.botones-visita {
  background-color: rgb(173, 173, 180);
}

tr {
  text-align: center
}

.calendar-style {
  font-size: 14px;
  width: 200px;
  height: 40px;
}
</style>



