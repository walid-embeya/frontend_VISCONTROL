import { defineStore } from 'pinia'
import { getVisitas, postVisita, addInvitadosToVisita, getVisitaPorId, getInvitadosVisita, deleteVisita, putVisita, llamadaAPI } from './api-service'
import { reactive } from 'vue'


export const visitasStore = defineStore('visitas', {
  state: () => ({         //// state: equiv a Data
    visitasApi: null,       ///// para cargar la lista recuperado de la api
    visitaApi: null,        ///// para recuperar una visita por ID
    idVisita: null,         ///// para recuperar el ID de visita tras agregar uns nueva visita y usarlo en anñadir invitados a dicha visita
  }),

  getters: {         ////// getters: equiv a Computed  

  },

  actions: {              ////// actions: equiv a Methods

    async getVisitasApi() {
      await getVisitas().then((response) => {
        this.visitasApi = reactive([])

        let visitasAux = response.data._embedded.visitas
        visitasAux.forEach(v => {
          llamadaAPI("get", null, v._links.self.href).then(r => {     //// llamada al link de visita
            let visita = r.data

            // visita.listaInvitados = []
            // llamadaAPI("get", null, r.data._links.lista_invitados.href).then(resp => {     //// llamada al link de lista invitados de la visita                        
            //     if (resp.data) {
            //       visita.listaInvitados = resp.data._embedded.personas
            //     }                    
            // })               
            this.visitasApi.push(visita)
          })
        })

        // visitasAux.forEach((element) => {
        //   let array = element._links.self.href.split('/')
        //   let id = array[array.length - 1]  ///// id de cada visita 

        //   getVisitaPorId(id).then((r) => {
        //     let visita = r.data
        //     this.visitasApi.push(visita)
        //   })
        // })

      })

      // console.log("visitas api dentro store", this.visitaApi)
    },

    async getInvitadosVisita(id) {
      return getInvitadosVisita(id).then((response) => {
        let listaInvitados = reactive([])
        let invitadosAux = []
        if (response.data._embedded) {
          invitadosAux = response.data._embedded.personas
          invitadosAux.forEach(i => {
            llamadaAPI("get", null, i._links.self.href).then(r => listaInvitados.push(r.data))      //// llamada al link de invitado  y cargar toda la persona (invitado) a listaInvitados                
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

    async putVisita(persona) {
      await putVisita(persona)
    },
  }

})
