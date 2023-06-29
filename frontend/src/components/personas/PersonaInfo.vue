<script>
import Modelo from '@/components/Model.vue'
import { mapActions, mapState } from 'pinia'
import { personasStore } from '@/stores/personas'
import ProgressSpinner from 'primevue/progressspinner'
import { timestampToFecha, timestampToHora } from '@/utils/utils'

export default {
  components: { Modelo, ProgressSpinner },   ///// registro local de los componentes
  data() {
    return {
    }
  },
  computed: {
    ...mapState(personasStore, ['personaApi', 'visitasPersona']),
    esInvitado() {
      if (this.personaApi) {
        return this.personaApi.tipo == 'Invitado' ? true : false
      }
      else
        return false
    },
    tituloComponente() {
      return this.esInvitado ? 'DETALLES DEL INVITADO' : 'DETALLES DEL ANFITRIÓN'
    },
    fechaHoy() {
      return new Date()
    },
  },
  methods: {
    ...mapActions(personasStore, ['getPersonaPorId', 'getVisitasPersona']),
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
  mounted() {
    this.getVisitasPersona(this.$route.params.identificador)
  },
  created() {
    this.getPersonaPorId(this.$route.params.identificador)
  },
}
</script>

<template>
  <Modelo :titulo=tituloComponente>
    <div v-if="personaApi" class="alert alert-dark p-2 mb-0">
      <!-- informaciones comunes -->
      <div class="container border rounded mb-1 alert alert-secondary">
        <div class="row mt-1 mb-4">
          <div class="col-md-4">
            <label class="fs-5">DNI<span class="ms-3"><b>{{ personaApi.dni }}</b></span></label>
          </div>
          <div class="col-md-4">
            <label class="fs-5">Nombre<span class="ms-3"><b>{{ personaApi.nombre }}</b></span></label>
          </div>
          <div class="col-md-4">
            <label class="fs-5">Apellidos<span class="ms-3"><b>{{ personaApi.apellidos }}</b></span></label>
          </div>
        </div>
        <div class="row">
          <div class="col-md-4">
            <label class="fs-5">Telefono<span class="ms-3"><b>{{ personaApi.telefono }}</b></span></label>
          </div>
          <div class="col-md-6">
            <label class="fs-5">Email<span class="ms-3"><b>{{ personaApi.email }}</b></span></label>
          </div>
        </div>
      </div>
      <!-- datos invitado -->
      <div v-if="esInvitado" class="container border rounded mb-1 alert alert-secondary">
        <div class="row mt-1 mb-4">
          <div class="col-md-4">
            <label class="fs-5">Matricula<span class="ms-3"><b>{{ personaApi.matricula }}</b></span></label>
          </div>
          <div class="col-md-6">
            <label class="fs-5">Empresa<span class="ms-3"><b>{{ personaApi.empresa }}</b></span></label>
          </div>
        </div>
        <div class="row">
          <div class="col-md-4">
            <div v-if="personaApi.autorizacion" class="form-check form-check-inline">
              <input class="form-check-input" type="checkbox" id="autorizacion" value="option1" checked disabled>
              <label class="form-check-label" for="autorizacion">Autorizado</label>
            </div>
            <div v-else class="form-check form-check-inline">
              <input class="form-check-input" type="checkbox" id="autorizacion" value="option1" disabled>
              <label class="form-check-label" for="autorizacion">Autorizado</label>
            </div>
          </div>
          <div v-if="personaApi.autorizacion" class="col-md-4">
            <label class="fs-5">Inicio autorización<span class="ms-3"><b>{{ fecha(personaApi.inicioAut)
            }}</b></span></label>
          </div>
          <div v-if="personaApi.autorizacion" class="col-md-4">
            <label class="fs-5">Fin autorización<span class="ms-3"><b>{{ fecha(personaApi.inicioAut) }}</b></span></label>
          </div>
        </div>
      </div>
      <!-- datos anfitrion -->
      <div v-else class="container border rounded mb-1 alert alert-secondary">
        <div class="row mt-1">
          <div class="col-md-4">
            <label class="fs-5">NIP<span class="ms-3"><b>{{ personaApi.nip }}</b></span></label>
          </div>
          <div class="col-md-4">
            <label class="fs-5">Area<span class="ms-3"><b>{{ personaApi.area }}</b></span></label>
          </div>
          <div class="col-md-4">
            <label class="fs-5">Role<span class="ms-3"><b>{{ personaApi.role }}</b></span></label>
          </div>
        </div>
      </div>
      <div v-if="visitasPersona" class="container border rounded mb-0 alert alert-light">
        <p v-if="esInvitado" class="fs-3 fw-bold text-center text-warning">Lista de visitas del invitado</p>
        <p v-else class="fs-3 fw-bold text-center text-danger">Lista de visitas planificadas por el anfitrión</p>
        <div class="lista-visitas">
          <table class="table table-striped table-hover">
            <thead>
              <tr>
                <th scope="col" class="color-thead">Fecha/Hora Inicio</th>
                <th scope="col" class="color-thead">Fecha/Hora Fin</th>
                <th scope="col" class="color-thead">Actividad</th>
                <th scope="col" class="color-thead">Estado</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="visita of visitasPersona">
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
      <div v-else class="container border rounded mb-0 alert alert-light text-center">
        <p class="text-center fw-bold fs-5">Esta persona no tiene visitas</p>
      </div>
    </div>
    <div v-else class="text-center">
      <ProgressSpinner />
    </div>
    <div class="d-flex justify-content-center border rounded alert alert-warning">
      <button @click="this.$router.push({ name: 'home' })" class="btn btn-secondary my-2 me-1">
        <font-awesome-icon icon="fa-solid fa-house" style="color: #ffffff;" class="me-2" />Home
      </button>
      <button @click="this.$router.go(-1)" class="btn btn-secondary my-2">
        <font-awesome-icon icon="fa-solid fa-xmark" size="lg" class="me-2" />Cerrar
      </button>
    </div>
  </Modelo>
</template>

<style scoped>
.lista-visitas {
  height: 300px;
  overflow-y: scroll;
}

tr {
  text-align: center;
}

.color-thead {
  background-color: grey;
}
</style>