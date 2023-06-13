<script>
import Modelo from '@/components/Model.vue'
import { mapActions, mapState } from 'pinia'
import { personasStore } from '@/stores/personas'

export default {
  components: { Modelo },   ///// registro local de los componentes
  data() {
    return {
      opcionElegida: 0,
      anfitrion: null,

    }
  },

  computed: {
    ...mapState(personasStore, ['anfitrionesApi', 'huespedMasInvitado']),
  },

  methods: {
    ...mapActions(personasStore, ['getAnfitrionesApi', 'getPersonaMasInvitado']),

    mostrarResultadoConsulta() {

      if (this.opcionElegida == 0) {
        console.log("anfitrion : ", JSON.stringify(this.anfitrion.id, null, 2))
        this.getPersonaMasInvitado(this.anfitrion.id)
      }
    },

  },

  created() {
    this.getAnfitrionesApi()
  },
};
</script>


<template>
  <Modelo titulo="CONSULTAR EL HUÉSPED MÁS INVITADO">

    <div class="container alert alert-primary border rounded mb-1">
      <div class="row align-items-center">
        <div class="col-auto">
          <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="opcion" id="opcion1" value="opcion1" @click="opcionElegida=0" checked>
            <label class="form-check-label" for="opcion1">
              Por Anfitrión
            </label>
          </div>
          <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="opcion" id="opcion2" value="opcion2" @click="opcionElegida=1" disabled>
            <label class="form-check-label" for="opcion2">
              En todas lass visitas
            </label>
          </div>
        </div>

        <div v-if="opcionElegida === 0" class="col-auto ms-5">
          <label for="anfitrionvisita" class="form-label fs-5 fw-bold">Anfitrión</label>
        </div>
        <div v-if="opcionElegida === 0" class="col-auto">
          <select id="anfitrionVisita" class="form-select me-5" v-model="anfitrion" @click="mostrarResultadoConsulta" required>
            <option value="">--seleccionar un anfitrión--</option>
            <option v-for="anf of anfitrionesApi" :value="anf">{{ anf.nombre }} {{ anf.apellidos }}</option>
          </select>
        </div>
      </div>
    </div>


    <div v-if="huespedMasInvitado" class="container alert alert-dark border rounded">

      <pre>JSON.stringify(huespedMasInvitado, null, 2)</pre>


    </div>

















  </Modelo>
</template>

