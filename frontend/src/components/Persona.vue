<script>
export default {
  props: [ 'persona' ],  ///// aqui, la opcion "props" es un array of atributes string
  data() {
    return { }
  },
  computed: {
    
  },
  created() {

    
  },
}
</script>


<template>
    <!-- Este componente no necesita una capa <div></div> -->
    
    <tr>
      <th style="text-align: center;" scope="row">{{ persona.dni }}</th>
      <td>{{ persona.nombre }}</td>
      <td>{{ persona.apellidos }}</td> 
      <td>{{ persona.tipo }}</td>
     
      <td><font-awesome-icon :icon="['fas', 'pen-to-square']" size="lg" style="color: #e5a50a;" class="option"/></td>
      <td><font-awesome-icon :icon="['fas', 'trash-can']" size="lg" style="color: #e01b24;" class="option"/></td> 
      <td class="icon">
          <router-link :to="{ name: 'persona', params: {  identificador: persona.id }}"><font-awesome-icon :icon="['fas', 'circle-info']" size="lg" style="color: #77767b;" /></router-link>
      </td> 
          
    </tr>
</template>

<style scoped>

    tr, th {
      text-align: center
    }

    .option {
      cursor: pointer;
    }
</style>