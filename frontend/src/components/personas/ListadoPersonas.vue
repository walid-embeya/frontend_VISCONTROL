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
      opcionElegida: 'anfitrion'
    }
  },
  computed: {
    ...mapState(personasStore, ['personasApi']),
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

    <!-- Button Modal -->
    <div class="d-flex justify-content-start">
      <button @click="mostrarModal" type="button" class="btn btn-success mt-2" data-bs-toggle="modal"
        data-bs-target="#addPersona">
        <font-awesome-icon :icon="['fas', 'circle-plus']" class="me-2" />Nueva Persona
      </button>
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

    <div v-if="personasApi" style="height: 600px; overflow-y: scroll;">
      <table class="table table-striped table-hover mt-2">
        <thead class="alert alert-primary">
          <tr>
            <th scope="col">DNI</th>
            <th scope="col">Nombre</th>
            <th scope="col">Apellidos</th>
            <th scope="col">Tipo</th>
          </tr>
        </thead>
        <tbody>
          <Persona v-for="persona of personasApi" :persona="persona" @mostrarPersona="mostrarPersona"
            @editarPersona="editarPersona" @borrarPersona="borrarPersona"></Persona>
        </tbody>
      </table>
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
