<script>
import Visita from './Visita.vue'
import Modelo from './Model.vue'
import { mapState } from 'pinia'
import { visitasStore } from '@/stores/visitas'
// import visitasJson from '@/assets/visitas.json'

// Para PrimeVue
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';

export default {
    components: { Visita, Modelo, DataTable, Column },   ///// registro local del componente "Visita"

    data() {
        return {
            // visitas: null
        };
    },
    computed:{
    ...mapState(visitasStore, [ 'visitas' ])  
    },
    methods: {
        showDetails(id) {
            this.$emit('show-info', id); // émettre un événement 'show-info' avec l'ID de la ligne
        }
    },
    mounted() {
        // this.visitas = visitasJson._embedded.visitas;
    }
};
</script>


<template>
    <Modelo titulo="LISTADO VISITAS">
        <div class="card">
            <DataTable :value="visitas" tableStyle="min-width: 50rem">
                <Column field="fechaInicio" header="Fecha Inicio" sortable style="width: 25%"></Column>
                <Column field="fechaFin" header="fecha Fin" sortable style="width: 25%"></Column>
                <Column field="actividad" header="Actividad" sortable style="width: 25%"></Column>
                <Column field="actuacion" header="Actuacion" sortable style="width: 25%"></Column>
              
                <Column header="Actions" style="width: 20%">
                    <template #body="rowData">
                       <Button icon="pi pi-info-circle" @click="showDetails(rowData)" />
                       <!-- <router-link :to="`/visitas/${invitado.matricula}`"><font-awesome-icon :icon="['fas', 'circle-info']" size="lg" style="color: #77767b;" /></router-link>  -->
                    </template>
                </Column>
            </DataTable>
        </div>

        
    </Modelo>
</template>

