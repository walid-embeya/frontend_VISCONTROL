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
        <!-- <a class="navbar-brand" href="#">Navbar</a> -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown"
          aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
          <ul class="navbar-nav">   

            <li class="nav-item">
              <!-- <a class="nav-link active" aria-current="page" href="#">Home</a> -->
              <router-link class="navbar-brand" :to="{ name: 'home' }"
                :class="$route.name == 'home' ? 'text-warning fw-bold' : ''">Home</router-link>
            </li>
            
            <li class="nav-item">
              <!-- <a class="nav-link" href="#">Features</a> -->
              <router-link class="nav-link" aria-current="page" :to="{ name: 'visitas' }"
                :class="$route.name == 'visitas' ? 'text-warning fw-bold' : ''">Visitas</router-link>
            </li>

            <li class="nav-item">
              <!-- <a class="nav-link" href="#">Pricing</a> -->
              <router-link class="nav-link" :to="{ name: 'personas' }"
                :class="$route.name == 'personas' ? 'text-warning fw-bold' : ''">Personas</router-link>
            </li>

            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                Dropdown link
              </a>
              <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="#">Action</a></li>
                <li><a class="dropdown-item" href="#">Another action</a></li>
                <li><a class="dropdown-item" href="#">Something else here</a></li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </nav>

  </div>
</template>