<script>
export default {
  props: ['titulo']
}

</script>

<template>
  <!-- <div class="container alert alert-light"> -->
  <div class="mb-3">
    <h1 class="titulo">{{ titulo }}</h1>


  </div>

  <div>
    <slot></slot>
  </div>
</template>