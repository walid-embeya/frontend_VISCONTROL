import { defineStore } from 'pinia'
import visitasJson from '@/assets/visitas.json'
import { getVisitas, postVisita, addInvitadosToVisita, getVisitaPorId, getInvitadosVisita } from './api-service'
import { dateToString, timestampToFecha, timestampToHora } from '@/utils/utils'


export const visitasStore = defineStore('visitas', {
    state: () => ({         //// state: equiv a Data

        visitasApi: null,       ///// para cargar la lista recuperado de la api
        visitaApi: null,        ///// para recuperar una visita por ID
        invitadosVisitaApi: undefined,    //// para recuperar la lista de invitados de una visita
        idVisita: null,         ///// para recuperar el ID de visita tras agregar uns nueva visita y usarlo en anñadir invitados a dicha visita

    }),

    getters: {         ////// getters: equiv a Computed        
        // visitasGlobalesApi() {
        //     if (this.visitasApi) {
        //         return this.visitasApi.sort((a,b) => a.fechaInicio - b.fechaInicio)  
        //     }  
        //     else
        //         return null        
        // },

        // visitasPendientesApi() {
        //     console.log("globales :", this.visitasGlobalesApi)
        //     let fechaSistema = Date.now()        
        //     if (this.visitasGlobalesApi) {           
        //         return this.visitasGlobalesApi.filter(v => new Date(v.fechaInicio) >= fechaSistema)
        //     }  
        //     else 
        //         return null         
        // },
    },

    actions: {              ////// actions: equiv a Methods
        
        // async getVisitasApi() {
        //     await getVisitas().then(r => 
        //         {
        //            let visitasAux =  r.data._embedded.visitas  
        //            visitasAux.forEach((element) => {
        //                     element.fechaInicio = dateToString(new Date(element.fechaInicio))
        //                     element.fechaFin = dateToString(new Date(element.fechaFin))
        //                  });
        //            this.visitasApi = visitasAux                   
        //         }
        //     )
        // },

        async getVisitasApi() {
            await getVisitas().then((response) => 
                {
                    let visitasAux =  response.data._embedded.visitas  
                    this.visitasApi = []
                    visitasAux.forEach((element) => {
                        let array = element._links.self.href.split('/')                
                        let id = array[array.length-1]
    
                        getVisitaPorId(id).then((r) => {
                            this.visitasApi.push(r.data)
                        })

                        ///// esto para Table Primevue
                            // element.fechaInicio = dateToString(new Date(element.fechaInicio))
                            // element.fechaFin = dateToString(new Date(element.fechaFin))
                    })
                   // this.visitasApi.sort((a,b) => a.fechaInicio - b.fechaInicio)                                    
                })
                
                console.log("Lista Visitas API desde store: ", this.visitasApi)
        },

        async postVisita(visita) {
            await postVisita(visita).then((r) => {
                let array = r.data._links.self.href.split('/')                
                this.idVisita = array[array.length-1]
                console.log("id visita guardada = ", this.idVisita)
            })     
        },  
       
        async addInvitadosToVisita(listaInvitados, id) {
            await addInvitadosToVisita(listaInvitados, id)      
        }, 
        
        async getVisitaPorId(id) {
            await getVisitaPorId(id).then((r) => this.visitaApi = r.data)
        },

        async getInvitadosVisita(id) {
            console.log("getInvitadosVisita dentro del store")
            await getInvitadosVisita(id).then((response) => {
                this.invitadosVisitaApi = response.data._embedded.personas
            })
        },
                                        
    }

  })
