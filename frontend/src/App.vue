<script setup>
  import Menu from './components/Menu.vue'
  // import AltaVisita from '@/formularios/AltaVisita.vue'
</script>

<template>
  <Menu></Menu>
  <div class="container">
    <router-view></router-view>
    <!-- <AltaVisita /> -->
  </div> 

</template>

<!-- 
<style scoped>       /*  scoped : para aplicar estilos sólo dentro este componente, pero no se aplican sobre los componentes que son dentro  */
       :deep(.badge) {         /*  deep selectors : para resolver o evitar el concepto de "scoped"  */
         cursor: pointer;
       }
</style> -->