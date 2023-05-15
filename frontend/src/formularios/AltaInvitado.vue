<script>
  import { mapState } from 'pinia'
  import Modelo from '@/components/Model.vue'
  import { invitadosStore } from '@/stores/invitados'

  export default {
    components: { Modelo },   ///// registro local de los componentes
    data() {
      return {
        invitado: {
          id: '',
          dni: '',
          nombre: '',
          apellidos: '',
          telefono: '',
          email: '',
          matricula: '',
          empresa: '',
          autorizacion: undefined
        },
        invitados: []
      }
    },

    computed: {
        ...mapState(invitadosStore, { listadoInvitados: 'invitados' }),
    },

    methods: {
      submitForm() {
        // Agregar la persona a la lista de personas
        this.listadoInvitados.push(this.invitado)
        
        // Limpiar los campos del formulario
        // this.persona = {
        //   dni: '',
        //   nombre: '',
        //   apellidos: '',
        //   telefono: '',
        //   fechaNacimiento: ''
        // }
        
        // Aquí se puede enviar los datos de la persona al servidor para guardarlos en una base de datos, por ejemplo
      }
    }
  }
  </script>


<template>
    <div>
      <Modelo titulo="CREACIÓN NUEVO INVITADO">

        <form @submit.prevent="submitForm" class="p-1 border rounded" style="background-color: rgb(143, 170, 211);">
          <div class="container alert alert-dark border rounded mb-1">
              <div class="row mb-3">              
                  <div class="col-md-4">
                    <label for="dni" class="form-label">DNI</label>
                    <input type="text" class="form-control" id="dni" v-model="invitado.dni" placeholder="dni" required>
                  </div>
                  <div class="col-md-4">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="dni" v-model="invitado.nombre" placeholder="nombre" required>
                  </div>
                  <div class="col-md-4">
                    <label for="apellidos" class="form-label">Apellidos</label>
                    <input type="text" class="form-control" id="apellidos" v-model="invitado.apellidos" placeholder="apellidos" required>
                  </div>
              </div>
              
              <div class="row mb-3">
                  <div class="col-md-4">
                    <label for="telefono" class="form-label">Telefono</label>
                    <input type="text" class="form-control" id="telefono" v-model="invitado.telefono" placeholder="telefono" required>
                  </div>

                  <div class="col-md-4">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" v-model="invitado.email" placeholder="name@example.com">
                  </div>
              </div>
              
              <div class="row mb-3">
                <div class="col-md-4">
                  <label for="matricula" class="form-label">Matricula</label>
                  <input type="text" class="form-control" id="matricula" v-model="invitado.matricula" placeholder="matricula" required>
                </div>

                <div class="col-md-4">
                  <label for="empresa" class="form-label">Empresa</label>
                  <!-- <input type="text" class="form-control" id="empresa" v-model="invitado.empresa" placeholder="empresa" required> -->
                </div>
              </div>

              <!-- autorizacion -->

             


              
              


          </div>          

           <!-- botones de guardar y cancelar -->
          <div class="container border rounded mb-0 p-2 btn btn-secondary" style="align-items: center; justify-content: center; display:flex">                    
              <div class="d-inline me-1"><button type="submit" class="btn btn-warning" @click.prevent="crearInvitado">Guardar Invitado</button></div>
              <button type="submit" class="btn btn-warning" @click="$router.go(-1)">Cancelar</button>
          </div> 

          <!-- table para listar todos los invitados -->
          <div class="container alert alert-light border rounded mb-0 p-2" style="align-items: center; justify-content: center; display:flex">
            
          </div>
          
        </form>
        
      </Modelo>
    </div>
</template>


      
      
  
  
  