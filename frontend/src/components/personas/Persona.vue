<script>

export default {
  props: [ 'persona' ],  ///// aqui, la opcion "props" es un array of atributes string
  emits: [ 'borrarPersona', 'editarPersona', 'mostrarPersona' ],
  data() {
    return {}
  },
}
</script>

<template>
  <!-- Este componente no necesita una capa <div></div> -->
  <tr>
    <th scope="row">{{ persona.dni }}</th>
    <td>{{ persona.nombre }}</td>
    <td>{{ persona.apellidos }}</td>
    <td>{{ persona.tipo }}</td>
    <td><font-awesome-icon :icon="['fas', 'pen-to-square']" size="lg" style="color: #e5a50a;" class="option"
        @click="$emit('editarPersona', persona)" /></td>
    <td><font-awesome-icon :icon="['fas', 'trash-can']" size="lg" style="color: #e01b24;" class="option"
        @click="$emit('borrarPersona', persona)" /></td>
    <td><font-awesome-icon :icon="['fas', 'circle-info']" size="lg" style="color: #77767b;"
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