import { defineStore } from 'pinia'
import visitasJson from '@/assets/visitas.json'

const listaVisitas = visitasJson._embedded.visitas

export const visitasStore = defineStore('visitas', {
    state: () => ({         //// state: equiv a Data
        visitas: listaVisitas
    }),
    //   getters: {         ////// getters: equiv a Computed
    //     doubleCount: this.participantes.find(p => p.id == this.$route.params.id)
    //   },
    actions: {              ////// actions: equiv a Methods
       getVisitaPorId(id) {
           return this.visitas.find(v => v.id == id)
       }
       
    }
  })
