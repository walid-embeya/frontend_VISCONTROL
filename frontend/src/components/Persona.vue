<script>
  import { personasStore } from '@/stores/personas'
  import { mapActions } from 'pinia'
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
      ...mapActions(personasStore, [ 'deletePersona' ]),


      // this.$dialog.confirm('Voulez-vous vraiment supprimer cette personne ?')
        // .then(() => {
        //   // L'utilisateur a cliqué sur "OK"
        //   this.deletePersona(personaId);
        // })
        // .catch(() => {
        //   // L'utilisateur a cliqué sur "Annuler" ou a fermé la boîte de dialogue
        //   console.log("L'utilisateur a choisi ANNULER");
        // })

      darDeBajaPersona(persona) {
        if (confirm('¿ Está seguro de borrar esta persona ?')) {
           this.deletePersona(persona)
        } else {
           console.log('Operación anulada !');
        }        
      },                  

    },    
  }
</script>


<template>
    <!-- Este componente no necesita una capa <div></div> -->

    <!-- Modal -->
    <div class="modal fade" id="confirmacionOperacion" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">Confirmación de operación</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">              
                <p><font-awesome-icon icon="fa-solid fa-check" size="lg" style="color: #26a269;" class="me-2" />operación realizada con éxito</p>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
          </div>
        </div>
      </div>

    <tr>
      <th scope="row">{{ persona.dni }}</th>
      <td>{{ persona.nombre }}</td>
      <td>{{ persona.apellidos }}</td> 
      <td>{{ persona.tipo }}</td>
      
      <!-- <td><font-awesome-icon :icon="['fas', 'pen-to-square']" size="lg" style="color: #e5a50a;" class="option" /></td> -->
      <td class="icon">
          <router-link :to="{ name: 'modificarpersona', params: { identificador: persona.id }}"><font-awesome-icon :icon="['fas', 'pen-to-square']" size="lg" style="color: #e5a50a;" class="option" /></router-link>
      </td>

      <td><font-awesome-icon :icon="['fas', 'trash-can']" size="lg" style="color: #e01b24;" class="option" @click="darDeBajaPersona(persona)" data-bs-toggle="modal" data-bs-target="#confirmacionOperacion" /></td>       
      
      <td class="icon">
          <router-link :to="{ name: 'personainfo', params: { identificador: persona.id }}"><font-awesome-icon :icon="['fas', 'circle-info']" size="lg" style="color: #77767b;" /></router-link>
      </td> 
    </tr>

    <!-- <Dialog v-model:visible="visible" modal header="Header" :style="{ width: '50vw' }">
        <p>¿ Está seguro de borrar esta persona ?</p>
        <template #footer>
            <Button label="No" icon="pi pi-times" @click="deletePersona(persona.id)" text />
            <Button label="Yes" icon="pi pi-check" @click="visible = false" autofocus />
        </template>
    </Dialog> -->

    
</template>

<style scoped>

    tr {
      text-align: center
    }

    .option {
      cursor: pointer;
    }
</style>