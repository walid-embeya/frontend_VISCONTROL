<script>
import Modelo from './Model.vue'
import Persona from './Persona.vue'
import { mapState, mapActions } from 'pinia'
import { personasStore } from '@/stores/personas'

import { Modal } from '~bootstrap'
import { nextTick } from 'vue'


export default {
  components: { Persona, Modelo },                   ///// registro local de los componentes "Modelo" y "Invitado"
  data()  {
    return {
      opcionElegida: 'anfitrion'
    }
  },
  computed: {
      ...mapState(personasStore,  [ 'personasApi' ]),
  }, 
  methods: {
    ...mapActions(personasStore, ['getPersonasApi']),
   
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
  },
  async created() {      
       await  this.getPersonasApi()      
      },
}
</script>

<template>
      <Modelo titulo="LISTADO PERSONAS">                            
            <!-- Button Modal -->
            <div class="d-flex justify-content-start">
                <button @click="mostrarModal" type="button" class="btn btn-danger mt-2" data-bs-toggle="modal" data-bs-target="#addPersona">
                    <font-awesome-icon :icon="['fas', 'circle-plus']" class="me-2"/>Nueva Persona
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
                            <input class="form-check-input" type="radio" name="radios" id="anfitrion" value="anfitrion" v-model="opcionElegida">
                            <label class="form-check-label" for="anfitrion">
                                Anfitrion
                            </label>
                          </div>
                          
                          <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="radios" id="invitado" value="invitado" v-model="opcionElegida">
                            <label class="form-check-label" for="invitado">
                                Invitado
                            </label>
                          </div>
                  </div>    
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Anular</button>
                    
                    <button @click="mostrarComponente" type="button" class="btn btn-success my-2" data-bs-dismiss="modal">validar</button>
                  </div>  
                </div>
              </div>
            </div>     


            <table class="table table-striped table-hover mt-2">
                <thead class="alert alert-primary">
                  <tr class="header">
                    <th scope="col">DNI</th>
                    <th scope="col">Nombre</th>
                    <th scope="col">Apellidos</th>      
                    <th scope="col">Tipo</th>                   
                  </tr>
                </thead>
                <tbody>                    
                    <Persona v-for="persona of personasApi" :persona="persona" />      
                </tbody>
            </table>

        </Modelo> 
</template>


<style>

  .header {
    text-align: center;   
  } 
</style>
