<script>
import Visita from './Visita.vue'
import Modelo from './Model.vue'
import { mapState, mapActions } from 'pinia'
import { visitasStore } from '@/stores/visitas'
// Para PrimeVue
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';


export default {
    components: { Visita, Modelo, DataTable, Column },   ///// registro local de los componentes

    data() {
        return {
      //       visitas: null
        };
    },
    computed:{
        ...mapState(visitasStore, [ 'visitas', 'visitasApi' ]),

    },
    methods: {
        ...mapActions(visitasStore, ['getVisitasApi']),
        
        
        // showDetails(id) {
        //     this.$emit('show-info', id); // émettre un événement 'show-info' avec l'ID de la ligne
        // }
    },
    mounted() {
        // this.visitas = visitasJson._embedded.visitas;
    },



    created() {
        
         this.getVisitasApi()
         
         console.log(this.visitasApi)

          //console.log("lista visitas : ", JSON.stringify(this.getVisitas))
     }

};
</script>


<template>
    <Modelo titulo="LISTADO VISITAS">
        <div v-if="visitasApi" class="card">
            <DataTable :value="visitasApi" tableStyle="min-width: 50rem">
                <Column field="fechaInicio" header="Fecha Inicio" sortable style="width: 25%"></Column>
                <Column field="fechaFin" header="fecha Fin" sortable style="width: 25%"></Column>
                <Column field="actividad" header="Actividad" sortable style="width: 25%"></Column>
                <Column field="actuacion" header="Actuacion" sortable style="width: 25%"></Column>
              
                <!-- <Column header="Actions" style="width: 20%">
                    <template #body="rowData">
                       <Button icon="pi pi-info-circle" @click="showDetails(rowData)" />
                       <router-link :to="`/visitas/${invitado.matricula}`"><font-awesome-icon :icon="['fas', 'circle-info']" size="lg" style="color: #77767b;" /></router-link> 
                    </template>
                </Column> -->
            </DataTable>

            <pre>{{ JSON.stringify(visitasApi, null, " ") }}</pre>
        </div>

       

        
    </Modelo>
</template>

