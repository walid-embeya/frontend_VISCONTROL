import { defineStore } from 'pinia'
import { getPersonas, getPersonaPorId, postPersona} from './api-service'


export const personasStore = defineStore('personas', {
    state: () => ({         //// state: equiv a Data
        personasApi: null,
        invitadosApi: null,
        anfitrionesApi: null,
        personaApi: null
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
            console.log("dentro de getPeronasApi")
            await getPersonas('todos').then(r => this.personasApi = r.data._embedded.personas)            
        },

        async getInvitadosApi() {
            await getPersonas('Invitado').then(r => this.invitadosApi = r.data._embedded.personas)            
        },
        
        async getAnfitrionesApi() {
            await getPersonas('Anfitrion').then(r => this.anfitrionesApi = r.data._embedded.personas)            
        },

        async getPersonaPorId(id) {
            console.log("llamando a getPersonaPorID dentro del store")
            await getPersonaPorId(id).then(r => this.personaApi = r.data)           
        },   
        async postPersona(persona) {
            await postPersona(persona)      
        },   
    }
  })
