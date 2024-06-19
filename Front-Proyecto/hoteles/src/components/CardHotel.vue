<template>
    <div class="hotel-card" >
        
      <h2>Hoteles en {{ ubicacion }}</h2>
        <div v-for="hotel in hoteles" :key="hotel.id" class="hotel-info" @click="verDetalle(hotel.id)">
            <div class="hotel-image">
                <img :src="'/img/'+ hotel.urlImagen">
            </div>
            <div class="detalles">
              <div class="nombre-y-estrellas">
                <h2>{{ hotel.nombre }}</h2>
                <div class="estrellas">
                  <template v-for="n in calcularEstrellas(hotel.estrellas)">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                      <path d="M23.555 8.729a1.505 1.505 0 0 0-1.406-.98h-6.087a.5.5 0 0 1-.472-.334l-2.185-6.193a1.5 1.5 0 0 0-2.81 0l-.005.016-2.18 6.177a.5.5 0 0 1-.471.334H1.85A1.5 1.5 0 0 0 .887 10.4l5.184 4.3a.5.5 0 0 1 .155.543l-2.178 6.531a1.5 1.5 0 0 0 2.31 1.684l5.346-3.92a.5.5 0 0 1 .591 0l5.344 3.919a1.5 1.5 0 0 0 2.312-1.683l-2.178-6.535a.5.5 0 0 1 .155-.543l5.194-4.306a1.5 1.5 0 0 0 .433-1.661z"></path>
                    </svg>
                  </template>
                </div>
              </div>
              <p>Ubicacion: {{ hotel.ubicacion }}</p>
              <p>Precio por noche: {{ hotel.precioNoche }}</p>
              <p>Numero de habitaciones totales: {{ hotel.numeroDeHabitacionesTotales }}</p>
              <button v-if="isLogged" class="reservar-btn">Reservar</button>
            </div>
        </div>
      </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import { useRoute, useRouter } from 'vue-router';
  import { useAuthStore } from '../authStore.js';
  
  const route = useRoute();
  const router = useRouter();
  const ubicacion = ref('');
  const hoteles = ref([]);
  const isLoading = ref(true);
  const { isLogged } = useAuthStore();
  
  onMounted(async () => {
    // Obtener la ubicación de la URL
    ubicacion.value = route.query.ubicacion;
  
    // Llamar a la función para buscar hoteles usando la ubicación
    await buscarHoteles();
  });
  
  const buscarHoteles = async () => {
    try {
      // Realizar la solicitud al endpoint de búsqueda de hoteles por ubicación
      const response = await fetch(`http://localhost:8080/api/hoteles/buscar-por-ubicacion?ubicacion=${ubicacion.value}`);
      const data = await response.json();
      console.log(data);
  
      // Asignar los hoteles obtenidos a la variable hoteles
      hoteles.value = data;
  
      // Marcar isLoading como falso para indicar que se han cargado los hoteles
      isLoading.value = false;
    } catch (error) {
      console.error('Error al buscar hoteles:', error);
    }
  };

  const calcularEstrellas = (cantidad) => {
  return Array.from({ length: cantidad }, (_, index) => index + 1);
  };


  const verDetalle = (hotelId) => {
    router.push({ name: 'HotelDetalle', params: { id: hotelId } });
  };
  </script>
  
<style scoped>
.nombre-y-estrellas {
  display: flex;
  align-items: center;
}

.estrellas {
  display: flex;
  padding-left: 5px;
  margin-top: -8px;
}

.estrellas svg {
  fill: #FFD700; 
  width: 20px; 
  height: 20px;
}

.hotel-card {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.hotel-info {
  display: flex;
  border: 1px solid #e0e0e0;
  border-radius: 10px;
  overflow: hidden;
  margin-bottom: 30px;
  width: 80%;
  max-width: 900px;
}

.hotel-image {
  flex: 1;
  overflow: hidden;
}

.hotel-image img {
  width: 400px;
  height: 200px;
  object-fit: cover;
  border-radius: 10px 0 0 10px;
  transition: transform 0.3s ease;
}

.hotel-info:hover .hotel-image img {
  transform: scale(1.1);
}

.detalles {
  flex: 1;
  padding: 20px;
}

.detalles h2 {
  margin-top: 0;
  font-size: 24px;
  color: #333; /* Color del texto principal */
}

.detalles p {
  margin: 10px 0;
  color: #666; /* Color del texto secundario */
}

.estrellas {
  font-size: 18px;
  color: #FFD700; /* Color de las estrellas */
}
</style>
  