<script>
import Modelo from './Model.vue'
import Visita from './Visita.vue'
import { mapActions, mapState } from 'pinia'
import { visitasStore } from '@/stores/visitas'
// import visitasJson from '@/assets/visitas.json'

export default {
  components: { Modelo, Visita },   ///// registro local del componente "Visita"
  data() {
    return {
      visitas: [],
      visitasPendientes: [],
      ordenAscendente: true, 
      ordenarPor: 'fechaInicio' 
    }
  },
  computed:{
    ...mapState(visitasStore, { visitasGlobales: 'visitas' }),
  },

  methods: {
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

      //   this.empresas.sort((a, b) => a.nombre.localeCompare(b.nombre));
      // return this.empresas;
    }
  },

  created() {
    this.visitas = this.visitasGlobales  

    let dateSysteme = Date.now();
    this.visitasPendientes = this.visitas.filter(v => new Date(v.fechaInicio) >= dateSysteme )
  }
}
</script>


<template>
  <div>
     <Modelo titulo="LISTADO VISITAS">
        <div v-if="visitas" class="container mt-5 mb-1">

          <div class="row">
              <div class="col-md-3">
                    <label class="radio-inline me-4 fs-5 fw-bold">
                      <input class="me-2" type="radio" name="optradio" checked @click="visitas = visitasGlobales">GLOBAL
                    </label>
                    <label class="radio-inline me-5 fs-5 fw-bold">
                      <input class="me-2" type="radio" name="optradio" @click="visitas=visitasPendientes">PENDIENTES
                    </label>   
              </div>

              <div class="col-md-6 text-center">
                  <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                    <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" @click="sortByStartDate" checked>
                    <label class="btn btn-outline-danger" for="btnradio1">Ordenar por fecha de inicio</label>

                    <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off" @click="sortByActivity">
                    <label class="btn btn-outline-danger" for="btnradio2">Ordenar por actividad</label>
                  </div>
              </div>
             
              <div class="col-md-3 text-center">
                    <router-link :to="{ name: 'nuevavisita' }" class="btn btn-secondary btn-lg my-1" style="background-color: rgb(102, 102, 105);">
                          <font-awesome-icon :icon="['fas', 'circle-plus']" class="me-2"/>Nueva Visita</router-link>             
              </div>
          </div>   
        </div>
        <div v-else>No hay visitas</div>
      
        <Visita v-for="vis of visitas" :visita="vis" class="py-2 px-2 mb-1"></Visita>
     </Modelo>
  </div> 
</template>


<style></style>