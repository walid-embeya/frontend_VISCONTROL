<script>
import Modelo from '@/components/Model.vue'
//import PersonaComun from './PersonaComun.vue'
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
        inicioAut: new Date(),
        finAut: new Date(),
        tipo: ''
      },

      //listaInvitados: []
    }
  },

  computed: {
    ...mapState(personasStore, ['invitadosApi' ]),

    // listadoInvitados() {
    //   return this.invitadosApi 
    // },

    // autorizacion() {
    //    return this.personaParaAnadir.autorizacion ==  null ? false : true;
    // }
  },

  methods: {

    ...mapActions(personasStore, ['getInvitadosApi']),
    ...mapActions(personasStore, ['postPersona']),

    

    crearInvitado() {
      this.personaParaAnadir.tipo = "Invitado";
      console.log("vamos a dar de alta el invitado", JSON.stringify(this.personaParaAnadir))

      this.postPersona(this.personaParaAnadir)

      ///// mensaje de confirmacion

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
        inicioAut: new Date(),
        finAut: new Date(),
        tipo: ''
      },

      this.getInvitadosApi()
    },
  },

  async created() {
    //console.log("current ruta = ", this.$route.name)

    await this.getInvitadosApi()
  }
}
</script>


<template>

    <Modelo titulo="CREACIÓN NUEVO INVITADO">

      <!-- Modal -->
      <div class="modal fade" id="confirmacionOperacion" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">Confirmación de operación</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              
                <p class="text-center">
                  <!-- <font-awesome-icon icon="fa-solid fa-circle-check" size="lg" style="color: #26a269;" class="me-2" />            -->
                  <font-awesome-icon icon="fa-solid fa-check" size="lg" style="color: #26a269;" class="me-2" />
                  operación realizada con éxito</p>

            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
          </div>
        </div>
      </div>

      <form class="p-1 border rounded" style="background-color: rgb(143, 170, 211);">

            <!-- informaciones communes de persona -->

            <div class="container alert alert-dark border rounded mb-1">              
                <div class="row mb-3">
                  <div class="col-md-4">
                    <label for="dni" class="form-label">DNI</label>
                    <input type="text" class="form-control" id="dni" v-model="personaParaAnadir.dni" placeholder="dni" required>
                  </div>
                  <div class="col-md-4">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="dni" v-model="personaParaAnadir.nombre" placeholder="nombre" required>
                  </div>
                  <div class="col-md-4">
                    <label for="apellidos" class="form-label">Apellidos</label>
                    <input type="text" class="form-control" id="apellidos" v-model="personaParaAnadir.apellidos" placeholder="apellidos" required>
                  </div>
                </div>

                <div class="row mb-3">
                  <div class="col-md-4">
                    <label for="telefono" class="form-label">Telefono</label>
                    <input type="text" class="form-control" id="telefono" v-model="personaParaAnadir.telefono" placeholder="0034 613 728 154">
                  </div>

                  <div class="col-md-4">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" v-model="personaParaAnadir.email" placeholder="name@example.com">
                  </div>
                </div>
            </div>


            <!-- informaciones propias de invitado -->

            <div class="container alert alert-dark border rounded mb-1">

                <div class="row mb-4">
                  <div class="col-md-4">
                    <label for="matricula" class="form-label">Matricula</label>
                    <input type="text" class="form-control" id="matricula" v-model="personaParaAnadir.matricula" placeholder="matricula" required>
                  </div>

                  <div class="col-md-4">
                    <label for="empresa" class="form-label">Empresa</label>
                    <input type="text" class="form-control" id="empresa" v-model="personaParaAnadir.empresa" placeholder="nombre de empresa" required>
                  </div>
                </div>


                <div class="row mb-0">  
                  <div class="col-md-2">
                    <div class="form-check form-check-inline">
                        <input v-model="personaParaAnadir.autorizacion"  class="form-check-input" type="checkbox" id="autorizacion" value="option1">
                        <label class="form-check-label" for="autorizacion">Autorizado</label>
                    </div>                                      
                  </div>
                  
                  <div v-if="personaParaAnadir.autorizacion" class="col-md-10">
                    <div class="row mb-2">
                      <div class="col-md-5">
                        <label for="fechaInicio" class="form-label me-2">Fecha Inicio</label>
                        <Calendar v-model="personaParaAnadir.fechaInicio" :show-time="false" dateFormat="dd/mm/yy"
                            :style="{ 'font-size': '14px', 'width': '200px', 'height': '35px' }"></Calendar>
                      </div>

                      <div class="col-md-5">
                        <label for="fechaInicio" class="form-label me-2">Fecha Fin</label>
                        <Calendar v-model="personaParaAnadir.fechaFin" :show-time="false" dateFormat="dd/mm/yy"
                            :style="{ 'font-size': '14px', 'width': '200px', 'height': '35px' }"></Calendar>
                      </div>
                    </div>  
                     
                  </div>                  
              </div>             
            </div>

        <!-- botones de guardar y cancelar -->

        <div class="d-flex justify-content-center p-2 mb-1">
            <button type="submit" class="btn btn-success d-inline me-1" @click.prevent="crearInvitado" data-bs-toggle="modal" data-bs-target="#confirmacionOperacion">
                <font-awesome-icon icon="fa-solid fa-floppy-disk" size="lg" class="me-2"/>Guardar</button>
                        
            <button type="submit" class="btn btn-secondary" @click="$router.push({ name: 'personas' })">Cancelar</button>
        </div>

        <!-- table para listar todos los invitados -->
        <div class="container alert alert-light border rounded mb-0 p-2">

          <table class="table table-striped table-hover mt-2">
              <thead class="alert alert-dark">
                <tr class="columna">
                  <th scope="col">DNI</th>
                  <th scope="col">Nombre</th>
                  <th scope="col">Apellidos</th>
                  <th scope="col">Matricula</th>
                  <th scope="col">Empresa</th>
                  <!-- <th scope="col" style="text-align: center;">Autorizado</th> -->
                </tr>
              </thead>
              <tbody>
                <tr class="linea" v-for="invitado of invitadosApi">
                  <th scope="row">{{ invitado.dni }}</th>
                  <td>{{ invitado.nombre }}</td>
                  <td>{{ invitado.apellidos }}</td>
                  <td>{{ invitado.matricula }}</td>
                  <td>{{ invitado.empresa }}</td>

                  <!-- <td class="td-auth" v-if="invitado.autorizacion"><input type="checkbox" id="autorizado" name="autorizado" checked><label for="autorizado"></label></td>
                  <td class="td-auth" v-else><input type="checkbox" id="noAutorizado" name="noAutorizado" disabled><label for="noAutorizado"></label></td>        -->

                </tr>
              </tbody>
          </table>
        </div>

      </form>
    </Modelo>
</template>

<style>

    .columna {
      text-align: center;
    }

    .linea{
      text-align: center;
    }

</style>


      
      