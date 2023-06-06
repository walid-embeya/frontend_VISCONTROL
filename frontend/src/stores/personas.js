import { defineStore } from 'pinia'
import { getPersonas, getPersonaPorId, postPersona, getVisitasPersona, putPersona, deletePersona } from './api-service'


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
            await getPersonas('Invitado').then((response) => {
                let invitadosAux = response.data._embedded.personas
                this.invitadosApi = []
                invitadosAux.forEach(element => {    
                    getPersonaPorId(element.id).then((r) => this.invitadosApi.push(r.data))                       
                })      
            })
        },   
    
        async getAnfitrionesApi() {
            await getPersonas('Anfitrion').then((response) => {
                let anfitrionesAux = response.data._embedded.personas
                this.anfitrionesApi = []
                anfitrionesAux.forEach(element => {    
                    getPersonaPorId(element.id).then((r) => this.anfitrionesApi.push(r.data))                       
                })      
            })
        },

        /////////////////////////////****************************************///////////////////////////

        async getPersonaPorId(id) {      
            // await getPersonaPorId(id).then(r => this.personaApi = r.data)           
            
            await getPersonaPorId(id).then((response) =>  {
                this.personaApi = response.data

                getVisitasPersona(response.data.id).then((r) =>  {
                    this.visitasPersona = []            
                    if (r.data._embedded.visitas !== null) {
                        this.visitasPersona = r.data._embedded.visitas            
                    }
                })   

                // this.personaApi.inicioAut = new Date(element.inicioAut)
                // this.personaApi.finAut = new Date(element.finAut)              
            })
        },

        ////////////*********************************************////////////////
        // async getVisitasPersona(id) {
        //     //console.log("llamando a getVisiatsPersona dentro del store")
        //     await getVisitasPersona(id).then((r) =>  {
        //         this.visitasPersona = []            
        //         if (r.data._embedded.visitas !== null) {
        //             this.visitasPersona = r.data._embedded.visitas            
        //         }
        //     })           
        // },
        ///////////////////////////////////////////////////////////////////////
                            
        async postPersona(persona) {
            await postPersona(persona).then((response) => {
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
    
        async putPersona(persona) {
            console.log('put persona desde store')
            await putPersona(persona)      
        },
    }
  })
