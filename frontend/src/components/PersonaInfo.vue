<script>
import Modelo from './Model.vue'
import { mapActions, mapState } from 'pinia'
import { personasStore } from '@/stores/personas'
import ProgressSpinner from 'primevue/progressspinner'
import { dateToString, timestampToFecha, timestampToHora } from '@/utils/utils'


export default {
  components: { Modelo, ProgressSpinner },   ///// registro local de los componentes
  data() {
    return { 

    }
  },
  computed: {
    ...mapState(personasStore,  [ 'personaApi', 'visitasPersona' ]),

    esInvitado() {
      return this.personaApi.tipo == 'Invitado' ? true : false
    },

    tituloComponente() {
      return this.esInvitado ? 'DETALLES DEL INVITADO' : 'DETALLES DEL ANFITRIÓN'
    },

    fechaHoy() {
      return new Date()
    },

   
  },

  methods: {
    ...mapActions(personasStore, [ 'getPersonaPorId', 'getVisitasPersona' ]), 
   
    esPendiente(visita) {
      const hoy = new Date()
      return new Date(visita.fechaFin) > hoy ? true : false
    },

    // estadoVisita(visita) {
    //   return new Date(visita.fechaFin) > fechaHoy ? 'Pendiente' : 'Hecha'
    // },

    fecha(d) {
      return timestampToFecha(new Date(d))
    },

    hora(d) {
      return timestampToHora(new Date(d))
    },
  },

 async mounted()  {
  console.log("mounted id = ", this.$route.params.identificador)
  //this.getPersonaPorId(this.$route.params.identificador)  
  await this.getVisitasPersona(this.$route.params.identificador)
},

 async created() {

    // console.log("Created id = ", this.$route.params.identificador)

    //this.getPersonaPorId(this.$route.params.identificador)   
    //await this.getVisitasPersona(this.$route.params.identificador)
    
    console.log("json", JSON.stringify(visitasPersona, null, " "))
}, 

}
</script>

