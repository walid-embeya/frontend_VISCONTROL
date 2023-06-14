<script>
import Modelo from '@/components/Model.vue'
import { mapActions, mapState } from 'pinia'
import { visitasStore } from '@/stores/visitas'
import { personasStore } from '@/stores/personas'
import Calendar from 'primevue/calendar'

import Toast from 'primevue/toast'
// import { Dialog } from 'primevue/dialog'
import { useToast } from 'primevue/usetoast'


export default {
  // components: { Modelo, Calendar, Dialog, Toast },   ///// registro local de los componentes
  components: { Modelo, Calendar, Toast },
  data() {
    return {
      ////// para dialog primevue
      //visible: false,

      //// para recuperar el anfitrion seleccionado
      anfitrionParaAnadir: '',

      //// para elemento select multiple (seleccionar uno o muchos invitados)
      invitadosElegidos: [],

      // la visita que queremos añadir
      visita: {
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

      ////// para mostrar y ocultar componente de agregación de invitados
      mostrarSegundoForm: false,
    }
  },

  computed: {
    ...mapState(personasStore, ['anfitrionesApi', 'invitadosApi']),
    ...mapState(visitasStore, ['idVisita']),
  },

  methods: {
    ...mapActions(personasStore, ['getInvitadosApi', 'getAnfitrionesApi']),
    ...mapActions(visitasStore, ['postVisita', 'addInvitadosToVisita']),

    agregarVisita() {
      this.visita.anfitrion = this.anfitrionParaAnadir._links.self.href
      this.postVisita(this.visita)

      ////// confirm dialog de primevue
      // this.visible = true

      ////// para mostrar y ocultar componente de agregación de invitados
      this.mostrarSegundoForm = true;
    },

    showSuccess() {
      this.visible = false
      this.$toast.add({ severity: 'success', summary: 'Visita añadida', detail: this.idVisita, life: 3000 })
    },

    anadirInvitados() {
      if (this.idVisita != null) {
        this.invitados.listaInvitados = []
        this.invitadosElegidos.forEach(inv => this.invitados.listaInvitados.push(inv._links.self.href))    //////cargar solo los links de los invitados

        this.addInvitadosToVisita(this.invitados, this.idVisita)
      }
    },

    finalizarVisita() {
      ////// para mostrar y ocultar componente de agregación de invitados
      this.mostrarSegundoForm = false

      ///// limpiar campos de visita
      this.visita.fechaInicio = new Date()
      this.visita.fechaFin = new Date()
      this.visita.actuaciones = ''
      this.visita.actividad = ''
      this.anfitrionParaAnadir = ''

      ///// vaciar la lista de invitados elegidos
      this.invitadosElegidos = []
    },

    darAltaAnfitrion() {
      this.$router.push({ name: 'nuevoanfitrion' })
    },

  },

  created() {
    this.toast = useToast()
    this.getInvitadosApi()
    this.getAnfitrionesApi()
  },

}
</script>


<template>
  <!-- para dialog p-button-lg-->
  <Toast /> 
  <!-- <Dialog v-model:visible="visible" modal header="Confirmación" :style="{ width: '30vw' }">
    <p>Nueva visita añadida en la base de datos con exito.<br>
      Debería agregar uno o más invitados a esta visita</p>
    <template class="d-flex justify-content-center">
      <Button label="OK" icon="pi pi-info-circle" @click="showSuccess" class="btn btn-secondary mt-2" autofocus>OK</Button> 
    </template>
  </Dialog>  -->

 
  <!-- Modal visita finalizada -->
  <div class="modal fade" id="confirmacionVisitafinalizada" tabindex="-1" aria-labelledby="exampleModalLabel"
    aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Confirmación de operación</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <p><font-awesome-icon icon="fa-solid fa-check" size="lg" style="color: #26a269;" class="me-2" />Visita {{
            idVisita }} y sus invitatdos añadidos con éxito</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Modal invitados añadidos a visita -->
  <div class="modal fade" id="confirmacionInvitadosAnadidosVisita" tabindex="-1" aria-labelledby="exampleModalLabel"
    aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Confirmación de operación</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <p><font-awesome-icon icon="fa-solid fa-check" size="lg" style="color: #26a269;" class="me-2" />Lista de
            invitatdos añadida a Visita {{ idVisita }} con éxito <br>
            Puede actualizar la lista de invitados antes de finalizar la visita
          </p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>


  <Modelo titulo="CREACIÓN NUEVA VISITA">
    <form class="p-1 border rounded" style="background-color: rgb(16, 70, 151);">
      <!-- datos visita -->
      <div class="container alert alert-secondary border rounded mb-1 pt-2 pb-0">
        <div class="row">
          <div class="col-md-3">
            <label for="fechainicio" class="form-label fs-5 fw-bold">Fecha/Hora Inicio</label>
            <Calendar class="datosvisita" v-model="visita.fechaInicio" :disabled="mostrarSegundoForm" :show-time="true"
              dateFormat="dd/mm/yy" required :style="{ 'font-size': '16px', 'width': '200px', 'height': '40px' }">
            </Calendar>
          </div>
          <div class="col-md-3">
            <label for="fechafin" class="form-label fs-5 fw-bold">Fecha/Hora Fin</label>
            <Calendar class="datosvisita" v-model="visita.fechaFin" :disabled="mostrarSegundoForm" :show-time="true"
              dateFormat="dd/mm/yy" required
              :style="{ 'font-size': '16px', 'width': '200px', 'height': '40px', 'color': mostrarSegundoForm ? 'black' : 'initial' }">
            </Calendar>
          </div>
          <div class="col-md-6">
            <label for="actividad" class="form-label fs-5 fw-bold">Actividad</label>
            <input type="text" class="form-control datosvisita" id="actividad" v-model="visita.actividad"
              :disabled="mostrarSegundoForm" placeholder="actividad" required>
          </div>
        </div>

        <div class="row my-2">
          <div class="col-md-6">
            <label for="actuacion" class="form-label fs-5 fw-bold">Descripción</label>
          </div>
          <div class="col-md-6">
            <label for="anfitrionVisita" class="form-label fs-5 fw-bold">Anfitrión</label>
          </div>
        </div>

        <div class="row mb-2">
          <div class="col-md-6">
            <textarea class="form-control" id="actuacion" rows="3" v-model="visita.actuaciones"
              :disabled="mostrarSegundoForm" placeholder="descripción de la visita" style="resize: none;"
              :style="{ color: mostrarSegundoForm ? 'gray' : 'black' }"></textarea>
          </div>
          <div class="col-md-5">
            <select id="anfitrionVisita" class="form-select datosvisita me-2" v-model="anfitrionParaAnadir"
              :disabled="mostrarSegundoForm" required>
              <option value="" disabled>--seleccionar un anfitrión--</option>
              <option v-for="anf of anfitrionesApi" :value="anf">{{ anf.nombre }} {{ anf.apellidos }}</option>
            </select>
          </div>
          <div class="col-md-1">
            <button type="button" @click="darAltaAnfitrion" class="btn btn-dark" data-bs-toggle="tooltip"
              data-bs-placement="right" title="hacer clic para dar de alta un nuevo anfitrión">
              <font-awesome-icon :icon="['fas', 'circle-plus']" size="lg" style="color: #ffffff;" />
            </button>
          </div>
        </div>
      </div>

      <!-- buton de guardar visita (Primer Endpoint) -->
      <div class="d-flex justify-content-center border rounded alert alert-primary p-2 m-0"
        style="background-color: rgb(169, 169, 189);">
        <button :disabled="mostrarSegundoForm" type="button" class="btn btn-success me-2" @click.prevent="agregarVisita">
          <font-awesome-icon icon="fa-solid fa-floppy-disk" size="lg" class="me-2" />Guardar Visita</button>
        <button :disabled="mostrarSegundoForm" type="button" class="btn btn-secondary" @click="this.$router.go(-1)">
          <font-awesome-icon icon="fa-solid fa-xmark" size="lg" class="me-2" />Cancelar</button>
      </div>
    </form>

    <form v-if="mostrarSegundoForm" class="border rounded mb-1 p-1" style="background-color: rgb(16, 70, 151);">

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
          <div class="col-md-4">
            <select class="form-select" multiple v-model="invitadosElegidos" data-bs-toggle="tooltip"
              data-bs-placement="right" title="Para elegir un invitado, pulsa CTRL y haz clic para añadirlo a la visita">
              <option v-for="inv of invitadosApi" :value="inv">{{ inv.dni }} - {{ inv.nombre }} {{ inv.apellidos }}
              </option>
            </select>
          </div>
          <div class="col-md-2"></div>
          <!-- tabla para lista de invitados (se puede solo eliminar invitado) -->
          <div class="col-md-5 alert alert-light">
            <table class="table table-hover table-striped">
              <thead>
                <tr>
                  <th scope="col">DNI</th>
                  <th scope="col">Nombre</th>
                  <th scope="col">Apellidos</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="elemento of invitadosElegidos">
                  <td>{{ elemento.dni }}</td>
                  <td>{{ elemento.nombre }}</td>
                  <td>{{ elemento.apellidos }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <!-- botones de guardar invitados y finalizar visita (Segundo Endpoint)-->
      <div class="d-flex justify-content-center border rounded mb-0 p-3" style="background-color: rgb(169, 169, 189);">
        <button type="button" class="btn btn-warning d-inline me-1" @click.prevent="anadirInvitados"
          data-bs-toggle="modal" data-bs-target="#confirmacionInvitadosAnadidosVisita"><font-awesome-icon
            icon="fa-solid fa-user-plus" class="me-2" />Añadir Lista Invitados</button>
        <button type="button" class="btn btn-light" data-bs-toggle="modal" data-bs-target="#confirmacionVisitafinalizada"
          @click="finalizarVisita">Finalizar Visita</button>
      </div>
    </form>
  </Modelo>
</template>

<style>
.datosvisita[disabled] {
  color: gray;
}

.p-button-lg {
  font-size: 1rem;
  padding: 0.75rem 2.75rem;
}

.icono {
  margin-right: 0.5rem;
}

</style>



