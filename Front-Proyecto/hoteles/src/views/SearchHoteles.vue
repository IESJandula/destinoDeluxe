<template>
  <header>
    <Header></Header>
  </header>
  
  
  <div class="global">
    <div class="search">
      <Busqueda></Busqueda>
    </div>
    <Hoteles></Hoteles>
  </div>
  <Footer></Footer>
</template>

<script setup>
import Hoteles from '../components/CardHotel.vue'
import Header from '../components/Header.vue'
import Busqueda from '../components/Busqueda.vue';
import Footer from '../components/Footer.vue'

</script>

<style scoped>
.global{
  margin-top: 10%;
}

.search{
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>