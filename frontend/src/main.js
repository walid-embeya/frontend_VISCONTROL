import { createApp } from 'vue'
//import './style.css'
import App from './App.vue'

import PrimeVue from 'primevue/config';

import Home from './components/PaginaPrincipal.vue'
import Visitas from '@/components/ListadoVisitas.vue'
import VisitasAnc from '@/components/ListaVisitasPV.vue'
import Busqueda from '@/components/Busqueda.vue'

// sprint 1 (solo gestion de invitados)
import Invitados from '@/components/ListadoInvitados.vue'
import InvitadoInfo from '@/components/InvitadoInfo.vue'
///////////////////////////////////////

import Personas from '@/components/ListadoPersonas.vue'
import PersonaInfo from '@/components/PersonaInfo.vue'

import AltaInvitado from '@/formularios/AltaInvitado.vue'
import AltaAnfitrion from '@/formularios/AltaAnfitrion.vue'
import AltaVisita from '@/formularios/AltaVisita.vue'
import About from '@/components/About.vue'
import NotFound from '@/components/NotFound.vue'

import { createRouter, createWebHashHistory } from 'vue-router'

// Create a pinia instance (the root store) 
import { createPinia } from 'pinia'

// Import our custom CSS
import './scss/styles.scss'
// Import all of Bootstrap's JS
import * as bootstrap from 'bootstrap'

/* import the fontawesome core */
import { library } from '@fortawesome/fontawesome-svg-core'
/* import font awesome icon component */
import { FontAwesomeIcon, FontAwesomeLayers } from '@fortawesome/vue-fontawesome'
// /* import specific icons */
import { faCheck, faHouse, faCircleMinus, faCircleLeft, faCircleInfo, faPlusCircle, faCircleCheck, faPenToSquare, faTrashCan, faSquareXmark, faFloppyDisk, faList } from '@fortawesome/free-solid-svg-icons'
import { faInstagram, faFacebook, faTwitter, faLinkedin } from '@fortawesome/free-brands-svg-icons'

// /* add icons to the library */
library.add(faCheck, faHouse, faCircleMinus, faCircleLeft, faInstagram, faLinkedin, faTwitter, faFacebook, faCircleInfo, faPlusCircle, faCircleCheck, faPenToSquare, faTrashCan, faSquareXmark, faFloppyDisk, faList)


//theme PrimeVue
import "primevue/resources/themes/lara-light-indigo/theme.css";     
    
//core  PrimeVue
import "primevue/resources/primevue.min.css";


const pinia = createPinia()

const routes = [
    { path: '/', component: Home, name: 'home' },
    { path: '/visitas', component: Visitas, name: 'visitas' },
    
    { path: '/busqueda', component: VisitasAnc, name: 'busqueda' },    

    // sprint 1 (solo gestion de invitados)
    { path: '/invitados', component: Invitados, name: 'invitados' },
    { path: '/invitados/:identificador', component: InvitadoInfo, name: 'invitado' },
    ///////////////////////////////////////

    { path: '/personas', component: Personas, name: 'personas' },
    { path: '/personas/:identificador', component: PersonaInfo, name: 'personainfo' },


    { path: '/nuevoinvitado', component: AltaInvitado, name: 'nuevoinvitado' },
    { path: '/nuevoanfitrion', component: AltaAnfitrion, name: 'nuevoanfitrion' },
    { path: '/nuevavisita', component: AltaVisita, name: 'nuevavisita' },
    { path: '/ayuda', component: About, name: 'ayuda' },
    { path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFound }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes, // routes: routes
})

const app = createApp(App)

app.use(pinia)

app.use(router)

/* add font awesome icon component */
app.component('font-awesome-icon', FontAwesomeIcon)             //Registro global del componente "FontAwesomeIcon"
app.component('font-awesome-layers', FontAwesomeLayers)

app.use(PrimeVue);

app.mount('#app')

