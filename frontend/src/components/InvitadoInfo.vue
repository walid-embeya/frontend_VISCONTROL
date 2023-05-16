<script>
import Modelo from './Model.vue'
import { mapActions } from 'pinia'
import { invitadosStore } from '@/stores/invitados'


export default {
  components: { Modelo },   ///// registro local de los componentes
  data() {
    return { }
  },
  computed: {
    invitado() {
        // return this.invitados.find(i => i.matricula == this.$route.params.matricula)
        //return this.getInvitadoPorMatricula(this.$route.params.matricula)

        return this.getInvitadoPorId(this.$route.params.identificador)      
    }, 

    autorizacion() {
       return this.invitado.autorizacion.fechaInicio ==  null ? false : true;
    }

  },
  methods: {
    ...mapActions(invitadosStore, ['getInvitadoPorId'])
  },
  created() {
    // console.log(this.$route.params.identificador)
  }

}
</script>

<template>
  <div>
    <Modelo titulo="DETALLES DEL INVITADO">

      <div class="container mt-5 alert alert-dark">
        
          <div class="row my-4">
              <div class="col-md-3">
                  <div class="fs-5">DNI <b>{{ invitado.dni }}</b></div>
              </div>
              <div class="col-md-6">
                  <div class="fs-5">Nombre y apellidos <b>{{ invitado.nombre }}  {{ invitado.nombre }}</b></div>
              </div>
              <div class="col-md-3">
                  <div class="fs-5">Matricula <b>{{ invitado.matricula }}</b></div>                  
              </div>
          </div>   

          <div class="row my-4">
              <div class="col-md-3">
                  <div class="fs-5">Empresa <b>{{ invitado.empresa }}</b></div>            
              </div>
              <div class="col-md-6">
                  <div class="fs-5">Email <b>{{ invitado.email }}</b></div>    
              </div>
              <div class="col-md-3">
                <div class="fs-5">Telefono <b>{{ invitado.telefono }}</b></div>                    
              </div>
          </div>   

          <div v-if="autorizacion" class="row my-1">
              <div class="row my-1">
                <div class="col-md-12">
                    <span class="fs-4"><b>autorizacion</b></span>
                </div>
              </div>

              <div class="row">   
                <div class="col-md-3">
                  <div class="fs-5">Fecha Inicio <b>{{ invitado.autorizacion.fechaInicio }}</b></div>                 
                </div>
                <div class="col-md-6">
                  <div class="fs-5">Fecha Fin <b>{{ invitado.autorizacion.fechaFin }}</b></div>   
                </div>
                <div class="col-md-3">
                  <div class="fs-5">Tipo <b>{{ invitado.autorizacion.tipo }}</b></div>   
                </div>
              </div>   
           </div>
      </div> 

      <!-- <pre>{{ JSON.stringify(invitado, null, " ") }}</pre> -->

    </Modelo>

    <router-link to="/invitados" class="btn btn-danger" @click="$router.go(-1)">
      <font-awesome-icon :icon="['fas', 'circle-left']" size="lg" style="color: #f6f5f4;" class="me-2" />Atrás
    </router-link>
                    
  </div>
</template>