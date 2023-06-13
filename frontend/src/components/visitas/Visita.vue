<script>
import { mapActions } from 'pinia'
import { visitasStore } from '@/stores/visitas'
import { timestampToFecha, timestampToHora } from '@/utils/utils'


export default {
  props: ['visita'],
  // components: { Accordion, AccordionTab },   ///// registro local de los componentes
  emits: ['borrarVisita', 'editarVisita', 'mostrarAnfitrion'],
  data() {
    return {
      invitadosVisita: []
    }
  },
  computed: {
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
    ...mapActions(visitasStore, ['getInvitadosVisita']),

    esPendiente(visita) {
      const hoy = new Date()
      return new Date(visita.fechaFin) > hoy ? true : false
    },
  },

  created() {
    this.getInvitadosVisita(this.visita.id).then(invitados => {
      this.invitadosVisita = invitados
    })
  },

}
</script>


<template>
  <div class="container alert alert-dark">
    <div class="row mb-3">
      <div class="col-md-5">
        <label class="fs-5"><b>Fecha Inicio</b><span class="ms-4">{{ fechaInicio }}</span></label>
      </div>
      <div class="col-md-4">
        <label class="fs-5"><b>Hora Inicio</b><span class="ms-4">{{ horaInicio }}</span></label>
      </div>

      <div v-if="esPendiente(visita)" class="col-md-3">
        <div class="d-flex flex-wrap">
          <span v-if="esPendiente(visita)" class="badge bg-secondary p-2 m-2" @click="$emit('editarVisita', visita)">
          <font-awesome-icon :icon="['fas', 'pen-to-square']" size="lg" style="color: #e5a50a;"
            class="me-1"></font-awesome-icon>
          Editar visita</span>

        <span class="badge bg-secondary p-2 m-2" @click="$emit('borrarVisita', visita)">
          <font-awesome-icon :icon="['fas', 'trash-can']" size="lg" style="color: #e01b24;"
            class="me-1"></font-awesome-icon>
          Borrar visita</span>  
        </div>

        <!-- <span v-if="esPendiente(visita)" class="badge bg-secondary p-2 me-2" @click="$emit('editarVisita', visita)">
          <font-awesome-icon :icon="['fas', 'pen-to-square']" size="lg" style="color: #e5a50a;"
            class="me-1"></font-awesome-icon>
          Editar visita</span>

        <span class="badge bg-secondary p-2" @click="$emit('borrarVisita', visita)">
          <font-awesome-icon :icon="['fas', 'trash-can']" size="lg" style="color: #e01b24;"
            class="me-1"></font-awesome-icon>
          Borrar visita</span> -->

      </div>

      <!-- <div v-if="esPendiente(visita)" class="col-md-1 d-flex justify-content-start rounded op me-2">
        <span v-if="esPendiente(visita)" class="badge bg-secondary p-2 me-2" @click="$emit('editarVisita', visita)">
          <font-awesome-icon :icon="['fas', 'pen-to-square']" size="lg" style="color: #e5a50a;"
            class="me-1"></font-awesome-icon>
          Editar visita</span>
      </div>
      <div v-if="esPendiente(visita)" class="col-md-1 d-flex justify-content-start rounded op me-2">
        <span class="badge bg-secondary p-2" @click="$emit('borrarVisita', visita)">
          <font-awesome-icon :icon="['fas', 'trash-can']" size="lg" style="color: #e01b24;"
            class="me-1"></font-awesome-icon>
          Borrar visita</span>
      </div> -->
    </div>

    <div class="row mb-3">
      <div class="col-md-5">
        <label class="fs-5"><b>Fecha Fin</b><span class="ms-5">{{ fechaFin }}</span></label>
      </div>
      <div class="col-md-4">
        <label class="fs-5"><b>Hora Fin</b><span class="ms-5">{{ horaFin }}</span></label>
      </div>
    </div>

    <div class="row mb-3">
      <div class="col-md-9">
        <label class="fs-5"><b>Actividad</b><span class="ms-5">{{ visita.actividad }}</span></label>
      </div>
      <div class="col-md-3">
        <span class="badge text-bg-danger d-flex justify-content-center rounded op p-2 fs-7"
          @click="$emit('mostrarAnfitrion', visita)">
          <font-awesome-icon icon="fa-solid fa-id-card" size="lg" class="me-2" />Ver Anfitrión</span>
      </div>
    </div>

    <div class="row mb-3">
      <div class="col-md-9">
        <label class="fs-5"><b>Descripción</b><span class="ms-4">{{ visita.actuaciones }}</span></label>
      </div>
    </div>

    <div v-if="invitadosVisita" class="accordion accordion-flush alert alert-secondary mt-2 mb-0 border rounded"
      id="detallesVisita">
      <div class="accordion-item">
        <h2 class="accordion-header">
          <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
            :data-bs-target="`#flush-collapse${visita.id}`" aria-expanded="false"
            :aria-controls="`flush-collapse${visita.id}`" style="color: darkred;">
            <font-awesome-icon :icon="['fas', 'list']" style="color: #a51d2d;" class="me-2" />Mostrar lista de invitados
            {{ visita.id }}
          </button>
        </h2>

        <div :id="`flush-collapse${visita.id}`" class="accordion-collapse collapse" data-bs-parent="#detallesVisita">
          <div class="accordion-body">
            <table class="table table-striped table-hover">
              <thead class="alert alert-dark">
                <tr>
                  <th scope="col">DNI</th>
                  <th scope="col">Nombre</th>
                  <th scope="col">Apellidos</th>
                  <th scope="col">Empresa</th>
                  <th scope="col">Autorizado</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="invit in invitadosVisita">
                  <th scope="row">{{ invit.dni }}</th>
                  <td>{{ invit.nombre }}</td>
                  <td>{{ invit.apellidos }}</td>
                  <td>{{ invit.empresa }}</td>

                  <td v-if="invit.autorizacion == true"><input type="checkbox" id="autorizado" name="autorizado"
                      checked><label for="disabled"></label></td>
                  <td v-else><input type="checkbox" id="noAutorizado" name="noAutorizado" disabled><label
                      for="disabled"></label></td>

                  <td>
                    <router-link :to="{ name: 'personainfo', params: { identificador: invit.id } }"><font-awesome-icon
                        :icon="['fas', 'circle-info']" size="lg" style="color: #77767b;" /></router-link>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>

    <!--  
     <div v-if="invitadosVisita" class="row">
       <pre>invitados visita {{ visita.id }} recuperados del store : {{ JSON.stringify(invitadosVisita, null, " ") }}</pre>               
    </div> -->

  </div>
</template>

<style scoped>
.op {
  cursor: pointer;
}

tr {
  text-align: center
}

[type="checkbox"] {
  display: none;
  position: relative
}

[type="checkbox"]+label:after {
  content: "";
  display: inline-block;
  width: 1em;
  height: 1em;
  outline: 1px solid silver;
}

[type="checkbox"]:checked+label:after {
  background: rgb(69, 180, 36);
}
</style>