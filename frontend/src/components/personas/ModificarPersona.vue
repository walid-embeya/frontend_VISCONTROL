<script>
import Modelo from '@/components/Model.vue'
import { mapState, mapActions } from 'pinia'
import { personasStore } from '@/stores/personas'
import Calendar from 'primevue/calendar'

export default {
  components: { Modelo, Calendar },   ///// registro local de los componentes
  data() {
    return {
      personaParaModificar: null,
      //   personaParaModificar: {
      //     dni: '',
      //     nombre: '',
      //     apellidos: '',
      //     telefono: '',
      //     email: '',
      //     nip: null,
      //     area: null,
      //     role: null,
      //     matricula: '',
      //     empresa: '',
      //     autorizacion: null,
      //     inicioAut: null,
      //     finAut: null,
      //     tipo: ''
      //   },
    }
  },
  computed: {
    ...mapState(personasStore, ['personaApi', 'personasApi']),
    esInvitado() {
      if (this.personaApi) {
        return this.personaApi.tipo == 'Invitado' ? true : false
      }
      else
        return false
    },
    tituloComponente() {
      return this.esInvitado ? 'MODIFICACIÓN DEL INVITADO' : 'MODIFICACIÓN DEL ANFITRIÓN'
    },
  },
  methods: {
    ...mapActions(personasStore, ['putPersona', 'getPersonaPorId']),
    modificarPersona() {
      this.putPersona(this.personaParaModificar)
    },
  },
  async created() {
    this.personaParaModificar = this.personasApi.find(p => p.id == this.$route.params.identificador)
    await this.getPersonaPorId(this.$route.params.identificador)
    if (this.personaApi) {
      this.personaParaModificar = { ...this.personaApi }   ///// clone superficial

      if (this.personaApi.inicioAut != null) {
        this.personaParaModificar.inicioAut = new Date(this.personaParaModificar.inicioAut)
        this.personaParaModificar.finAut = new Date(this.personaParaModificar.finAut)
      }
    }
    //  console.log(this.personasApi)
    // this.personaParaModificar = this.personasApi.find(p => p.id == this.$route.params.identificador)
  },
}
</script>

<template>
  <Modelo :titulo=tituloComponente>
    <!-- Modal -->
    <div class="modal fade" id="confirmacionOperacion" tabindex="-1" aria-labelledby="exampleModalLabel"
      aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Confirmación de operación</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <p><font-awesome-icon icon="fa-solid fa-check" size="lg" style="color: #26a269;" class="me-2" />Operación
              realizada con éxito</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
    <form v-if="personaApi" class="p-2 border rounded datos-persona">
      <!-- informaciones communes de persona -->
      <div class="container alert alert-dark border rounded mb-1">
        <div class="row mb-3">
          <div class="col-md-4">
            <label for="dni" class="form-label">DNI</label>
            <input type="text" class="form-control" id="dni" v-model="personaParaModificar.dni" placeholder="dni"
              required>
          </div>
          <div class="col-md-4">
            <label for="nombre" class="form-label">Nombre</label>
            <input type="text" class="form-control" id="dni" v-model="personaParaModificar.nombre" placeholder="nombre"
              required>
          </div>
          <div class="col-md-4">
            <label for="apellidos" class="form-label">Apellidos</label>
            <input type="text" class="form-control" id="apellidos" v-model="personaParaModificar.apellidos"
              placeholder="apellidos" required>
          </div>
        </div>
        <div class="row mb-3">
          <div class="col-md-4">
            <label for="telefono" class="form-label">Telefono</label>
            <input type="text" class="form-control" id="telefono" v-model="personaParaModificar.telefono"
              placeholder="0034 613 728 154">
          </div>
          <div class="col-md-4">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" v-model="personaParaModificar.email"
              placeholder="name@example.com">
          </div>
        </div>
      </div>
      <!-- informaciones propias del invitado -->
      <div v-if="personaApi.tipo == 'Invitado'" class="container alert alert-dark border rounded mb-2">
        <div class="row mb-4">
          <div class="col-md-4">
            <label for="matricula" class="form-label">Matricula</label>
            <input type="text" class="form-control" id="matricula" v-model="personaParaModificar.matricula"
              placeholder="matricula" required>
          </div>
          <div class="col-md-4">
            <label for="empresa" class="form-label">Empresa</label>
            <input type="text" class="form-control" id="empresa" v-model="personaParaModificar.empresa"
              placeholder="nombre de empresa" required>
          </div>
        </div>
        <div class="row">
          <div class="col-md-4">
            <div class="form-check form-check-inline">
              <input v-model="personaParaModificar.autorizacion" class="form-check-input" type="checkbox"
                id="autorizacion" value="option1">
              <label class="form-check-label" for="autorizacion">Autorizado</label>
            </div>
          </div>
          <div v-if="personaParaModificar.autorizacion" class="col-md-8">
            <div class="row">
              <div class="col-md-5">
                <label for="fechainicio" class="form-label me-2">Fecha Inicio</label>
              </div>
              <div class="col-md-5">
                <label for="fechafin" class="form-label me-2">Fecha Fin</label>
              </div>
            </div>
            <div class="row">
              <div class="col-md-5">
                <Calendar v-model="personaParaModificar.inicioAut" :show-time="false" dateFormat="dd/mm/yy"
                  :style="{ 'font-size': '14px', 'width': '200px', 'height': '35px' }"></Calendar>
              </div>
              <div class="col-md-5">
                <Calendar v-model="personaParaModificar.finAut" :show-time="false" dateFormat="dd/mm/yy"
                  :style="{ 'font-size': '14px', 'width': '200px', 'height': '35px' }"></Calendar>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- informaciones propias del anfitrion -->
      <div v-else class="container alert alert-dark border rounded mb-2">
        <div class="row mb-4">
          <div class="col-md-4">
            <label for="nip" class="form-label">NIP</label>
            <input type="text" class="form-control" id="nip" v-model="personaParaModificar.nip"
              placeholder="NIP del anfitrión" required>
          </div>
          <div class="col-md-4">
            <label for="area" class="form-label">Area</label>
            <input type="text" class="form-control" id="area" v-model="personaParaModificar.area"
              placeholder="area de encarga del anfitrión" required>
          </div>
          <div class="col-md-4">
            <label for="role" class="form-label">Role</label>
            <input type="text" class="form-control" id="role" v-model="personaParaModificar.role"
              placeholder="role del anfitrión" required>
          </div>
        </div>
      </div>
      <!-- botones de guardar y cancelar -->
      <div class="d-flex justify-content-center p-2 mb-0">
        <button type="submit" class="btn btn-success d-inline me-1" @click.prevent="modificarPersona"
          data-bs-toggle="modal" data-bs-target="#confirmacionOperacion">
          <font-awesome-icon icon="fa-solid fa-floppy-disk" size="lg" class="me-2" />Actualizar</button>
        <button type="submit" class="btn btn-secondary"
          @click="this.$router.push({ name: 'personas' })"><font-awesome-icon icon="fa-solid fa-xmark" size="lg"
            class="me-2" />Cancelar</button>
      </div>
      <!-- <pre>persona API : {{ JSON.stringify(personaApi, null, " ") }}</pre> -->
    </form>
    <div v-else class="text-center border rounded p-4 mb-0 datos-persona">
      <h4>cargando datos de persona...</h4>
    </div>
  </Modelo>
</template>

<style>
.datos-persona {
  background-color: rgb(143, 170, 211)
}

.columna {
  text-align: center;
}

.linea {
  text-align: center;
}
</style>


      
      