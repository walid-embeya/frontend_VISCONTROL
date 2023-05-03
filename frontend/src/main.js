import { createApp } from 'vue'
//import './style.css'
import App from './App.vue'

import PrimeVue from 'primevue/config';

import Home from './components/PaginaPrincipal.vue'
// import Visitas from '@/components/ListadoVisitas.vue'
import Visitas from '@/components/ListaVisitasPV.vue'
import Invitados from '@/components/ListadoInvitados.vue'
import InvitadoInfo from '@/components/InvitadoInfo.vue'
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
import { fainstagram, faCircleInfo, faPlusCircle, faCircleCheck, faPenToSquare, faTrashCan, faSquareXmark, faFloppyDisk, faList } from '@fortawesome/free-solid-svg-icons'

import { faFacebook, faTwitter, fainstagram } from '@fortawesome/free-brands-svg-icons'
// /* add icons to the library */


library.add(faTwitter, faFacebook, faCircleInfo, faPlusCircle, faCircleCheck, faPenToSquare, faTrashCan, faSquareXmark, faFloppyDisk, faList)




//theme PrimeVue
import "primevue/resources/themes/lara-light-indigo/theme.css";     
    
//core  PrimeVue
import "primevue/resources/primevue.min.css";


const pinia = createPinia()

const routes = [
    { path: '/', component: Home, name: 'home' },
    { path: '/visitas', component: Visitas },
    { path: '/invitados', component: Invitados },
    { path: '/ayuda', component: About },
    { path: '/invitados/:matricula', component: InvitadoInfo },
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

