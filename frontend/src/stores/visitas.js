import { defineStore } from 'pinia'
import visitasJson from '@/assets/visitas.json'
import { getVisitas, postVisita, addInvitadosToVisita, getVisitaPorId } from './api-service'
import { dateToString, timestampToFecha, timestampToHora } from '@/utils/utils'

const listaVisitas = visitasJson._embedded.visitas

export const visitasStore = defineStore('visitas', {
    state: () => ({         //// state: equiv a Data

        visitas: listaVisitas,   //// datos desde json

        visitasApi: null,        ///// para cargarlo con la lista recuperado de la api
        visitaApi: null,
        idVisita: null,

    }),

    getters: {         ////// getters: equiv a Computed

        
        getVisitasGlobales() {
            //// otra forma
            //return listaVisitas.sort((a,b) => a.fechaInicio - b.fechaInicio)

            return this.visitas.sort((a,b) => a.fechaInicio - b.fechaInicio)            
        },

        getVisitasPendientes() {
            let fechaSistema = Date.now();           
            return this.getVisitasGlobales.filter(v => new Date(v.fechaInicio) >= fechaSistema)            
        },
        
        generarIdVisita() {
            // let max=this.visitas[0].id;
            // for(let i=1;i<this.visitas.length;i++) {
            //     if (this.visitas[i].id > max) {
            //         max = this.visitas[i].id
            //     }
            // }
            // return max

            //return this.visitas.reduce((p, c) => { return c.id > p ? c.id : p }, 0)
        }
        
    },

    actions: {              ////// actions: equiv a Methods
        
        async getVisitasApi() {
            await getVisitas().then(r => 
                {
                   let visitasAux =  r.data._embedded.visitas  
                   visitasAux.forEach((element) => {
                            element.fechaInicio = dateToString(new Date(element.fechaInicio))
                            element.fechaFin = dateToString(new Date(element.fechaFin))
                         });
                   this.visitasApi = visitasAux                   
                }
            )
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
       
        










        // getVisitaPorId(id) {
        //    return this.visitas.find(v => v.id == id)
        // },                            
    }

  })
