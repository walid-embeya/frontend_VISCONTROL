<script>
export default {
    props: ['titulo']
}

</script>

<template>
     <!-- <div class="container alert alert-light"> -->
    <div>
        <h1 class="titulo">{{ titulo }}</h1>
    
        <slot></slot>
    </div>
</template>