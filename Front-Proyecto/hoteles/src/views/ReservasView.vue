<template>
    <Header></Header>
  
    <div class="container">
      <h2>Mis Reservas </h2>
      <ul class="reserva-list">
        <li v-for="reserva in reservas" :key="reserva.id" class="reserva-card">
          <div class="hotel-image">
            <img :src="'/img/'+ reserva.hotel.urlImagen" alt="Hotel Image">
          </div>
          <div class="detalles">
            <h3>{{ reserva.hotel.nombre }}</h3>
            <p>Fecha de llegada: {{ reserva.fechaInicio }}</p>
            <p>Fecha de salida: {{ reserva.fechaFin }}</p>
            <p>Precio total: {{ reserva.precioTotal }}€</p>
          </div>
        </li>
      </ul>
    </div>
  
    <Footer class="footer"></Footer>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import Header from '../components/Header.vue';
  import Footer from '../components/Footer.vue';
  import { useAuthStore } from '../authStore';
  
  const authStore = useAuthStore();
  const reservas = ref([]);
  
  onMounted(async () => {
    try {
      if (!authStore.isLoggedIn || !authStore.user) {
        throw new Error('Usuario no autenticado');
      }
  
      const userEmail = authStore.user.email;
      const endpoint = `http://localhost:8080/api/reservas/cliente/reservas?email=${encodeURIComponent(userEmail)}`;
  
      const response = await fetch(endpoint);
      if (!response.ok) {
        throw new Error('Error al obtener las reservas del cliente');
      }
      reservas.value = await response.json();
    } catch (error) {
      console.error('Error al obtener las reservas:', error);
    }
  });
  </script>
  
  <style scoped>
  .container {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 150px; 
  }
  
  h2{
    text-align: center;
  }
  .reserva-list {
    list-style-type: none;
    padding: 0;
    margin-left: 8%;
  }
  
  .reserva-card {
    margin-bottom: 40px; 
    border: 1px solid #e0e0e0;
    border-radius: 10px;
    overflow: hidden;
    display: flex;
    width: 80%; 
    max-width: 600px; 
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); 
    transition: transform 0.3s ease;
  }
  
  .reserva-card:hover {
    transform: translateY(-5px); 
  }
  
  .hotel-image {
    flex: 1;
  }
  
  .hotel-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  
  .detalles {
    flex: 2;
    padding: 20px;
  }
  
  .detalles h3 {
    margin-top: 0;
    font-size: 24px; 
    color: #333; 
  }
  
  .detalles p {
    margin: 10px 0;
    color: #666;
  }
  </style>
  