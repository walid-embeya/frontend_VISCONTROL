<script setup>
import Menu from './components/Menu.vue'
</script>

<template>
  <div>
    <Menu></Menu>

    <div class="container">
      <router-view></router-view>
    </div>
  </div>
</template>