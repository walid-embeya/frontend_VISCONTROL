<script>
import Modelo from '@/components/Model.vue'
import { mapActions, mapState } from 'pinia'
import { visitasStore } from '@/stores/visitas'
import { invitadosStore } from '@/stores/invitados'
import Calendar from 'primevue/calendar'


export default {
    components: { Modelo, Calendar },   ///// registro local de los componentes
    //   props: ['invitado'],
    data() {
        return {
            listaVisitas: [],   //lista de todas las visitas
            actividades: [],    
            
            // la visita que queremos añadir
            visita: {
                id: null,
                fechaInicio: null,
                fechaFin: null,
                actividad: null,
                actuacion: null,
                invitados: []
                },

            invitadosVisita: [],

            invitadosElegidos: [], 

            nuevoInvitado: {
                matricula: null,
                nombre: null,
                apellidos: null,
                empresa: null
                }            
            }
        },

    computed: {
        ...mapState(visitasStore, ['visitas', 'generarIdVisita']),
        ...mapState(invitadosStore, ['invitados']),
    },

    methods: {
        ...mapActions(invitadosStore, ['getInvitadoPorMatricula']),
       
        crearVisita() {

  
        },        
        anadirInvitado() {generarIdVisita

            // invitadosElegidos.push()

            // this.invitadosVisita.push(Object.assign({}, this.nuevoInvitado))
            // this.nuevoInvitado.matricula = ''
            // this.nuevoInvitado.nombre = ''
            // this.nuevoInvitado.apellidos = ''
            // this.nuevoInvitado.empresa = ''
        },
        borrarInvitado(id) {

        },
        saveTimestamp() {
            const timestamp = this.visita.fechaInicio.getTime();
            // jsonData.timestamp = timestamp;
            // Enregistrer les modifications dans le fichier JSON
        },
    },

    created() {
        this.listaVisitas = this.visitas

        console.log("max store", this.generarIdVisita)

        ///////// recuperar lista de actividades
        let tab1 = []
        this.visitas.forEach(visita => {
            if (!tab1.includes(visita.actividad)) {
                tab1.push(visita.actividad);
            }  
        })

        this.actividades = tab1.map((valeur, index) => {
            return { id: index + 1, actividad: valeur };
        })
    }
}
</script>


