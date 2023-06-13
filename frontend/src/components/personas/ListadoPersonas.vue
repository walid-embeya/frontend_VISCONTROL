<script>
import Modelo from '@/components/Model.vue'
import Persona from './Persona.vue'
import { mapState, mapActions } from 'pinia'
import { personasStore } from '@/stores/personas'

import ConfirmDialog from 'primevue/confirmdialog'
import Toast from 'primevue/toast'
import { useToast } from 'primevue/usetoast'

import { Modal } from '~bootstrap'
import { nextTick } from 'vue'

export default {
  components: { Persona, Modelo, ConfirmDialog, Toast },                   ///// registro local de los componentes
  data() {
    return {
      opcionElegida: 'anfitrion',

      dniParaBuscar: '',     // almacena el dni de la peronsa que queremos buscar
      tipoFiltro: '',        // almacena el tipo de persona seleccionado para el filtro  
      resultadosBusqueda: [],
      filtroPendiente: false,
    }
  },
  computed: {
    ...mapState(personasStore, ['personasApi']),

    personasParaMostrar() {
      return this.filtroPendiente ? this.resultadosBusqueda : this.personasApi
    },
  },
  methods: {
    ...mapActions(personasStore, ['getPersonasApi', 'deletePersona']),

    mostrarModal() {
      //await nextTick()
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

    borrarPersona(persona) {
      this.$confirm.require({
        message: '¿Está seguro de borrar la persona con DNI número : ' + persona.dni + ' ?',
        header: 'Confirmación de borrado',
        icon: 'pi pi-info-circle',
        acceptClass: 'p-button-danger',
        acceptLabel: 'Sí',
        accept: () => {
          this.deletePersona(persona).then((response) => {
            let indexToRemove = this.personasApi.indexOf(persona)
            this.personasApi.splice(indexToRemove, 1)
          })
            .catch((error) => {
              console.error("A la hora de borrar la persona, Se ha producido un error : ", error);
            });
          this.$toast.add({ severity: 'success', summary: 'Borrado', detail: persona.dni, life: 3000 });
        },
        reject: () => {
          this.$toast.add({ severity: 'error', summary: 'Borrado', detail: 'Cancelado', life: 3000 });
        }
      })
    },

    buscarPersonaPorDni() {
      if (this.dniParaBuscar) {
        this.filtroPendiente = true
        this.resultadosBusqueda = []    ///// vaciar el array resultadosBusqueda
        const personaEncontrada = this.personasApi.find(persona => persona.dni === this.dniParaBuscar)
        if (personaEncontrada) {
          this.resultadosBusqueda = [personaEncontrada] // Almacena los resultados de la búsqueda
        }
      } else {          ////DNI vacío, el resultados de búsqueda es toda las personas
        this.filtroPendiente = false
        this.resultadosBusqueda = this.personasApi
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

    <Toast />
    <ConfirmDialog></ConfirmDialog>

    <div class="my-3">
      <div class="d-flex justify-content-between">
        <!-- Boton Modal -->
        <button @click="mostrarModal" type="button" class="btn btn-success" data-bs-toggle="modal"
          data-bs-target="#addPersona">
          <font-awesome-icon :icon="['fas', 'circle-plus']" class="me-2" />Nueva Persona
        </button>

        <!-- Boton busqueda -->
        <div class="d-flex justify-content-end">
          <input type="text" v-model="dniParaBuscar" @input="buscarPersonaPorDni" placeholder="Buscar por DNI"
            class="form-control me-2 ">
          <button @click="buscarPersonaPorDni" type="button" class="btn btn-primary">Buscar</button>
        </div>
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
      <div style="height: 600px; overflow-y: scroll;" class="border rounded mb-3">
        <table class="table table-striped table-hover">
          <thead class="alert alert-primary">
            <tr>
              <th scope="col">DNI</th>
              <th scope="col">Nombre</th>
              <th scope="col">Apellidos</th>
              <th scope="col">
                <div>
                  <label for="tipoFiltro" class="me-2">Tipo</label>
                  <select id="tipoFiltro" v-model="tipoFiltro" @click="buscarPersonasPorTipo">
                    <option value="">Todos</option>
                    <option value="Anfitrion">Anfitrión</option>
                    <option value="Invitado">Invitado</option>
                  </select>
                </div>
              </th>
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
tr {
  text-align: center;
}
</style>
