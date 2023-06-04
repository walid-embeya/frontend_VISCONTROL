<script>
  import { personasStore } from '@/stores/personas'
  import { mapActions } from 'pinia'
  import axios from 'axios'
  import Dialog from 'primevue/dialog';

  export default {
    components: { Dialog },
    props: [ 'persona' ],  ///// aqui, la opcion "props" es un array of atributes string
    data() {
      return { }
    },

    computed: {
      
    },

    methods: {
      ...mapActions(personasStore, ['deletePersona']),

      // DarDeBajaPersona(personaId) {
      //   this.$dialog.confirm('Voulez-vous vraiment supprimer cette personne ?')
      //   .then(() => {
      //     // L'utilisateur a cliqué sur "OK"
      //     this.deletePersona(personaId);
      //   })
      //   .catch(() => {
      //     // L'utilisateur a cliqué sur "Annuler" ou a fermé la boîte de dialogue
      //     console.log("L'utilisateur a choisi ANNULER");
      //   })
      // },
        
        

            // if (confirm('¿ Está seguro de borrar esta persona ?')) {

            //   console.log("L'utilisateur a choisi OK"); 


            //   this.deletePersona(this.persona.id)

              
            // } else {
            //   console.log("L'utilisateur a choisi ANNULER");
            // }
     
      deletePersona(id) {
        axios.delete(`http://localhost:8080/api/personas/${id}`)
          .then(response => {
            console.log("La personne a été supprimée avec succès !");

          })
          .catch(error => {
            console.error("Une erreur s'est produite lors de la suppression de la personne :", error);
          });
      }

    },    
  }
</script>


<template>
    <!-- Este componente no necesita una capa <div></div> -->

    <!-- <Dialog v-model:visible="visible" modal header="Header" :style="{ width: '50vw' }">
        <p>¿ Está seguro de borrar esta persona ?</p>
        <template #footer>
            <Button label="No" icon="pi pi-times" @click="deletePersona(persona.id)" text />
            <Button label="Yes" icon="pi pi-check" @click="visible = false" autofocus />
        </template>
    </Dialog>
     -->
    

    <tr>
      <th scope="row">{{ persona.dni }}</th>
      <td>{{ persona.nombre }}</td>
      <td>{{ persona.apellidos }}</td> 
      <td>{{ persona.tipo }}</td>
      <td><font-awesome-icon :icon="['fas', 'pen-to-square']" size="lg" style="color: #e5a50a;" class="option" /></td>
      <td><font-awesome-icon :icon="['fas', 'trash-can']" size="lg" style="color: #e01b24;" class="option" /></td>       
      <td class="icon">
          <router-link :to="{ name: 'personainfo', params: { identificador: persona.id }}"><font-awesome-icon :icon="['fas', 'circle-info']" size="lg" style="color: #77767b;" /></router-link>
      </td> 
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