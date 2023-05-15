<script>
  import Modelo from '@/components/Model.vue'

  export default {
    components: { Modelo },   ///// registro local de los componentes
    data() {
      return {
        persona: {
          dni: '',
          nombre: '',
          apellidos: '',
          telefono: '',
          fechaNacimiento: ''
        },
        personas: []
      }
    },
    methods: {
      submitForm() {
        // Agregar la persona a la lista de personas
        this.personas.push(this.persona);
        
        // Limpiar los campos del formulario
        this.persona = {
          dni: '',
          nombre: '',
          apellidos: '',
          telefono: '',
          fechaNacimiento: ''
        };
        
        // Aquí se puede enviar los datos de la persona al servidor para guardarlos en una base de datos, por ejemplo
      }
    }
  }
  </script>


<template>
    <div>
      <Modelo titulo="CREACIÓN NUEVO INVITADO">
        <!-- <form class="p-1 border rounded" style="background-color: rgb(143, 170, 211);">
        

        </form> -->
      </Modelo>
    </div>
</template>


      
      <!-- <h2>Dar de alta persona</h2>
      <form @submit.prevent="submitForm">                       
        <div>
          <label for="dni">DNI:</label>
          <input type="text" id="dni" v-model="persona.dni" required>
        </div>
        <div>
          <label for="nombre">Nombre:</label>
          <input type="text" id="nombre" v-model="persona.nombre" required>
        </div>
        <div>
          <label for="apellidos">Apellidos:</label>
          <input type="text" id="apellidos" v-model="persona.apellidos" required>
        </div>
        <div>
          <label for="telefono">Teléfono:</label>
          <input type="tel" id="telefono" v-model="persona.telefono" required>
        </div>
        <div>
          <label for="fechaNacimiento">Fecha de nacimiento:</label>
          <input type="date" id="fechaNacimiento" v-model="persona.fechaNacimiento" required>
        </div>
        <button type="submit">Dar de alta</button>
      </form> -->
   
  
  
  