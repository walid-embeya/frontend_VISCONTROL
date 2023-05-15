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
       getInvitadoPorMatricula(matricula) {
           return this.invitados.find(i => i.matricula == matricula)
       },
       getInvitadoPorDNI(dni) {
            return this.invitados.find(i => i.dni == dni)
        },
        getInvitadosPorEmpresa(empresa) {
            return this.invitados.find(i => i.empresa == empresa)
        }
    }
  })
