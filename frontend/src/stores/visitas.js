import { defineStore } from 'pinia'
import visitasJson from '@/assets/visitas.json'

const listaVisitas = visitasJson._embedded.visitas

export const visitasStore = defineStore('visitas', {
    state: () => ({         //// state: equiv a Data
        visitas: listaVisitas
    }),

    getters: {         ////// getters: equiv a Computed

    },

    actions: {              ////// actions: equiv a Methods
        getVisitaPorId(id) {
           return this.visitas.find(v => v.id == id)
        },
 
    }

  })
