<template>
<div class="global">
  <div class="form-container">
    <p class="title">Crear Cuenta</p>
    <form class="form" @submit.prevent="registrar">
      <input v-model="nombre" type="text" class="input" placeholder="Nombre" />
      <input v-model="email" type="email" class="input" placeholder="Email" />
      <input v-model="password" type="password" class="input" placeholder="Password" />
      <input v-model="edad" type="number" class="input" placeholder="Edad" />
      <input v-model="nacionalidad" type="text" class="input" placeholder="Nacionalidad" />
      <input v-model="idioma" type="text" class="input" placeholder="Idioma" />
      <button class="form-btn" type="submit">Crear Cuenta</button>
    </form>
    
      <p class="sign-up-label">
        ¿Ya tienes una cuenta?<RouterLink to="/iniciar-sesion" ><span class="sign-up-link">Inica Sesion</span></RouterLink>
      </p>
    
    <div class="buttons-container">
      <div class="apple-login-button">
        <svg
          stroke="currentColor"
          fill="currentColor"
          stroke-width="0"
          class="apple-icon"
          viewBox="0 0 1024 1024"
          height="1em"
          width="1em"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M747.4 535.7c-.4-68.2 30.5-119.6 92.9-157.5-34.9-50-87.7-77.5-157.3-82.8-65.9-5.2-138 38.4-164.4 38.4-27.9 0-91.7-36.6-141.9-36.6C273.1 298.8 163 379.8 163 544.6c0 48.7 8.9 99 26.7 150.8 23.8 68.2 109.6 235.3 199.1 232.6 46.8-1.1 79.9-33.2 140.8-33.2 59.1 0 89.7 33.2 141.9 33.2 90.3-1.3 167.9-153.2 190.5-221.6-121.1-57.1-114.6-167.2-114.6-170.7zm-105.1-305c50.7-60.2 46.1-115 44.6-134.7-44.8 2.6-96.6 30.5-126.1 64.8-32.5 36.8-51.6 82.3-47.5 133.6 48.4 3.7 92.6-21.2 129-63.7z"
          ></path>
        </svg>
        <span>Inicia sesion con Apple</span>
      </div>
      <div class="google-login-button">
        <svg
          stroke="currentColor"
          fill="currentColor"
          stroke-width="0"
          version="1.1"
          x="0px"
          y="0px"
          class="google-icon"
          viewBox="0 0 48 48"
          height="1em"
          width="1em"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            fill="#FFC107"
            d="M43.611,20.083H42V20H24v8h11.303c-1.649,4.657-6.08,8-11.303,8c-6.627,0-12-5.373-12-12
	c0-6.627,5.373-12,12-12c3.059,0,5.842,1.154,7.961,3.039l5.657-5.657C34.046,6.053,29.268,4,24,4C12.955,4,4,12.955,4,24
	c0,11.045,8.955,20,20,20c11.045,0,20-8.955,20-20C44,22.659,43.862,21.35,43.611,20.083z"
          ></path>
          <path
            fill="#FF3D00"
            d="M6.306,14.691l6.571,4.819C14.655,15.108,18.961,12,24,12c3.059,0,5.842,1.154,7.961,3.039l5.657-5.657
	C34.046,6.053,29.268,4,24,4C16.318,4,9.656,8.337,6.306,14.691z"
          ></path>
          <path
            fill="#4CAF50"
            d="M24,44c5.166,0,9.86-1.977,13.409-5.192l-6.19-5.238C29.211,35.091,26.715,36,24,36
	c-5.202,0-9.619-3.317-11.283-7.946l-6.522,5.025C9.505,39.556,16.227,44,24,44z"
          ></path>
          <path
            fill="#1976D2"
            d="M43.611,20.083H42V20H24v8h11.303c-0.792,2.237-2.231,4.166-4.087,5.571
	c0.001-0.001,0.002-0.001,0.003-0.002l6.19,5.238C36.971,39.205,44,34,44,24C44,22.659,43.862,21.35,43.611,20.083z"
          ></path>
        </svg>
        <span>Inicia sesion con Google</span>
      </div>
    </div>
  </div>
</div>
</template>

<script setup>
import { ref } from 'vue';
import { useToast } from 'vue-toastification';
import { useRouter } from 'vue-router';
const router = useRouter();
const toast = useToast();

const nombre = ref('');
const email = ref('');
const password = ref('');
const edad = ref('');
const nacionalidad = ref('');
const idioma = ref('');

const registrar = async () => {
  try {
    const response = await fetch('http://localhost:8080/api/clientes/registrar', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        nombre: nombre.value,
        email: email.value,
        contrasena: password.value,
        edad: edad.value,
        nacionalidad: nacionalidad.value,
        idioma: idioma.value
      })
    });

    const data = await response.text();
    console.log(data); // Aquí puedes manejar la respuesta según tus necesidades
    if (response.ok) {
      // Mostrar el toast de éxito
      toast.success('Cuenta creada correctamente');

      router.push('/')
    } else {
      // Mostrar un toast de error si la respuesta no es exitosa
      toast.error(data.message || 'Todos los campos son obligatorios');
    }

    // Lógica adicional después de registrar el cliente, como mostrar un mensaje de éxito o redirigir a otra página
  } catch (error) {
    console.error('Error al registrar el cliente:', error);
    // Aquí puedes manejar el error, por ejemplo, mostrando un mensaje de error al usuario
  }
};
</script>

<style scoped>

.global{
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 5%;
}

.form-container {
  width: 350px;
  height: 750px;
  background-color: #fff;
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
  border-radius: 10px;
  box-sizing: border-box;
  padding: 20px 30px;
}

.title {
  text-align: center;
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
        "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
  margin: 10px 0 30px 0;
  font-size: 28px;
  font-weight: 800;
}

.sub-title {
  margin: 0;
  margin-bottom: 5px;
  font-size: 9px;
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
        "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
}

.form {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 18px;
  margin-bottom: 15px;
}

.input {
  border-radius: 20px;
  border: 1px solid #c0c0c0;
  outline: 0 !important;
  box-sizing: border-box;
  padding: 12px 15px;
}

.form-btn {
  padding: 10px 15px;
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
        "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
  border-radius: 20px;
  border: 0 !important;
  outline: 0 !important;
  background: teal;
  color: white;
  cursor: pointer;
  box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
}

.form-btn:active {
  box-shadow: none;
}

.sign-up-label {
  margin: 0;
  font-size: 10px;
  color: #747474;
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
        "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
}

.sign-up-link {
  margin-left: 1px;
  font-size: 11px;
  text-decoration: underline;
  text-decoration-color: teal;
  color: teal;
  cursor: pointer;
  font-weight: 800;
}

.buttons-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  margin-top: 20px;
  gap: 15px;
}

.apple-login-button,
    .google-login-button {
  border-radius: 20px;
  box-sizing: border-box;
  padding: 10px 15px;
  box-shadow: rgba(0, 0, 0, 0.16) 0px 10px 36px 0px,
        rgba(0, 0, 0, 0.06) 0px 0px 0px 1px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
        "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
  font-size: 11px;
  gap: 5px;
}

.apple-login-button {
  background-color: #000;
  color: #fff;
  border: 2px solid #000;
}

.google-login-button {
  border: 2px solid #747474;
}

.apple-icon,
    .google-icon {
  font-size: 18px;
  margin-bottom: 1px;
}
</style>
