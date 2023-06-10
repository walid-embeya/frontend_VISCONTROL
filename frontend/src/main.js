import { createApp } from 'vue'
//import './style.css'
import App from './App.vue'
import Home from './components/PaginaPrincipal.vue'

import PrimeVue from 'primevue/config'
import ConfirmationService from 'primevue/confirmationservice'
import ToastService from 'primevue/toastservice';

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
import { faXmark, faIdCard, faSort, faUserPlus, faCheck, faHouse, faCircleMinus, faCircleLeft, faCircleInfo, faPlusCircle, faCircleCheck, faPenToSquare, faTrashCan, faSquareXmark, faFloppyDisk, faList } from '@fortawesome/free-solid-svg-icons'
import { faInstagram, faFacebook, faTwitter, faLinkedin } from '@fortawesome/free-brands-svg-icons'

// /* add icons to the library */
library.add(faXmark, faIdCard, faSort, faUserPlus, faCheck, faHouse, faCircleMinus, faCircleLeft, faInstagram, faLinkedin, faTwitter, faFacebook, faCircleInfo, faPlusCircle, faCircleCheck, faPenToSquare, faTrashCan, faSquareXmark, faFloppyDisk, faList)

//theme PrimeVue
import "primevue/resources/themes/lara-light-indigo/theme.css";

//core  PrimeVue
import "primevue/resources/primevue.min.css";

const pinia = createPinia()

const Personas = () => import('@/components/personas/ListadoPersonas.vue')
const PersonaInfo = () => import('@/components/personas/PersonaInfo.vue')
const AltaInvitado = () => import('@/components/personas/AltaInvitado.vue')
const AltaAnfitrion = () => import('@/components/personas/AltaAnfitrion.vue')
const ModificarPersona = () => import('@/components/personas/ModificarPersona.vue')

const Visitas = () => import('@/components/visitas/ListadoVisitas.vue')
const AltaVisita = () => import('@/components/visitas/AltaVisita.vue')
const ModificarVisita = () => import('@/components/visitas/ModificarVisita.vue')

const About = () => import('@/components/About.vue')
const NotFound = () => import('@/components/NotFound.vue')

const routes = [
  { path: '/', component: Home, name: 'home' },

  { path: '/visitas', component: Visitas, name: 'visitas' },
  { path: '/nuevavisita', component: AltaVisita, name: 'nuevavisita' },
  { path: '/visitas/:identificador/modificarvisita', component: ModificarVisita, name: 'modificarvisita' },

  { path: '/personas', component: Personas, name: 'personas' },
  { path: '/personas/:identificador/personainfo', component: PersonaInfo, name: 'personainfo' },
  { path: '/personas/:identificador/modificarpersona', component: ModificarPersona, name: 'modificarpersona' },
  { path: '/nuevoinvitado', component: AltaInvitado, name: 'nuevoinvitado' },
  { path: '/nuevoanfitrion', component: AltaAnfitrion, name: 'nuevoanfitrion' },
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

app.use(ConfirmationService);

app.use(ToastService)

app.mount('#app')

