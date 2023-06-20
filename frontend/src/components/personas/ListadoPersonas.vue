<script>
import Modelo from '@/components/Model.vue'
import Persona from './Persona.vue'
import { mapState, mapActions } from 'pinia'
import { personasStore } from '@/stores/personas'
import ConfirmDialog from 'primevue/confirmdialog'
import Toast from 'primevue/toast'
import { useToast } from 'primevue/usetoast'
import { Modal } from '~bootstrap'
import Dialog from 'primevue/dialog'

export default {
  components: { Persona, Modelo, ConfirmDialog, Toast, Dialog },                   ///// registro local de los componentes
  data() {
    return {
      opcionElegida: 'anfitrion',
      dniParaBuscar: '',     // almacena el dni de la peronsa que queremos buscar
      tipoFiltro: '',        // almacena el tipo de persona seleccionado para el filtro  
      resultadosBusqueda: [],
      filtroPendiente: false,

      ////// para dialog primevue
      visible: false,
      mensajeDialog: '',
    }
  },
  computed: {
    ...mapState(personasStore, ['personasApi', 'visitasPersona']),

    personasParaMostrar() {
      return this.filtroPendiente ? this.resultadosBusqueda : this.personasApi
    },
  },
  methods: {
    ...mapActions(personasStore, ['getPersonasApi', 'deletePersona', 'borrarVisitasPersona', 'getVisitasPersona']),

    mostrarModal() {
      Modal.getOrCreateInstance('#addPersona').show()
    },

    mostrarComponente() {
      if (this.opcionElegida === 'anfitrion') {
        this.$router.push({ name: 'nuevoanfitrion' })
      } else if (this.opcionElegida === 'invitado') {
        this.$router.push({ name: 'nuevoinvitado' })
      }
    },

    mostrarPersona(persona) {
      this.$router.push({ name: 'personainfo', params: { identificador: persona.id } })
    },

    editarPersona(persona) {
      this.$router.push({ name: 'modificarpersona', params: { identificador: persona.id } })
    },

    async borrarPersona(persona) {
      this.$confirm.require({
        message: '¿ Está seguro de borrar la persona con DNI ' + persona.dni + ' y sus visitas ?',
        header: 'Confirmación de borrado',
        icon: 'pi pi-info-circle',
        acceptClass: 'p-button-danger',
        acceptLabel: 'Sí',
        accept: () => {
          if (persona.tipo == 'Invitado') {
            this.visible = false
            this.deletePersona(persona).then((r) => {
              let indexToRemove = this.personasApi.indexOf(persona)
              this.personasApi.splice(indexToRemove, 1)
            })
              .catch((error) => {
                console.error("A la hora de borrar la persona, Se ha producido un error : ", error);
              })

            this.$toast.add({ severity: 'success', summary: 'Borrado', detail: persona.dni, life: 3000 })
          }
          else {
            ///// para Dialog primevue
            this.visible = true
            this.mensajeDialog = 'Ne se puede borrar una persona de tipo anfitrión'
          }
        },
        reject: () => {
          this.$toast.add({ severity: 'error', summary: 'Borrado', detail: 'Cancelado', life: 3000 })
        }
      })
    },

    buscarPersonaPorDni() {
      if (this.dniParaBuscar) {
        this.filtroPendiente = true
        this.resultadosBusqueda = []         // Vaciar el array resultadosBusqueda
        const caracterBuscado = this.dniParaBuscar.toLowerCase()

        // Filtrar las personas cuyo DNI contenga el carácter buscado
        const personasEncontradas = this.personasApi.filter(persona => persona.dni.toLowerCase().includes(caracterBuscado))

        // const personaEncontrada = this.personasApi.find(persona => persona.dni === this.dniParaBuscar)
        if (personasEncontradas.length > 0) {
          this.resultadosBusqueda = personasEncontradas; // Almacena los resultados de la búsqueda
        }
      } else {
        // DNI vacío, los resultados de búsqueda son todas las personas
        this.filtroPendiente = false;
        this.resultadosBusqueda = this.personasApi;
      }
    },

    buscarPersonasPorTipo() {
      if (this.tipoFiltro) {
        this.filtroPendiente = true
        this.resultadosBusqueda = []          ///// vaciar el array resultadosBusqueda
        if (this.tipoFiltro == 'Todos') {
          this.resultadosBusqueda = this.personasApi
        }
        else {
          this.resultadosBusqueda = this.personasApi.filter(persona => persona.tipo === this.tipoFiltro)
        }
      } else {        // tipoFiltro vacío, el resultados de búsqueda es toda las personas
        this.filtroPendiente = false
        this.resultadosBusqueda = this.personasApi
      }
    },

  },

  created() {
    this.toast = useToast()
    this.getPersonasApi()
  },

}
</script>

