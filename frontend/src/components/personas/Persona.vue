<script>
import { mapState } from 'pinia'
import { personasStore } from '@/stores/personas'

export default {
  props: ['persona'],
  emits: ['borrarPersona', 'editarPersona', 'mostrarPersona'],
  data() {
    return {}
  },
  computed: {
    ...mapState(personasStore, ['modeConeccion']),
  },
}
</script>

<template>
  <!-- Este componente no necesita una capa <div></div> -->
  <tr class="border">
    <th scope="row">{{ persona.dni }}</th>
    <td>{{ persona.nombre }}</td>
    <td>{{ persona.apellidos }}</td>
    <td>{{ persona.tipo }}</td>
    <td><font-awesome-icon :icon="['fas', 'pen-to-square']" size="lg" style="color: #e5a50a;" class="option"
        @click="$emit('editarPersona', persona)" /></td>

    <td v-if="modeConeccion == 'Administrador'"><font-awesome-icon :icon="['fas', 'trash-can']" size="lg"
        style="color: #e01b24;" class="option" @click="$emit('borrarPersona', persona)" /></td>
    <td v-else><font-awesome-icon :icon="['fas', 'trash-can']" size="lg" style="color: #a4a2af;" class="option"
        :style="{ 'pointer-events': modeConeccion !== 'Administrador' ? 'none' : 'auto' }" /></td>

    <td><font-awesome-icon :icon="['fas', 'circle-info']" size="lg" style="color: #77767b;" class="option"
        @click="$emit('mostrarPersona', persona)" /></td>
  </tr>
</template>

<style scoped>
tr {
  text-align: center
}

.option {
  cursor: pointer;
}
</style>