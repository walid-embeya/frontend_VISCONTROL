<script setup>
  import Menu from './components/Menu.vue'
</script>

<template>
  <Menu />
  <div class="container">
    <router-view></router-view>
  </div> 

</template>

<!-- 
<style scoped>       /*  scoped : para aplicar estilos sólo dentro este componente, pero no se aplican sobre los componentes que son dentro  */
       :deep(.badge) {         /*  deep selectors : para resolver o evitar el concepto de "scoped"  */
         cursor: pointer;
       }
</style> -->