<template>
  <Modelo titulo="LISTADO PERSONAS">

    <Dialog v-model:visible="visible" modal header="Mensaje" :style="{ width: '35vw' }">
      <p>
        <font-awesome-icon icon="fa-solid fa-exclamation" size="xl" style="color: #f5c211;" class="me-3" />
        {{ mensajeDialog }}
      </p>
      <template #footer>
        <div class="d-flex justify-content-center">
          <button class="btn btn-secondary" @click="visible = false">OK</button>
        </div>
      </template>
    </Dialog>

    <Toast />
    <ConfirmDialog></ConfirmDialog>

    <div class="d-flex justify-content-between mb-2">
      <!-- Boton Modal -->
      <button @click="mostrarModal" type="button" class="btn btn-success me-2" d ta-bs-toggle="modal"
        data-bs-target="#addPersona">
        <font-awesome-icon :icon="['fas', 'circle-plus']" class="me-2" />Nueva Persona
      </button>

      <!-- Boton busqueda -->
      <div class="d-flex justify-content-end">
        <input id="dni" type="text" v-model="dniParaBuscar" @input="buscarPersonaPorDni" placeholder="buscar por DNI"
          class="form-control me-2 ">
      </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="addPersona" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="exampleModalLabel">Elegir Tipo Persona</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="form-check form-check-inline">
              <input class="form-check-input" type="radio" name="radios" id="anfitrion" value="anfitrion"
                v-model="opcionElegida">
              <label class="form-check-label" for="anfitrion">
                Anfitrion
              </label>
            </div>

            <div class="form-check form-check-inline">
              <input class="form-check-input" type="radio" name="radios" id="invitado" value="invitado"
                v-model="opcionElegida">
              <label class="form-check-label" for="invitado">
                Invitado
              </label>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Anular</button>
            <button @click="mostrarComponente" type="button" class="btn btn-success my-2"
              data-bs-dismiss="modal">validar</button>
          </div>
        </div>
      </div>
    </div>

    <div v-if="personasApi">
      <div class="border rounded mb-4 p-2 lista-personas">
        <table class="table table-striped table-hover">
          <thead>
            <tr>
              <th scope="col" class="color-thead">DNI</th>
              <th scope="col" class="color-thead">Nombre</th>
              <th scope="col" class="color-thead">Apellidos</th>
              <th scope="col" class="color-thead">
                <div>
                  <select id="tipoFiltro" v-model="tipoFiltro" @click="buscarPersonasPorTipo">
                    <option value="">Todos</option>
                    <option value="Anfitrion">Anfitrión</option>
                    <option value="Invitado">Invitado</option>
                  </select>
                </div>
              </th>
              <th scope="col" class="color-thead"></th>
              <th scope="col" class="color-thead"></th>
              <th scope="col" class="color-thead"></th>
            </tr>
          </thead>
          <tbody>
            <Persona v-for="persona of personasParaMostrar" :persona="persona" @mostrarPersona="mostrarPersona"
              @editarPersona="editarPersona" @borrarPersona="borrarPersona"></Persona>
          </tbody>
        </table>
      </div>
      <p class="fw-bold">{{ personasParaMostrar.length }} registros encontrados</p>
    </div>

    <div v-else class="text-center alert alert-light border rounded p-4 mb-0">
      <h4>cargando lista de personas...</h4>
    </div>
  </Modelo>
</template>

<style scoped>
.lista-personas {
  height: 600px;
  overflow-y: scroll;
}

tr {
  text-align: center;
}

.color-thead {
  background-color: grey;
}
</style>
