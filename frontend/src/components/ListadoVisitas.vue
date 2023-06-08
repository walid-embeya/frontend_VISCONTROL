<script>
import Modelo from './Model.vue'
import Visita from './Visita.vue'
import { mapActions, mapState } from 'pinia'
import { visitasStore } from '@/stores/visitas'

import { timestampToFecha, timestampToHora } from '@/utils/utils'


export default {
  components: { Modelo, Visita },   ///// registro local del componente "Visita"
  data() {
    return {
      visitas: [],
      ordenAscendente: true, 
      ordenarPor: 'fechaInicio' 
    }
  },
  computed:{
    ...mapState(visitasStore, [ 'visitasApi', 'visitasPendientesApi' ]),

    visitasPendientes() {
        if (this.visitas) {
            let fechaSistema = Date.now()                   
            return this.visitas.filter(v => new Date(v.fechaInicio) >= fechaSistema)   
        } 
        else  
            return null        
    },

  },

  methods: {
    ...mapActions(visitasStore, [ 'getVisitasApi' ]),
    
    sortByStartDate() {
        this.ordenarPor = 'fechaInicio';
        this.ordenAscendente = !this.ordenAscendente; // Cambiar el orden ascendente/descendente

        const orden = this.ordenAscendente ? 1 : -1 // Determinar el orden ascendente o descendente
        
        return this.visitas.sort((a, b) => {
            if (a[this.ordenarPor] < b[this.ordenarPor]) return -1 * orden
            if (a[this.ordenarPor] > b[this.ordenarPor]) return 1 * orden
            return 0;
        })
    },
    sortByActivity() {
        this.ordenarPor = 'actividad';
        this.ordenAscendente = !this.ordenAscendente; // Cambiar el orden ascendente/descendente

        const orden = this.ordenAscendente ? 1 : -1 // Determinar el orden ascendente o descendente
            
        return this.visitas.sort((a, b) => {
              if (a[this.ordenarPor] < b[this.ordenarPor]) return -1 * orden
              if (a[this.ordenarPor] > b[this.ordenarPor]) return 1 * orden
              return 0;
        })
    },

  },
  
  async created() {               

      await this.getVisitasApi()

      this.visitas = this.visitasApi
      // console.log("Lista Visitas API desde componente: ", this.visitasApi)

      // console.log("Lista Visitas pendientes API : ", this.visitasPendientes)

      

  }
}
</script>


<template>
    
    <Modelo titulo="LISTADO VISITAS">

        <div class="container pt-3">
            
            <div class="row">            
                <div class="col-md-3">
                    <label class="radio-inline me-5 fs-5 fw-bold mt-2 mb-2 ">
                        <input class="me-2" type="radio" name="optradio" checked @click="visitas=visitasPendientes">PENDIENTES
                    </label> 
                    <label class="radio-inline me-4 fs-5 fw-bold">
                        <input class="me-2" type="radio" name="optradio" @click="visitas=visitasApi">GLOBAL
                    </label>                  
                </div>

                <div class="col-md-6 d-flex justify-content-center p-3 mb-3">
                  <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                      <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" @click="sortByStartDate" checked>
                      <label class="btn btn-outline-info mt-1 fw-bold" for="btnradio1">Ordenar por fecha de inicio</label>

                      <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off" @click="sortByActivity">
                      <label class="btn btn-outline-info mt-1 fw-bold" for="btnradio2">Ordenar por actividad</label>
                  </div>
                </div>
              
                <div class="col-md-3 d-flex justify-content-end p-3 mb-3">
                      <router-link :to="{ name: 'nuevavisita' }" class="btn btn-success my-1">
                            <font-awesome-icon :icon="['fas', 'circle-plus']" class="me-2"/>Nueva Visita</router-link>             
                </div>
            </div>    
        </div>
        
        <div v-if="visitas">                    
            <Visita v-for="vis of visitas" :visita="vis" class="py-3 px-3 mb-1"></Visita>
        </div>
      
        <div v-else class="container border rounded mb-0 alert alert-warning">
            <p class="text-center fw-bold fs-5">No hay visitas</p>
        </div>   
        <!-- </div>     -->
     </Modelo>
</template>


<style></style>