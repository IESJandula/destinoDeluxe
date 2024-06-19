<template>
  
    <div class="search-form">
        <input type="text" v-model="ubicacionInput" placeholder="🛏️Donde quieres ir">
        <button class="search-button" @click="buscarHoteles">Buscar</button>
    </div>
  
</template>


<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const ubicacionInput = ref('');
const router = useRouter();

const buscarHoteles = () => {
  const ubicacion = encodeURIComponent(ubicacionInput.value.trim()); // Codificar la ubicación para la URL
  if (ubicacion) {
    const endpoint = `http://localhost:8080/api/hoteles/buscar-por-ubicacion?ubicacion=${ubicacion}`;
    fetch(endpoint)
      .then(response => {
        if (!response.ok) {
          throw new Error('Error en la solicitud');
        }
        return response.json();
      })
      .then(data => {
        console.log(data); 
        router.push({ name: 'Hoteles', query: { ubicacion: ubicacion } });// Aquí puedes manejar la respuesta del servidor
      })
      .catch(error => {
        console.error('Error al buscar hoteles:', error);
        alert('Hubo un error al buscar hoteles. Por favor, inténtalo de nuevo más tarde.');
      });
  } else {
    alert('Por favor ingresa una ubicación válida');
  }
};
</script>

<style scoped>
.search-form {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px; /* Espacio entre elementos */
  background-color:rgb(170, 214, 211) ;
  padding: 5px;
  border-radius: 15px;
}

input[type="text"]{
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 10px;
  font-size: 16px;
  flex: 1; 
}

button.search-button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #ffffff;
  color: rgb(0, 0, 0);
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button.search-button:hover {
  background-color: #00523e;
}
</style>