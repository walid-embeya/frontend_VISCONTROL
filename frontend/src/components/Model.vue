<script>
export default {
  props: ['titulo']
}

</script>

<template>
  <div>
    <h1 class="titulo">{{ titulo }}</h1>
  </div>
  <slot></slot>
  <!-- </div> -->
</template>
