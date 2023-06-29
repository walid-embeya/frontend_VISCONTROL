import { defineStore } from 'pinia'
import { getVisitas, postVisita, addInvitadosToVisita, getVisitaPorId, getInvitadosVisita, deleteVisita, putVisita, llamadaAPI } from './api-service'
import { reactive } from 'vue'

export const visitasStore = defineStore('visitas', {
  state: () => ({         //// state: equiv a Data
    visitasApi: null,       ///// para cargar la lista recuperado de la api
    visitaApi: null,        ///// para recuperar una visita por ID
    idVisita: null,         ///// para recuperar el ID de visita tras agregar uns nueva visita y usarlo en anñadir invitados a dicha visita
  }),
  actions: {
    async getVisitasApi() {
      await getVisitas().then((response) => {
        this.visitasApi = reactive([])
        let visitasAux = response.data._embedded.visitas
        visitasAux.forEach(v => {
          llamadaAPI("get", null, v._links.self.href).then(r => {     //// llamada al link de visita
            let visita = r.data
            this.visitasApi.push(visita)
          })
        })
      })
    },
    async getInvitadosVisita(id) {
      return getInvitadosVisita(id).then((response) => {
        let listaInvitados = reactive([])
        let invitadosAux = []

        if (response.data._embedded) {
          invitadosAux = response.data._embedded.personas
          invitadosAux.forEach(i => {
            llamadaAPI("get", null, i._links.self.href).then(r => listaInvitados.push(r.data))      //// llamada al link de invitado y cargar toda la persona (invitado) a listaInvitados                
          })
        }
        return reactive(listaInvitados)
      })
    },
    async postVisita(visita) {
      await postVisita(visita).then((r) => {
        ////// recuperar el ID de la visita añadida para poder agregar invitados
        let array = r.data._links.self.href.split('/')
        this.idVisita = array[array.length - 1]
      })
    },
    async addInvitadosToVisita(listaInvitados, id) {
      await addInvitadosToVisita(listaInvitados, id)
    },
    async getVisitaPorId(id) {
      await getVisitaPorId(id).then((r) => this.visitaApi = r.data)
    },
    async deleteVisita(visita) {
      await deleteVisita(visita)
    },
    async putVisita(visita) {
      await putVisita(visita)
    },
  }

})