<template>
    <div>
        <Modelo titulo="CREACIÓN NUEVA VISITA">
            <form class="p-1 border rounded" style="background-color: rgb(143, 170, 211);">
                <!-- datos visita -->                                                                          
                
                <div class="container alert alert-dark border rounded mb-1">
           
                    <div class="row">
                        <div class="col-md-3">
                            <div class="fs-5 fw-bold">Fecha inicio</div>
                        </div>
                        <div class="col-md-3">
                            <div class="fs-5 fw-bold">Hora Fin</div>
                        </div>                             
                        <div class="col-md-5">
                            <div class="fs-5 fw-bold">Actividad</div>
                        </div>   
                    </div>

                    <div class="row mb-1">                
                        <div class="col-md-3">
                            <Calendar v-model="visita.fechaInicio" :show-time="true" :style="{'font-size': '14px', 'width': '200px', 'height': '35px'}" ></Calendar>
                        </div>
                        <div class="col-md-3">
                            <Calendar v-model="visita.fechaFin" :show-time="true" :style="{'font-size': '14px', 'width': '200px', 'height': '35px'}" ></Calendar>
                        </div> 
                        <div class="col-md-6">
                            <select class="form-select" v-model="visita.actividad">
                                <!-- <option v-for="vis of visitas" :value="vis.id">{{ vis.actividad }}</option> -->
                                <option v-for="activ of actividades" :value="activ.id">{{ activ.actividad }}</option>
                            </select>
                        </div>                                     
                    </div>

                    <div class="row">
                        <div class="col-md-12">
                            <label for="actuacion" class="form-label my-1 fs-5 fw-bold">Actuación</label>
                            <textarea class="form-control" id="direccion" rows="3" v-model="visita.actuacion"
                                placeholder="actuacion de la visita"  style="resize: none;"></textarea>
                        </div>
                    </div>                    
                </div>
                                
                <!-- 
                <b><label class="fs-5 me-3 my-3 text-center">Date de fin</label></b>                        
                <input type="date" class="btn btn-light me-5" v-model="visita.fechaFin" required> 
                -->
                
               
                <!-- datos visitantes -->
                <div class="container alert alert-dark border rounded mb-1">
              
                    <div class="row form-group mb-3">                    
                        <div class="col-md-2">
                            <label for="nombreinvitado" class="col-form-label">Nombre del invitado</label>
                        </div>
                        <div class="col-md-4">                            
                            <select class="form-select" aria-label=".form-select-sm example" v-model="nuevoInvitado.matricula">                            
                                <!-- <option value="0" selected="">Seleccione un invitado</option> -->
                                <option v-for="invit of invitados" :value="invit.matricula">{{ invit.matricula }} - {{ invit.nombre }} {{ invit.apellidos }}</option>
                            </select>
                        </div>                             
                        <div class="col-md-2">                                                                                                                                                                 
                            <button type="button" @click.prevent="anadirInvitado" class="btn btn-secondary" data-bs-toggle="tooltip" data-bs-placement="right" title="hacer clic para añadirlo a la lista de invitados de esta visita">
                                <font-awesome-icon :icon="['fas', 'circle-plus']" size="lg" style="color: #ffffff;" class="me-2"/>añadir
                            </button> 
                        </div>   
                    </div>

                    <div class="row">
                        <table class="table table-dark table-hover table-striped">
                       
                            <thead>
                                <tr>
                                    <th scope="col">Matricula</th>
                                    <th scope="col">Nombre</th>
                                    <th scope="col">Apellidos</th>
                                    <th scope="col">Empresa</th>                        
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="elemento of invitadosVisita">                                    
                                    <td >{{ elemento.nombre }}</td>
                                </tr>                                
                            </tbody>
                        </table>
                    </div>
                   

                   
                </div>
            
                    
                <div class="container border rounded mb-0 p-2" style="background-color:  rgb(5, 5, 107); align-items: center; justify-content: center; display:flex">
                    
                    <div class="d-inline me-4"><button type="submit" class="btn btn-light" @click.prevent="crearVisita">Crear visita</button></div>
                    <button type="submit" class="btn btn-light" @click.prevent="crearVisita">Cancelar</button>
           </div>

                
            </form>
        </Modelo>
    </div>
</template>




<!-- 
<div class="visita-component">
  <div class="fechas">
    <div class="fechainicio">
      <label for="fechainicio">Fecha de inicio:</label>
      <input type="date" id="fechainicio" name="fechainicio" value="" required>
    </div>
    <div class="fechafin">
      <label for="fechafin">Fecha de fin:</label>
      <input type="date" id="fechafin" name="fechafin" value="" required>
    </div>
  </div>
  <div class="actividad">
    <label for="actividad">Actividad:</label>
    <textarea id="actividad" name="actividad" required></textarea>
  </div>
  <div class="visitantes">
    <table>
      <thead>
        <tr>
          <th>Matrícula</th>
          <th>Nombre</th>
          <th>Apellidos</th>
          <th>Empresa</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td><input type="text" id="matricula1" name="matricula1" required></td>
          <td><input type="text" id="nombre1" name="nombre1" required></td>
          <td><input type="text" id="apellidos1" name="apellidos1" required></td>
          <td><input type="text" id="empresa1" name="empresa1" required></td>
        </tr>
        <tr>
          <td><input type="text" id="matricula2" name="matricula2" required></td>
          <td><input type="text" id="nombre2" name="nombre2" required></td>
          <td><input type="text" id="apellidos2" name="apellidos2" required></td>
          <td><input type="text" id="empresa2" name="empresa2" required></td>
        </tr>
      
      </tbody>
    </table>
  </div>
</div> -->





