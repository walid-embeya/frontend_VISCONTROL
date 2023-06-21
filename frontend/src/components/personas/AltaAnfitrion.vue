<script>
import Modelo from '@/components/Model.vue'
import { mapState, mapActions } from 'pinia'
import { personasStore } from '@/stores/personas'

export default {
  components: { Modelo },   ///// registro local de los componentes
  data() {
    return {
      personaParaAnadir: {
        dni: '',
        nombre: '',
        apellidos: '',
        telefono: '',
        email: '',
        nip: '',
        area: '',
        role: '',
        tipo: ''
      },
    }
  },
  computed: {
    ...mapState(personasStore, ['anfitrionesApi']),
  },
  methods: {
    ...mapActions(personasStore, ['getAnfitrionesApi', 'postPersona']),
    crearAnfitrion() {
      this.personaParaAnadir.tipo = "Anfitrion";
      this.postPersona(this.personaParaAnadir)
      // Limpiar los campos del formulario
      this.personaParaAnadir = {
        dni: '',
        nombre: '',
        apellidos: '',
        telefono: '',
        email: '',
        nip: '',
        area: '',
        role: '',
        tipo: ''
      }
    },
  },
  created() {
    this.getAnfitrionesApi()
  }
}
</script>

<template>
  <Modelo titulo="CREACIÓN NUEVO ANFITRIÓN">
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
    <form class="p-2 border rounded datos-anfitrion">
      <!-- informaciones communes de persona -->
      <div class="container alert alert-dark border rounded mb-2">
        <div class="row mb-3">
          <div class="col-md-4">
            <label for="dni" class="form-label">DNI</label>
            <input type="text" class="form-control" id="dni" v-model="personaParaAnadir.dni" placeholder="dni" required>
          </div>
          <div class="col-md-4">
            <label for="nombre" class="form-label">Nombre</label>
            <input type="text" class="form-control" id="dni" v-model="personaParaAnadir.nombre" placeholder="nombre"
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
      <!-- informaciones propias del anfitrion -->
      <div class="container alert alert-dark border rounded mb-2">
        <div class="row">
          <div class="col-md-4">
            <label for="nip" class="form-label">NIP</label>
            <input type="text" class="form-control" id="nip" v-model="personaParaAnadir.nip"
              placeholder="NIP del anfitrión" required>
          </div>
          <div class="col-md-4">
            <label for="area" class="form-label">Area</label>
            <input type="text" class="form-control" id="area" v-model="personaParaAnadir.area"
              placeholder="area de encarga del anfitrión" required>
          </div>
          <div class="col-md-4">
            <label for="role" class="form-label">Role</label>
            <input type="text" class="form-control" id="role" v-model="personaParaAnadir.role"
              placeholder="role del anfitrión" required>
          </div>
        </div>
      </div>
      <!-- botones de guardar y cancelar -->
      <div class="d-flex justify-content-center p-2 mb-2">
        <button type="submit" class="btn btn-success d-inline me-1" @click.prevent="crearAnfitrion" data-bs-toggle="modal"
          data-bs-target="#confirmacionOperacion">
          <font-awesome-icon icon="fa-solid fa-floppy-disk" size="lg" class="me-2" />Guardar</button>
        <!-- $router.go(-1) -->
        <button type="submit" class="btn btn-secondary" @click="this.$router.go(-1)">Cancelar</button>
      </div>
      <!-- table para listar todos los anfitriones -->
      <div v-if="anfitrionesApi" class="container alert alert-light border rounded p-2 mb-0">
        <div class="lista-anfitriones">
          <table class="table table-striped table-hover me-2">
            <thead class="alert alert-dark">
              <tr class="columna">
                <th scope="col">DNI</th>
                <th scope="col">Nombre</th>
                <th scope="col">Apellidos</th>
                <th scope="col">NIP</th>
                <th scope="col">Area</th>
                <th scope="col">Role</th>
              </tr>
            </thead>
            <tbody>
              <tr class="linea" v-for="anfitrion of anfitrionesApi">
                <th scope="row">{{ anfitrion.dni }}</th>
                <td>{{ anfitrion.nombre }}</td>
                <td>{{ anfitrion.apellidos }}</td>
                <td>{{ anfitrion.nip }}</td>
                <td>{{ anfitrion.area }}</td>
                <td>{{ anfitrion.role }}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="d-flex justify-content mt-3">
          <h4 class="fw-blod text-dark me-2">Total :</h4>
          <p class="fs-5 fw-blod text-danger">{{ anfitrionesApi.length }} anfitriones</p>
        </div>
      </div>
      <div v-else class="text-center alert alert-light border rounded p-4 mb-0">
        <h4>cargando lista de anfitriones...</h4>
      </div>
    </form>
  </Modelo>

  <!-- <pre v-if="invitadosApi">{{ JSON.stringify(anfitrionesApi, null, " ") }}</pre> -->
</template>

<style>
.datos-anfitrion {
  background-color: rgb(143, 170, 211);
}

.lista-anfitriones {
  height: 250px;
  overflow-y: scroll;
}

.columna {
  text-align: center;
}

.linea {
  text-align: center;
}
</style>


      
      