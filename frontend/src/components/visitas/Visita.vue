<script>
import { mapActions, mapState } from 'pinia'
import { visitasStore } from '@/stores/visitas'
import { personasStore } from '@/stores/personas'
import { timestampToFecha, timestampToHora } from '@/utils/utils'

export default {
  props: ['visita'],
  data() {
    return {
      invitadosVisita: []
    }
  },

  watch: {
    visita(value) {
      this.getInvitadosVisita(this.visita.id).then(invitados => {
        this.invitadosVisita = invitados
      })
    },
  },

  computed: {
    ...mapState(personasStore, ['modeConeccion']),

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
    </div>
    <div class="row mb-3">
      <div class="col-md-12">
        <label class="fs-5"><b>Descripción</b><span class="ms-4">{{ visita.actuaciones }}</span></label>
      </div>
    </div>
    <div class="row mb-3">
      <div v-if="esPendiente(visita)" class="col-md-6 mb-2">
        <div class="d-flex flex-wrap">
          <span class="badge bg-secondary p-2 opcion me-2" @click="$emit('editarVisita', visita)">
            <font-awesome-icon :icon="['fas', 'pen-to-square']" size="lg" style="color: #e5a50a;"
              class="me-1"></font-awesome-icon>Editar visita</span>

          <span v-if="modeConeccion == 'Administrador'" class="badge bg-secondary p-2 opcion"
            @click="$emit('borrarVisita', visita)">
            <font-awesome-icon :icon="['fas', 'trash-can']" size="lg" style="color: #e01b24;"
              class="me-1"></font-awesome-icon>Borrar visita</span>
        </div>
      </div>
      <div class="col-md-6 mb-2 d-flex">
        <span class="badge text-bg-danger d-flex justify-content-center rounded opcion p-2 fs-7"
          @click="$emit('mostrarAnfitrion', visita)">
          <font-awesome-icon icon="fa-solid fa-id-card" size="lg" class="me-2" />Ver Anfitrión</span>
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
            <!-- {{ visita.id }} -->
          </button>
        </h2>
        <div :id="`flush-collapse${visita.id}`" class="accordion-collapse collapse" data-bs-parent="#detallesVisita">
          <div class="accordion-body">
            <div class="table-responsive">
              <table class="table table-striped table-hover">
                <thead>
                  <tr class="border">
                    <th scope="col" class="color-thead">DNI</th>
                    <th scope="col" class="color-thead">Nombre</th>
                    <th scope="col" class="color-thead">Apellidos</th>
                    <th scope="col" class="color-thead">Empresa</th>
                    <th scope="col" class="color-thead">Autorizado</th>
                    <th class="color-thead"></th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="invit in invitadosVisita" class="border">
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
    </div>
  </div>
</template>

<style scoped>
.opcion {
  cursor: pointer;
}

tr {
  text-align: center
}

.color-thead {
  background-color: grey;
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

@media(max-width:768px) {}
</style>