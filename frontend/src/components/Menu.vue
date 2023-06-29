<script>
import { personasStore } from '@/stores/personas'
import { mapState, mapActions } from 'pinia'

export default {
  data() {
    return {
    }
  },
  computed: {
    ...mapState(personasStore, ['modeConeccion']),
  },
  methods: {
    ...mapActions(personasStore, ['changeUserMode']),
  },
};
</script>

<template>
  <div>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container-fluid">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo03"
          aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <router-link class="nav-link" :to="{ name: 'home' }"
                :class="$route.name == 'home' ? 'text-warning fw-bold' : ''">Home</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" aria-current="page" :to="{ name: 'visitas' }"
                :class="$route.name == 'visitas' ? 'text-warning fw-bold' : ''">Visitas</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" :to="{ name: 'personas' }"
                :class="$route.name == 'personas' ? 'text-warning fw-bold' : ''">Personas</router-link>
            </li>
            <li v-if="modeConeccion == 'Administrador'" class="nav-item">
              <router-link class="nav-link" :to="{ name: 'personamasinvitada' }"
                :class="$route.name == 'personamasinvitada' ? 'text-warning fw-bold' : ''">Consultar persona más
                invitada</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" :to="{ name: 'ayuda' }"
                :class="$route.name == 'ayuda' ? 'text-warning fw-bold' : ''">Ayuda</router-link>
            </li>
          </ul>
          <!-- Menú "modo coneccion" -->
          <ul class="navbar-nav">
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                {{ modeConeccion }}
              </a>
              <ul class="dropdown-menu">
                <li>
                  <a class="dropdown-item" href="#" @click="changeUserMode('Administrador')">Administrador</a>
                </li>
                <li>
                  <a class="dropdown-item" href="#" @click="changeUserMode('Usuario')">Usuario</a>
                </li>
              </ul>
            </li>
          </ul>
          <font-awesome-icon icon="fa-solid fa-user" class="ms-2" style="color: #ffffff;" />
        </div>
      </div>
    </nav>
  </div>
</template>
