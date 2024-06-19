
<template>
<main>
    <header>
        <Header></Header>
    </header>
    
    <SeccionBusqueda></SeccionBusqueda>
    
    <div class="container">
        <div>
            <h2>Lugares de moda</h2>
            <p>Opciones mas demandas por nuestros clientes</p>
        </div>
        
        <div class="grid-1">
            <div class="city-container" @click="buscarHoteles('Madrid')">
                <img src="/img/madrid.jpg" class="img-ciudad">
                <p class="city-name">Madrid <img src="/img/icono.jpg" class="icono"></p>
            </div>
            <div class="city-container" @click="buscarHoteles('Barcelona')">
                <img src="/img/barcelona.jpg" class="img-ciudad">
                <p class="city-name">Barcelona <img src="/img/icono.jpg" class="icono"></p>
            </div>
        </div>

        <div class="grid-2">
            <div class="city-container" @click="buscarHoteles('Sevilla')">
                <img src="/img/sevilla.jpg" class="img-ciudad">
                <p class="city-name">Sevilla <img src="/img/icono.jpg" class="icono"></p>
            </div>
            <div class="city-container" @click="buscarHoteles('Cordoba')">
                <img src="/img/Cordoba.jpg" class="img-ciudad">
                <p class="city-name">Cordoba <img src="/img/icono.jpg" class="icono"></p>
            </div>
            <div class="city-container" @click="buscarHoteles('Valencia')">
                <img src="/img/valencia.jpg" class="img-ciudad">
                <p class="city-name">Valencia <img src="/img/icono.jpg" class="icono"></p>
            </div>
        </div>
    </div>
    <Footer></Footer>
</main>

</template>

<script setup>
import Header from '../components/Header.vue'
import SeccionBusqueda from '../components/SeccionBusqueda.vue'
import Footer from '../components/Footer.vue'
import { useRouter } from 'vue-router';

const router = useRouter();

const buscarHoteles = (ubicacion) => {
  // Navegar a la vista de hoteles con la ubicación como parámetro en la URL
  router.push({ name: 'Hoteles', query: { ubicacion: ubicacion } });
};
</script>

<style scoped>

.container{
    margin-top: 20px;
}

.icono{
    width: 20px;
}

.img-ciudad{
    width: 100%;
    height: 300px;
    border-radius: 10px;
}

.grid-1{
    display: grid;
    grid-template-columns: repeat(2,1fr);
    gap: 20px;
}

.grid-2{
    margin-top: 20px;
    display: grid;
    gap: 20px;
    grid-template-columns: repeat(3,1fr);
    margin-bottom: 20px;
}

.city-container {
    position: relative;
    text-align: center;
}

.city-name {
  position: absolute;
  top: 50%;
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
</style>
