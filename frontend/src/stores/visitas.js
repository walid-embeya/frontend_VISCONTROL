import { defineStore } from 'pinia'
import visitasJson from '@/assets/visitas.json'

const listaVisitas = visitasJson._embedded.visitas

export const visitasStore = defineStore('visitas', {
    state: () => ({         //// state: equiv a Data
        visitas: listaVisitas
    }),

    getters: {         ////// getters: equiv a Computed

        getVisitasGlobales() {
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

            return this.visitas.reduce((p, c) => { return c.id > p ? c.id : p }, 0)
        }
        
    },

    actions: {              ////// actions: equiv a Methods
        getVisitaPorId(id) {
           return this.visitas.find(v => v.id == id)
        },         


                       
    }

  })