<template>
    <Modelo :titulo=tituloComponente>
      
      <div v-if="personaApi" class="alert alert-dark p-2 mb-0">

          <!-- informaciones comunes -->
          <div class="container border rounded mb-1 alert alert-secondary">
              <div class="row mt-1 mb-4">
                  <div class="col-md-4">                      
                      <label class="fs-5">DNI<span class="ms-3"><b>{{ personaApi.dni }}</b></span></label>
                  </div>
                  <div class="col-md-4">                      
                      <label class="fs-5">Nombre<span class="ms-3"><b>{{ personaApi.nombre }}</b></span></label>
                  </div>
                  <div class="col-md-4">                      
                      <label class="fs-5">Apellidos<span class="ms-3"><b>{{ personaApi.apellidos }}</b></span></label>                                       
                  </div>
              </div>   
              <div class="row">
                  <div class="col-md-4">                     
                    <label class="fs-5">Telefono<span class="ms-3"><b>{{ personaApi.telefono }}</b></span></label>                
                  </div>
                  <div class="col-md-6">                        
                      <label class="fs-5">Email<span class="ms-3"><b>{{ personaApi.email }}</b></span></label> 
                  </div>               
              </div>         
          </div>
        
          <!-- datos invitado -->
          
          <div v-if="esInvitado" class="container border rounded mb-1 alert alert-secondary">

              <div class="row mt-1 mb-4">
                  <div class="col-md-4">                        
                      <label class="fs-5">Matricula<span class="ms-3"><b>{{ personaApi.matricula }}</b></span></label>
                  </div>
                  <div class="col-md-6">                      
                      <label class="fs-5">Empresa<span class="ms-3"><b>{{ personaApi.empresa }}</b></span></label>            
                  </div>
              </div>
                                
              <div class="row">  
                  <div class="col-md-4">
                    <div v-if="personaApi.autorizacion" class="form-check form-check-inline">
                        <input class="form-check-input" type="checkbox" id="autorizacion" value="option1" checked disabled>
                        <label class="form-check-label" for="autorizacion">Autorizado</label>
                    </div>
                    <div v-else class="form-check form-check-inline">
                        <input class="form-check-input" type="checkbox" id="autorizacion" value="option1" disabled>
                        <label class="form-check-label" for="autorizacion">Autorizado</label>
                    </div>                    
                  </div>
                  
                  <div v-if="personaApi.autorizacion" class="col-md-4">
                      <label class="fs-5">Inicio autorización<span class="ms-3"><b>{{ fecha(personaApi.inicioAut) }}</b></span></label>                                                                                            
                  </div>
                  <div v-if="personaApi.autorizacion" class="col-md-4">                                                                                          
                      <label class="fs-5">Fin autorización<span class="ms-3"><b>{{ fecha(personaApi.inicioAut) }}</b></span></label>            
                  </div>
              </div>              
          </div>


          <!-- datos anfitrion -->
          <div v-else class="container border rounded mb-1 alert alert-secondary">
              <div class="row mt-1">
                  <div class="col-md-3">                      
                      <label class="fs-5">NIP<span class="ms-3"><b>{{ personaApi.nip }}</b></span></label>          
                  </div>
                  <div class="col-md-5">                      
                      <label class="fs-5">Area<span class="ms-3"><b>{{ personaApi.area }}</b></span></label> 
                  </div>
                  <div class="col-md-4">                     
                    <label class="fs-5">Role<span class="ms-3"><b>{{ personaApi.role }}</b></span></label>                   
                  </div>
              </div> 
          </div>
          
          <div v-if="visitasPersona" class="container border rounded mb-0 alert alert-light">   

              <table class="table table-striped table-hover">
                <thead class="alert alert-primary" style="background-color:  rgb(4, 33, 117);">
                    <tr class="cabezera">
                      <th scope="col">Fecha/Hora Inicio</th>
                      <th scope="col">Fecha/Hora Fin</th>
                      <th scope="col">Actividad</th>
                      <th scope="col">Estado</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr class="linea" v-for="visita of visitasPersona" >
                      <th scope="row">{{ fecha(visita.fechaInicio) }} a las {{ hora(visita.fechaInicio) }}</th>
                      <td>{{ fecha(visita.fechaFin) }} a las {{ hora(visita.fechaFin) }}</td>
                      <td>{{ visita.actividad }}</td>

                      <td v-if="esPendiente(visita)">Pendiente</td>
                      <td v-else>Hecha</td>

                      <!-- <td v-if="new Date(visita.fechaFin) > fechaHoy">Pendiente</td>
                      <td v-else>hecha</td>  -->

                      <!--<td>{{ estadoVisita(visita) }}</td>  -->

                    </tr>
                  </tbody>
              </table>                            
          </div>

          <div v-else class="container border rounded mb-0 alert alert-warning">

              <p class="text-center fw-bold fs-5">Esta persona no tiene visitas</p>

          </div> 

          <pre>{{ JSON.stringify(visitasPersona, null, " ") }}</pre>

      </div> 

      <div v-else class="text-center">
          <ProgressSpinner />
      </div>
             
      <div class="d-flex justify-content-center border rounded">
        <button style="background-color: rgb(120, 124, 58); color: #ffffff;" @click="this.$router.push({ name: 'home' })" class="btn my-2 me-2">
          <font-awesome-icon icon="fa-solid fa-house" style="color: #ffffff;" class="me-2"/>Home

        </button> 

        <button style="background-color: rgb(120, 124, 58); color: #ffffff;"  @click="this.$router.go(-1)" class="btn my-2 me-2">
            <font-awesome-icon :icon="['fas', 'circle-left']" size="lg" style="color: #f6f5f4;" class="me-2"/>Atrás
        </button>               
      </div>        
    </Modelo>                
</template>

<style>
  .cabezera {
    text-align: center;
    color: white;
  }

  .linea {
    text-align: center;
    color: black;
  }

  input[type="checkbox"]:disabled + label {
        /* Ajoutez ici les styles que vous souhaitez appliquer */
        color: black;
        font-style: normal;
        font-weight: bold;

  }

  [type="checkbox"]:checked + label:after{
     background:rgb(69, 180, 36);
  } 
</style>