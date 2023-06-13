import { defineStore } from 'pinia'
import { getPersonas, getPersonaPorId, postPersona, getVisitasPersona, putPersona, deletePersona, getPersonaMasInvitado } from './api-service'


export const personasStore = defineStore('personas', {
  state: () => ({         //// state: equiv a Data (variables reactivos)
    personasApi: null,
    invitadosApi: null,
    anfitrionesApi: null,
    personaApi: null,
    visitasPersona: [],
    huespedMasInvitado: null,
  }),

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
      await getPersonaPorId(id).then((response) => {
        this.personaApi = response.data
      })
    },

    ////////////*********************************************////////////////
    async getVisitasPersona(id) {
      await getVisitasPersona(id).then((r) => {
        if (r.data._embedded) {
          this.visitasPersona = r.data._embedded.visitas
        }
        else {
          this.visitasPersona = []
        }
      })
    },
    ///////////////////////////////////////////////////////////////////////

    async postPersona(persona) {
      await postPersona(persona).then((response) => {
        if (persona.tipo == 'Invitado') {
          this.invitadosApi.push(persona)
        }
        else
          if (persona.tipo == 'Anfitrion') {
            this.anfitrionesApi.push(persona)
          }
      })
        .catch((error) => console.log(error))
    },

    async deletePersona(persona) {
      await deletePersona(persona)
    },

    async putPersona(persona) {
      await putPersona(persona)
    },

    async getPersonaMasInvitado(id) {
      await huespedMasInvitado(id).then(r => {
        this.huespedMasInvitado = r.data
      })
      
    },
    
  }
})
