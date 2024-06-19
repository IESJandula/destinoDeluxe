<template>
  <header>
    <Header></Header>
  </header>
  <div class="global">
    <div v-if="hotel" class="hotel-detail">
      <!-- Mostrar información del hotel -->
      <div class="hotel-info">
        <div class="image-container">
          <img :src="'/img/' + hotel.urlImagen" />
        </div>
        <div class="info-container">
          <h2>{{ hotel.nombre }}</h2>
          <div class="estrellas">
            <template v-for="n in calcularEstrellas(hotel.estrellas)">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                <path
                  d="M23.555 8.729a1.505 1.505 0 0 0-1.406-.98h-6.087a.5.5 0 0 1-.472-.334l-2.185-6.193a1.5 1.5 0 0 0-2.81 0l-.005.016-2.18 6.177a.5.5 0 0 1-.471.334H1.85A1.5 1.5 0 0 0 .887 10.4l5.184 4.3a.5.5 0 0 1 .155.543l-2.178 6.531a1.5 1.5 0 0 0 2.31 1.684l5.346-3.92a.5.5 0 0 1 .591 0l5.344 3.919a1.5 1.5 0 0 0 2.312-1.683l-2.178-6.535a.5.5 0 0 1 .155-.543l5.194-4.306a1.5 1.5 0 0 0 .433-1.661z"
                ></path>
              </svg>
            </template>
          </div>
          <p>{{ hotel.ubicacion }}</p>
          <p>Precio por noche: {{ hotel.precioNoche }}€</p>
          <p>
            Numero de habitaciones totales:
            {{ hotel.numeroDeHabitacionesTotales }}
          </p>
        </div>
        <!-- Otros detalles del hotel -->
      </div>

      <!-- Formulario de reserva -->
      <div class="container">
        <div class="card cart">
          <label class="title">RESERVA</label>
          <div class="steps">
            <div class="step">
              <div>
                <span>HOTEL</span>
                <p>{{ hotel.nombre }}</p>
                <p>{{ hotel.ubicacion }}</p>
              </div>
              <hr />
              <div>
                <span>VERIFICACION DISPONIBILIDAD</span>
                
                <form class="form">
                  <p>Fecha de llegada:</p>
                  <input
                      type="date"
                      placeholder="Fecha de llegada"
                      class="input_field"
                      v-model="fechaLlegada"
                      @change="calcularPrecioTotal"
                    />
                  <p class="parrafo">Fecha de salida:</p>
                  <input
                      type="date"
                      placeholder="Fecha de salida"
                      class="input_field"
                      v-model="fechaSalida"
                      @change="calcularPrecioTotal"
                  />
                  <br>
                  <!-- <button class="mi-btn">Verificar Disponibilidad</button> -->
                </form>
              </div>
              <hr />
              <div class="promo">
                <span>¿TIENES CODIGO DE DESCUENTO?</span>
                <form class="form">
                  <input
                    type="text"
                    placeholder="Enter a Promo Code"
                    class="input_field"
                  />
                  <button>Aplicar</button>
                </form>
              </div>
              <hr />
              <div class="payments">
                <span>PRECIO POR NOCHE: {{ hotel.precioNoche }}€</span>
                <div class="details">
                  <span>Subtotal:</span>
                  <span>€{{subtotal}}</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="card checkout">
          <div class="footer">
            <label class="price"><span>€{{subtotal}}</span></label>
            <button class="checkout-btn" @click="reservar">Reservar</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import Header from "../components/Header.vue";
import { useAuthStore } from "../authStore";
import { useToast } from 'vue-toastification';

const route = useRoute();
const authStore = useAuthStore();
const router = useRouter();
const hotel = ref(null);
const isLoading = ref(true);
const fechaLlegada = ref("");
const fechaSalida = ref("");
const subtotal = ref(0);

const toast = useToast();

onMounted(async () => {
  try {
    const response = await fetch(
      `http://localhost:8080/api/hoteles/${route.params.id}`
    );
    if (!response.ok) {
      throw new Error("Error al obtener los detalles del hotel");
    }
    hotel.value = await response.json();
    isLoading.value = false;
  } catch (error) {
    console.error("Error al obtener los detalles del hotel:", error);
    isLoading.value = false;
    error.value = "No se pudieron cargar los detalles del hotel.";
  }


});

// Función para crear la reserva


// Otros métodos y variables


const reservar = async () => {
  // Verificamos si el usuario está autenticado
  if (!authStore.isLoggedIn) {
    // Si el usuario no está autenticado, redirigimos a la página de inicio de sesión
    router.push("/iniciar-sesion");
    return;
  }

  // El usuario está autenticado, procedemos con la creación de la reserva
  try {
    const response = await fetch("http://localhost:8080/api/reservas/crear", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        idUsuario: authStore.user.id, // Utilizamos el ID de usuario del store de autenticación
        idHotel: route.params.id,
        fechaInicio: fechaLlegada.value,
        fechaFin: fechaSalida.value,
      }),
    });
    console.log(authStore.user.id);
    if (!response.ok) {
      throw new Error("Error al crear la reserva");
    }

    toast.info('Reserva realizada con exito')
  } catch (error) {
    console.error("Error al crear la reserva:", error);
    alert(
      "Ocurrió un error al crear la reserva. Por favor, inténtalo de nuevo."
    );
  }
};

const calcularEstrellas = (cantidad) => {
  return Array.from({ length: cantidad }, (_, index) => index + 1);
};

