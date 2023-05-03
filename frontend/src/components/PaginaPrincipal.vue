<script>

import Modelo from './Model.vue'

export default {
  components: { Modelo },   ///// registro local de los componentes
}
</script>

<template>
    <main>
        <div class="logo-container">
          <figure class="figure">
            <img src="../images/viscontrol.jpg" class="img-fluid" width="600" height="600" alt="Logo VISCONTROL">
          </figure>
        </div>

        <div class="titulo">
            <p>VISCONTROL</p>
        </div>
  
    </main>



    <!-- <footer class="footer">
        <div class="container">
          <div class="row">
            <div class="col-md-12 text-center">
            <div class="socialMedia">
                <font-awesome-icon :icon="['fab', 'square-facebook']" />
                <font-awesome-icon icon="fa-brands fa-square-facebook" />
            </div>
               


              <p>Contáctenos</p> 
              <hr>
              <p><b>VISCONTRIL V1.0 Mayo 2023</b></p>
              <p>© Copyright. Todos los derechos reservados.</p>
            </div>
          </div>
        </div> -->

        <!-- <div class="foot">
          
              <hr>
              <p><b>VISCONTRIL V1.0 Mayo 2023</b></p>
              <p>© Copyright. Todos los derechos reservados.</p>

        </div> 
    </footer> -->




    <footer class="footer">
      <hr>  
      <div class="container">
          <div class="row">
            <div class="col-md-4 text-center" style="text-align: center;">
              <p></p>
              <p>Contáctenos</p> 
            </div>

            <div class="col-md-4 text-center">
              <p><b>VISCONTRIL V1.0 Mayo 2023</b></p>
              <p>© Copyright. Todos los derechos reservados.</p>
            </div>

            <div class="col-md-4 text-center">
                <p>Síguenos en:</p>
                <div class="socialMedia">
                  <font-awesome-icon :icon="['fab', 'facebook']" class="me-2"/>
                  <font-awesome-icon :icon="['fab', 'twitter']" />
                  <font-awesome-icon :icon="['fab', 'instagram']" />
                  <font-awesome-icon :icon="['fab', 'linkedin']" />
                </div>  
            </div>


          </div>   
        </div>
  </footer>
    
</template>


<style scoped>
  .logo-container {
     display: flex;
     /* establecer un estilo de diseño de caja flexible */
     align-items:normal;
     /* centrar verticalmente los elementos */
     justify-content: center;    /* centrar horizontalmente el contenedor */
  }

  .titulo {
    font-size: 3.5em;
    color: cornflowerblue;
    padding-bottom: 2em;
  }

  .foot {
    text-align: center;
  }
  /* .footer {
    background-color: cornflowerblue;
  } */

</style>