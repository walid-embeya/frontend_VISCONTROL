import { defineStore } from 'pinia'
import invitadosJson from '@/assets/invitados.json'

const listaInvitados = invitadosJson._embedded.invitados

export const invitadosStore = defineStore('invitados', {
    state: () => ({         //// state: equiv a Data
        invitados: listaInvitados
    }),
    //   getters: {         ////// getters: equiv a Computed
  
    //   },
    actions: {              ////// actions: equiv a Methods
        getInvitadoPorId(paramId) {
           return this.invitados.find(i => i.id == paramId)
       },
       
       getInvitadoPorDNI(paramDni) {
            return this.invitados.find(i => i.dni == paramDni)
        },
        getInvitadosPorEmpresa(empresa) {
            return this.invitados.find(i => i.empresa == empresa)
        }
    }
  })