const calcularPrecioTotal = () => {
  // Convertir las fechas de llegada y salida a objetos Date
  const fechaInicio = new Date(fechaLlegada.value);
  const fechaFin = new Date(fechaSalida.value);

  // Calcular la diferencia en milisegundos entre las fechas
  const diferenciaTiempo = fechaFin.getTime() - fechaInicio.getTime();

  // Convertir la diferencia de tiempo de milisegundos a días
  const diasReserva = Math.ceil(diferenciaTiempo / (1000 * 3600 * 24));

  // Calcular el subtotal multiplicando el precio por noche del hotel por el número de días de reserva
  subtotal.value = hotel.value.precioNoche * diasReserva;

  // Calcular y devolver el precio total sumando subtotal, envío y impuestos
  return subtotal.value;
};
</script>

<style scoped>
.hotel-info {
  background-color: #f9f9f9;
  border-radius: 10px; 
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); 
}
.image-container {
  width: 700px; 
  max-height: 467px; 
  overflow: hidden; 
  border-radius: 10px; 
}

.image-container img {
  width: 100%; 
  height: auto; 
  display: block; 
}

.hotel-info img {
  border-radius: 10px; 
  margin-bottom: 20px; 
}

.info-container {
  margin-left: 20px; 
}

.hotel-info h2 {
  font-size: 24px; 
  color: #333; 
  margin-bottom: 10px; 
}

.hotel-info p {
  font-size: 16px; 
  color: #666; 
  margin-bottom: 5px; 
}
.mi-btn{
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 10px 18px;
  gap: 10px;
  width: 100%;
  height: 36px;
  background: rgba(16, 86, 82, 0.75);
  box-shadow: 0px 0.5px 0.5px #f3d2c9, 0px 1px 0.5px rgba(239, 239, 239, 0.5);
  border-radius: 5px;
  border: 0;
  font-style: normal;
  font-weight: 600;
  font-size: 12px;
  line-height: 15px;
  color: #000000;
  margin-top: 10px;
}

.hotel-detail{
  display: flex;
}
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
  fill: #ffd700; 
  width: 20px; 
  height: 20px;
}
.global {
  margin-top: 10%;
  margin-left: 1%;
}

.container {
  display: grid;
  grid-template-columns: auto;
  gap: 0px;
}

hr {
  height: 1px;
  background-color: rgba(16, 86, 82, 0.75);
  border: none;
}

.card {
  width: 400px;
  background: rgb(255, 250, 235);
  box-shadow: 0px 187px 75px rgba(0, 0, 0, 0.01),
    0px 105px 63px rgba(0, 0, 0, 0.05), 0px 47px 47px rgba(0, 0, 0, 0.09),
    0px 12px 26px rgba(0, 0, 0, 0.1), 0px 0px 0px rgba(0, 0, 0, 0.1);
}

.title {
  width: 100%;
  height: 40px;
  position: relative;
  display: flex;
  align-items: center;
  padding-left: 20px;
  border-bottom: 1px solid rgba(16, 86, 82, 0.75);
  font-weight: 700;
  font-size: 11px;
  color: #000000;
}

/* Cart */
.cart {
  border-radius: 19px 19px 0px 0px;
}

.cart .steps {
  display: flex;
  flex-direction: column;
  padding: 20px;
}

.cart .steps .step {
  display: grid;
  gap: 10px;
}

.cart .steps .step span {
  font-size: 13px;
  font-weight: 600;
  color: #000000;
  margin-bottom: 8px;
  display: block;
}

.cart .steps .step p {
  font-size: 11px;
  font-weight: 600;
  color: #000000;
}

/* Promo */
.promo form {
  display: grid;
  grid-template-columns: 1fr 80px;
  gap: 10px;
  padding: 0px;
}

.input_field {
  width: auto;
  height: 36px;
  padding: 0 0 0 12px;
  border-radius: 5px;
  outline: none;
  border: 1px solid rgb(16, 86, 82);
  background-color: rgb(251, 243, 228);
  transition: all 0.3s cubic-bezier(0.15, 0.83, 0.66, 1);
}

.input_field:focus {
  border: 1px solid transparent;
  box-shadow: 0px 0px 0px 2px rgb(251, 243, 228);
  background-color: rgb(201, 193, 178);
}

.promo form button {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 10px 18px;
  gap: 10px;
  width: 100%;
  height: 36px;
  background: rgba(16, 86, 82, 0.75);
  box-shadow: 0px 0.5px 0.5px #f3d2c9, 0px 1px 0.5px rgba(239, 239, 239, 0.5);
  border-radius: 5px;
  border: 0;
  font-style: normal;
  font-weight: 600;
  font-size: 12px;
  line-height: 15px;
  color: #000000;
}

/* Checkout */
.payments .details {
  display: grid;
  grid-template-columns: 10fr 1fr;
  padding: 0px;
  gap: 5px;
}

.payments .details span:nth-child(odd) {
  font-size: 12px;
  font-weight: 600;
  color: #000000;
  margin: auto auto auto 0;
}

.payments .details span:nth-child(even) {
  font-size: 13px;
  font-weight: 600;
  color: #000000;
  margin: auto 0 auto auto;
}

.checkout .footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 10px 10px 20px;
  background-color: rgba(16, 86, 82, 0.5);
}

.price {
  position: relative;
  font-size: 22px;
  color: #2b2b2f;
  font-weight: 900;
}

.checkout .checkout-btn {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  width: 150px;
  height: 36px;
  background: rgba(16, 86, 82, 0.55);
  box-shadow: 0px 0.5px 0.5px rgba(16, 86, 82, 0.75),
    0px 1px 0.5px rgba(16, 86, 82, 0.75);
  border-radius: 7px;
  border: 1px solid rgb(16, 86, 82);
  color: #000000;
  font-size: 13px;
  font-weight: 600;
  transition: all 0.3s cubic-bezier(0.15, 0.83, 0.66, 1);
}
</style>
