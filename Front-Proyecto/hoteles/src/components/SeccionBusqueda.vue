<template>
    <div class="container">
        <div class="eslogan">
            <img src="/img/elogan2.jpg">
            <p>Tu viaje de ensueño, a un solo clic</p>
        </div>

        <div class="search">
            <Busqueda></Busqueda>
        </div>
  </div>
</template>

<script setup>
import Busqueda from '../components/Busqueda.vue'
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-top: 14px;
}

.eslogan {
  width: 100%;
  margin-bottom: 5px; /* Espacio entre la imagen y el formulario */
}

.search{
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}


p {
  position: absolute;
  top: 45%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1; /* Asegura que el texto esté encima de la imagen */
  color: white;
  font-size: 24px;
  font-weight: bold;
  text-align: center;
  background-color: rgba(0, 0, 0, 0.5); /* Fondo semi-transparente para mayor legibilidad */
  padding: 10px 20px;
}

img{
    width: 102%;
    height: auto;
    z-index: 0;
    margin-top: 7%;
    margin-left: -1%;
    
}

</style>