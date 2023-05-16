<script>
import Modelo from './Model.vue'
import Invitado from './Invitado.vue'
import { mapState } from 'pinia'
import { invitadosStore } from '@/stores/invitados'


export default {
  components: { Invitado, Modelo },                   ///// registro local de los componentes "Modelo" y "Invitado"
  data()  {
    return {
        //invitados: invitadosJson._embedded.invitados
    }
  },  
  computed: {
      ...mapState(invitadosStore, [ 'invitados' ])  
  }
}
</script>

<template>
    <div>  
        <Modelo titulo="LISTADO INVITADOS">
          <router-link :to="{ name: 'nuevoinvitado' }" class="btn btn-secondary btn-lg my-2">
              <font-awesome-icon :icon="['fas', 'circle-plus']" class="me-2"/>Nuevo Invitado</router-link>    

          <table class="table table-striped table-hover">
              <thead class="alert alert-primary">
                <tr>
                  <th scope="col" style="text-align: center;">DNI</th>
                  <th scope="col" style="text-align: center;">Nombre</th>
                  <th scope="col" style="text-align: center;">Apellidos</th>
                  <!-- <th scope="col">Empresa</th> -->
                  <th scope="col" style="text-align: center;">Empresa</th>
                  <th class="th-aut" scope="col">Autorizado</th>
                </tr>
              </thead>
              <tbody>
                <Invitado v-for="invitado of invitados" :invitado="invitado" />      
              </tbody>
          </table>
        </Modelo> 
    </div>
</template>



<style>
  .th-aut {
    text-align: center;   
  } 
</style>
