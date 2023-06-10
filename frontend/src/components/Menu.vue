<script>

</script>

<template>
  <header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container-fluid">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo03"
          aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <router-link class="navbar-brand" :to="{ name: 'home' }"
          :class="$route.name == 'home' ? 'text-warning fw-bold' : ''">Home</router-link>
        <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <router-link class="nav-link" aria-current="page" :to="{ name: 'visitas' }"
                :class="$route.name == 'visitas' ? 'text-warning fw-bold' : ''">Visitas</router-link> 
            </li>
            <li class="nav-item">
              <router-link class="nav-link" :to="{ name: 'personas' }"
                :class="$route.name == 'personas' ? 'text-warning fw-bold' : ''">Personas</router-link>
            </li>                    
            <li class="nav-item">
              <router-link class="nav-link" :to="{ name: 'ayuda' }"
                :class="$route.name == 'ayuda' ? 'text-warning fw-bold' : ''">Ayuda</router-link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </header>
</template>


<style scoped>
.navbar {
  background-color: #a6acaf;
}

.navbar-brand:hover {
  color: white;
}

.nav-link:hover {
  color: white;
}</style>