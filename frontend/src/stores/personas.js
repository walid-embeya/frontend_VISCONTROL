import { defineStore } from 'pinia'
import { getPersonas, getPersonaPorId, postPersona, getVisitasPersona, putPersona, deletePersona, llamadaAPI} from './api-service'


export const personasStore = defineStore('personas', {
    state: () => ({         //// state: equiv a Data (variables reactivos)
        personasApi: null,
        invitadosApi: null,
        anfitrionesApi: null,
        personaApi: null,
        visitasPersona: null,
    }),
    
    getters: {         ////// getters: equiv a Computed
        
        // getInvitados() {
        //     return this.personasApi.filter(p => p.tipo == 'Invitado')
        // },

        // getAnfitriones() {
        //     return this.personasApi.filter(p => p.tipo == 'Anfitrion')
        // },

    },

    actions: {              ////// actions: equiv a Methods

        async getPersonasApi() {
            await getPersonas('todos').then(r => this.personasApi = r.data._embedded.personas)            
        },

        async getInvitadosApi() {
            await getPersonas('Invitado').then(r => this.invitadosApi = r.data._embedded.personas)            
        },
                
        async getAnfitrionesApi() {
            await getPersonas('Anfitrion').then(r => this.anfitrionesApi = r.data._embedded.personas)            
        },      
        
        /////////////////////////////****************************************///////////////////////////////
        async getPersonaPorId(id) {      
            await getPersonaPorId(id).then(r => this.personaApi = r.data)           
        },
                            
        async postPersona(persona) {
            await postPersona(persona).then((response) => {
                this.personasApi.push(persona)
                if (persona.tipo=='Invitado') {
                   this.invitadosApi.push(persona)
                }
                else 
                if (persona.tipo=='Anfitrion') {
                   this.anfitrionesApi.push(persona)
                }                
            })  
            .catch((error) => console.log(error))    
        },
                
        async deletePersona(persona) {
            await deletePersona(persona).then((response) => {
                let indexToRemove = this.personasApi.indexOf(persona)
                this.personasApi.splice(indexToRemove, 1)
            }) 
            .catch((error) => {
                       console.error("A la hora de borrar la persona, Se ha producido un error : ", error);
                     });     
        },   
    
        ////////////*********************************************////////////////


        async getVisitasPersona(id) {
            //console.log("llamando a getVisiatsPersona dentro del store")
            await getVisitasPersona(id).then(r => this.visitasPersona = r.data._embedded.visitas)           
        },

        async putPersona(persona, id) {
            console.log('delete desde store')
            await putPersona(persona, id)      
        },

    }
  })
