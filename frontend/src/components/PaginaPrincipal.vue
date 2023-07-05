<template>
  <main>
    <div class="logo-container">
      <figure>
        <img src="../images/viscontrol.jpg" class="img-fluid" width="600" height="600" alt="Logo VISCONTROL">
      </figure>
    </div>
    <div class="titulo">
      <p>VISCONTROL</p>
    </div>
  </main>

  <footer class="footer">
    <hr>
    <div class="container">
      <div class="row">
        <div class="col-md-4 text-center contactenos">
          <p class="mt-3">Contáctenos</p>
        </div>
        <div class="col-md-4 text-center">
          <p><b>VISCONTROL V2.0 Junio 2023</b></p>
          <p>© Copyright. Todos los derechos reservados.</p>
        </div>
        <div class="col-md-4 text-center">
          <p>Síguenos en:</p>
          <div class="socialMedia">
            <font-awesome-icon :icon="['fab', 'facebook']" class="me-2" />
            <font-awesome-icon :icon="['fab', 'twitter']" class="me-2" />
            <font-awesome-icon :icon="['fab', 'instagram']" class="me-2" />
            <font-awesome-icon :icon="['fab', 'linkedin']" class="me-2" />
          </div>
        </div>
      </div>
    </div>
  </footer>
</template>

<style scoped>
.logo-container {
  margin-top: 2em;
  display: flex;
  /* establecer un estilo de diseño de caja flexible */
  align-items: normal;
  /* centrar verticalmente los elementos */
  justify-content: center;
  /* centrar horizontalmente el contenedor */
}

.titulo {
  font-size: 4em;
  color: rgb(50, 109, 219);
  padding-bottom: 0.9em;
}

.contactenos {
  text-align: center;
}

.footer {
  text-align: center;
}

@media (max-width: 768px) {
  .titulo {
    font-size: 2em;
  }
}
</style>