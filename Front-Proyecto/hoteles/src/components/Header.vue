<template>
    <main>
        <div class="container">
            
            <div class="content">
                <RouterLink to="/" class="enlace">
                  <img src="../assets/DestinoDeluxe.png" class="img-logo"></img>
                </RouterLink>
                <div v-if="!authStore.isLoggedIn" class="botones">
                    <button class="boton">
                        <RouterLink to="/registrarse" class="enlace">
                            <span>Registrarse </span>
                            <svg width="30" height="30" viewBox="0 0 74 74" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <circle cx="37" cy="37" r="35.5" stroke="black" stroke-width="3"></circle>
                                <path d="M25 35.5C24.1716 35.5 23.5 36.1716 23.5 37C23.5 37.8284 24.1716 38.5 25 38.5V35.5ZM49.0607 38.0607C49.6464 37.4749 49.6464 36.5251 49.0607 35.9393L39.5147 26.3934C38.9289 25.8076 37.9792 25.8076 37.3934 26.3934C36.8076 26.9792 36.8076 27.9289 37.3934 28.5147L45.8787 37L37.3934 45.4853C36.8076 46.0711 36.8076 47.0208 37.3934 47.6066C37.9792 48.1924 38.9289 48.1924 39.5147 47.6066L49.0607 38.0607ZM25 38.5L48 38.5V35.5L25 35.5V38.5Z" fill="black"></path>
                            </svg>
                        </RouterLink>
                    </button>

                    <button class="boton">
                        <RouterLink to="/iniciar-sesion" class="enlace">
                            <span>Iniciar Sesion </span>
                            <svg width="30" height="30" viewBox="0 0 74 74" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <circle cx="37" cy="37" r="35.5" stroke="black" stroke-width="3"></circle>
                                <path d="M25 35.5C24.1716 35.5 23.5 36.1716 23.5 37C23.5 37.8284 24.1716 38.5 25 38.5V35.5ZM49.0607 38.0607C49.6464 37.4749 49.6464 36.5251 49.0607 35.9393L39.5147 26.3934C38.9289 25.8076 37.9792 25.8076 37.3934 26.3934C36.8076 26.9792 36.8076 27.9289 37.3934 28.5147L45.8787 37L37.3934 45.4853C36.8076 46.0711 36.8076 47.0208 37.3934 47.6066C37.9792 48.1924 38.9289 48.1924 39.5147 47.6066L49.0607 38.0607ZM25 38.5L48 38.5V35.5L25 35.5V38.5Z" fill="black"></path>
                            </svg>
                        </RouterLink>
                    </button>
                </div>
                <div v-else class="btn-cerrar">
                    <RouterLink to="/reservas" class="enlace">
                      <div>
                          <button class="boton-reserva">Mis reservas</button>
                      </div>
                    </RouterLink>
                    <RouterLink to="/" class="enlace">
                      <div>
                          <button class="boton-logout" @click="logout">Cerrar Sesión</button>
                      </div>
                    </RouterLink>
                </div>
            </div>
        </div>
    </main>
</template>

<script setup>
import { useAuthStore } from '../authStore.js';
import { useRouter } from 'vue-router';
import { useToast } from 'vue-toastification';

const authStore = useAuthStore();
const router = useRouter();
const toast = useToast();

const logout = () => {
  authStore.logout();
  toast.error('Sesion cerrada')
  router.push('/'); // Redirige al usuario a la página principal después de cerrar sesión
};
</script>

<style scoped>
.boton-reserva {
  background-color: #007bff; /* Azul bonito */
  color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.boton-reserva:hover {
  background-color: #0056b3; 
}

.btn-cerrar{
    display: flex;
    justify-content: flex-end;
    align-items: flex-end;
    flex:1;
}
.boton-logout {
  cursor: pointer;
  font-weight: 700;
  font-family: Helvetica, "sans-serif";
  transition: all 0.2s;
  padding: 10px 20px;
  border-radius: 100px;
  background: red; 
  border: none;
  color: white; 
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 15px;
}

.boton-logout:hover {
  background: #ff0000; 
}

.boton-logout:active {
  transform: scale(0.95);
}

main{
    display: flex;
    align-items: flex-start;
    justify-content: center;
    position: absolute;
    max-width: 1200px;
    top: 0;
    left: 0;
    right: 0;
    height: 25%;
    margin: 0 auto;
    width: 100%; 
    background-color:rgb(170, 214, 211) ;
    height: 15%;
}
button{
    margin-right: 10px;

}

.enlace{
    text-decoration: none;
    color: black;
}
.img-logo{
    width: 180px;
}

.content{
    display: flex;
    align-items: center;
    width: 100%;
    margin-top: -50px;
}

.botones{
    display: flex;
    justify-content: flex-end;
    width: 100%;
}


.container{
    display: flex;
    justify-content: space-between;
    width: 100%;
    padding: 20px;
}

button {
  cursor: pointer;
  font-weight: 700;
  font-family: Helvetica,"sans-serif";
  transition: all .2s;
  padding: 10px 20px;
  border-radius: 100px;
  background: #ffffff;
  border: 1px solid transparent;
  display: flex;
  align-items: center;
  font-size: 15px;
}

button:hover {
  background: #007e56;
}

button > svg {
  width: 34px;
  margin-left: 10px;
  transition: transform .3s ease-in-out;
}

button:hover svg {
  transform: translateX(5px);
}

button:active {
  transform: scale(0.95);
}

</style>