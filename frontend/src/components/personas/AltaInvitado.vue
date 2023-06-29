<script>
import Modelo from '@/components/Model.vue'
import { mapState, mapActions } from 'pinia'
import { personasStore } from '@/stores/personas'
import Calendar from 'primevue/calendar'

export default {
  components: { Modelo, Calendar },   ///// registro local de los componentes
  data() {
    return {
      personaParaAnadir: {
        dni: '',
        nombre: '',
        apellidos: '',
        telefono: '',
        email: '',
        matricula: '',
        empresa: '',
        autorizacion: false,
        inicioAut: null,
        finAut: null,
        tipo: ''
      },
    }
  },
  computed: {
    ...mapState(personasStore, ['invitadosApi']),
    isAutorizado() {
      return this.personaParaAnadir.autorizacion == true ? 'true' : 'false'
    }
  },
  methods: {
    ...mapActions(personasStore, ['getInvitadosApi', 'postPersona']),
    crearInvitado() {
      this.personaParaAnadir.tipo = 'Invitado'
      if (!this.isAutorizado) {
        this.personaParaAnadir.inicioAut = null
        this.personaParaAnadir.finAut = null
      }
      this.postPersona(this.personaParaAnadir)
      // Limpiar los campos del formulario
      this.personaParaAnadir = {
        dni: '',
        nombre: '',
        apellidos: '',
        telefono: '',
        email: '',
        matricula: '',
        empresa: '',
        autorizacion: false,
        inicioAut: null,
        finAut: null,
        tipo: ''
      }
    },
  },
  created() {
    this.getInvitadosApi()
  },
}
</script>

<template>
  <Modelo titulo="CREACIÓN NUEVO INVITADO">
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
            <p><font-awesome-icon icon="fa-solid fa-check" size="lg" style="color: #26a269;" class="me-2" />operación
              realizada con éxito</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
    <form class="p-2 border rounded datos-invitado">
      <!-- informaciones communes de persona -->
      <div class="container alert alert-dark border rounded mb-2">
        <div class="row mb-3">
          <div class="col-md-4">
            <label for="dni" class="form-label">DNI</label>
            <input type="text" class="form-control" id="dni" v-model="personaParaAnadir.dni" placeholder="dni" required>
          </div>
          <div class="col-md-4">
            <label for="nombre" class="form-label">Nombre</label>
            <input type="text" class="form-control" id="nombre" v-model="personaParaAnadir.nombre" placeholder="nombre"
              required>
          </div>
          <div class="col-md-4">
            <label for="apellidos" class="form-label">Apellidos</label>
            <input type="text" class="form-control" id="apellidos" v-model="personaParaAnadir.apellidos"
              placeholder="apellidos" required>
          </div>
        </div>
        <div class="row">
          <div class="col-md-4">
            <label for="telefono" class="form-label">Telefono</label>
            <input type="text" class="form-control" id="telefono" v-model="personaParaAnadir.telefono"
              placeholder="0034 613 728 154">
          </div>
          <div class="col-md-4">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" v-model="personaParaAnadir.email"
              placeholder="name@example.com">
          </div>
        </div>
      </div>
      <!-- informaciones propias del invitado -->
      <div class="container alert alert-dark border rounded mb-2">
        <div class="row mb-4">
          <div class="col-md-4">
            <label for="matricula" class="form-label">Matricula</label>
            <input type="text" class="form-control" id="matricula" v-model="personaParaAnadir.matricula"
              placeholder="matricula" required>
          </div>
          <div class="col-md-4">
            <label for="empresa" class="form-label">Empresa</label>
            <input type="text" class="form-control" id="empresa" v-model="personaParaAnadir.empresa"
              placeholder="nombre de empresa" required>
          </div>
        </div>
        <div class="row">
          <div class="col-md-4">
            <div class="form-check form-check-inline">
              <input v-model="personaParaAnadir.autorizacion" class="form-check-input" type="checkbox" id="autorizacion"
                value="option1">
              <label class="form-check-label" for="autorizacion">Autorizado</label>
            </div>
          </div>
          <div v-if="personaParaAnadir.autorizacion" class="col-md-8">
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
                <Calendar v-model="personaParaAnadir.fechaInicio" :show-time="false" dateFormat="dd/mm/yy"
                  :style="{ 'font-size': '14px', 'width': '200px', 'height': '35px' }"></Calendar>
              </div>
              <div class="col-md-5">
                <Calendar v-model="personaParaAnadir.fechaFin" :show-time="false" dateFormat="dd/mm/yy"
                  :style="{ 'font-size': '14px', 'width': '200px', 'height': '35px' }"></Calendar>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- botones de guardar y cancelar -->
      <div class="d-flex justify-content-center p-2 mb-2">
        <button type="submit" class="btn btn-success d-inline me-1" @click.prevent="crearInvitado" data-bs-toggle="modal"
          data-bs-target="#confirmacionOperacion">
          <font-awesome-icon icon="fa-solid fa-floppy-disk" size="lg" class="me-2" />Guardar</button>

        <button type="submit" class="btn btn-secondary" @click="$router.push({ name: 'personas' })">Cancelar</button>
      </div>
      <!-- table para listar todos los invitados -->
      <div v-if="invitadosApi" class="container alert alert-dark border rounded p-2 mb-0">
        <div class="lista-invitados">
          <table class="table table-striped table-hover">
            <thead>
              <tr class="columna">
                <th scope="col">DNI</th>
                <th scope="col">Nombre</th>
                <th scope="col">Apellidos</th>
                <th scope="col">Matricula</th>
                <th scope="col">Empresa</th>
              </tr>
            </thead>
            <tbody>
              <tr class="linea" v-for="invitado of invitadosApi">
                <th scope="row">{{ invitado.dni }}</th>
                <td>{{ invitado.nombre }}</td>
                <td>{{ invitado.apellidos }}</td>
                <td>{{ invitado.matricula }}</td>
                <td>{{ invitado.empresa }}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="d-flex justify-content mt-3">
          <h4 class="fw-blod text-dark me-2">Total :</h4>
          <p class="fs-5 fw-blod text-danger">{{ invitadosApi.length }} invitados</p>
        </div>
      </div>
      <div v-else class="text-center alert alert-light border rounded p-4 mb-0">
        <h4>cargando lista de invitados...</h4>
      </div>
    </form>
  </Modelo>
  <!-- <pre v-if="invitadosApi">{{ JSON.stringify(invitadosApi, null, " ") }}</pre> -->
</template>

<style>
.datos-invitado {
  background-color: rgb(143, 170, 211);
}

.lista-invitados {
  height: 230px;
  overflow-y: scroll;
}

.columna {
  text-align: center;
}

.linea {
  text-align: center;
}
</style>


      
      