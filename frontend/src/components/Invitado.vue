<script>
export default {
  props: [ 'invitado' ],  ///// aqui, la opcion "props" es un array of atributes string
  data() {
    return { }
  },
  computed: {
    autorizacion() {
       return this.invitado.autorizacion.fechaInicio ==  null ? false : true;
    }
  },
  created() {
    // console.log(this.autorizacion)
  }
}
</script>


<template>
    <!-- Este componente no necesita una capa <div></div> -->
    
    <tr>
      <th scope="row">{{ invitado.matricula }}</th>
      <td>{{ invitado.nombre }}</td>
      <td>{{ invitado.apellidos }}</td> 
      <td>{{ invitado.empresa }}</td>
      <td v-if="autorizacion"><input type="checkbox" id="autorizado" name="autorizado" checked></td>
      <td v-else><input type="checkbox" id="noAutorizado" name="noAutorizado"></td>
      <td><font-awesome-icon :icon="['fas', 'circle-check']" size="lg" class="option option1" /></td>
      <td><font-awesome-icon :icon="['fas', 'pen-to-square']" size="lg" class="option" /></td>
      <td><font-awesome-icon :icon="['fas', 'trash-can']" size="lg" class="option option3"/></td> 
      <td>
        <router-link :to="{ name: 'invitado', params: { identificador: invitado.matricula }}"><font-awesome-icon :icon="['fas', 'circle-info']" size="lg" style="color: #77767b;" /></router-link>
      </td>
      
      <!-- <td>      
         <router-link :to="`/invitados/${invitado.matricula}`"><font-awesome-icon :icon="['fas', 'circle-info']" size="lg" style="color: #77767b;" /></router-link> 
      </td> -->
    </tr>
</template>

<style>
 
  .option {
    cursor: pointer;
  }
  
  .option1 {
    color: #26a269
  }

  .option3 {
    color: red
  }
</style>