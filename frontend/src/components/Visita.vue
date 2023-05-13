<script>
import { mapState, mapActions } from 'pinia'
import { invitadosStore } from '@/stores/invitados'
import { timestampToFecha, timestampToHora } from '@/utils/utils'

export default {
  props: ['visita'],  ///// aqui, la opcion "props" es un array of atributes string
  data() {
    return {
    }
  },
  computed: {
    ...mapState(invitadosStore, ['invitados']),
    fechaInicio() {
      return timestampToFecha(new Date(this.visita.fechaInicio))
    },
    horaInicio() {
      return timestampToHora(new Date(this.visita.fechaInicio))
    },
    fechaFin() {
      return timestampToFecha(new Date(this.visita.fechaFin))
    },
    horaFin() {
      return timestampToHora(new Date(this.visita.fechaFin))
    },
  },
  methods: {
    ...mapActions(invitadosStore, ['getInvitadoPorMatricula']),

    autorizacion(matricula) {
      return this.getInvitadoPorMatricula(matricula).autorizacion.fechaInicio == null ? false : true;
    },
  },
}

</script>

<template>
  <div class="container alert alert-dark">
            <!-- 
              <div class="d-inline me-4 fs-5 fw-bold">Fecha inicio</div><span class="fs-5 me-5">{{ visita.fechaInicio }}</span> 

              <div class="d-inline me-4 fs-5 fw-bold">Fecha fin</div><span class="fs-5 me-5"> {{ visita.fechaFin }}</span> <br>

              <div class="d-inline me-4 fs-5 fw-bold">Actividad</div><span class="fs-5"> {{ visita.actividad }}</span> <br>

              <div class="d-inline me-5 fs-5 fw-bold">Actuación</div><span class="fs-5"> {{ visita.actuacion }}</span> 
            -->

          <div class="row">
              <div class="col-md-2">
                <div class="fs-5 fw-bold">Fecha inicio</div>
              </div>
              <div class="col-md-3">
                <span class="fs-5">{{ fechaInicio }}</span>
              </div>
              <div class="col-md-2">
                <div class="fs-5 fw-bold">Hora Inicio</div>
              </div>
              <div class="col-md-2">
                <span class="fs-5">{{ horaInicio }}</span>
              </div>              
          </div>

          <div class="row">              
              <div class="col-md-2">
                <div class="fs-5 fw-bold">Fecha fin</div>
              </div>
              <div class="col-md-3">
                <span class="fs-5"> {{ fechaFin }}</span> 
              </div>
              <div class="col-md-2">
                <div class="fs-5 fw-bold">Hora fin</div>
              </div>
              <div class="col-md-2">
                <span class="fs-5"> {{ horaFin }}</span> 
              </div>
          </div>

          <div class="row">
              <div class="col-md-2">
                <div class="fs-5 fw-bold">Actividad</div>
              </div>
              <div class="col-md-3">
                <span class="fs-5">{{ visita.actividad }}</span>
              </div> 
              
              <div class="col-md-2">
                <div class="fs-5 fw-bold">Actuación</div>
              </div>
              <div class="col-md-5">
                <span class="fs-5"> {{ visita.actuacion }}</span> 
              </div>
          </div>         

          <div class="accordion accordion-flush alert alert-secondary mt-2 mb-1" id="detallesVisita">
            <div class="accordion-item">
              <!-- <h2 class="accordion-header"> -->
              <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                :data-bs-target="`#flush-collapse${visita.id}`" aria-expanded="false"
                :aria-controls="`flush-collapse${visita.id}`" style="color: darkred;">
                <font-awesome-icon :icon="['fas', 'list']" style="color: #a51d2d;" class="me-2" />Mostrar lista de invitados
              </button>
              <!-- </h2> -->

              <div :id="`flush-collapse${visita.id}`" class="accordion-collapse collapse" data-bs-parent="#detallesVisita">
                <div class="accordion-body">
                  <table class="table table-striped table-hover">
                    <thead class="alert alert-dark">
                      <tr>
                        <th scope="col">Matricula</th>
                        <th scope="col">Nombre</th>
                        <th scope="col">Apellidos</th>
                        <th scope="col">Empresa</th>
                        <th scope="col" class="th-aut">Autorizado</th>
                      </tr>
                    </thead>
                    <tbody>

                      <!-- <Invitado v-for="invitado of visita.invitados" :invitado="invitado" />  -->

                      <tr v-for="invit in visita.invitados">
                        <th scope="row">{{ invit.matricula }}</th>
                        <td>{{ invit.nombre }}</td>
                        <td>{{ invit.apellidos }}</td>
                        <td>{{ invit.empresa }}</td>
                        <td v-if="autorizacion(invit.matricula)"><input type="checkbox" id="autorizado" name="autorizado"
                            checked></td>
                        <td v-else><input type="checkbox" id="noAutorizado" name="noAutorizado"></td>

                        <td>
                          <router-link :to="{ name: 'invitado', params: { identificador: invit.matricula } }"><font-awesome-icon
                              :icon="['fas', 'circle-info']" size="lg" style="color: #77767b;" /></router-link>
                        </td>

                        <!-- <td>
                          <router-link :to="`/invitados/${invit.matricula}`"> <font-awesome-icon :icon="['fas', 'circle-info']"
                                      size="lg" style="color: #77767b;" /></router-link>
                        </td> -->


                      </tr>
                    </tbody>
                  </table>

                            <!-- <ul>
                              <li v-for="invit in visita.invitados" >                            
                                  <span class="me-3">{{ invit.dni }}</span> 
                                  <span class="me-3">{{ invit.nombre }}</span> 
                                  <span class="me-3">{{ invit.apellidos }}</span>
                              </li>
                            </ul> -->

                </div>
              </div>
            </div>
          </div>
  </div>
</template>

<style>
.th-aut {
  text-align: left;
  vertical-align: middle;
}</style>