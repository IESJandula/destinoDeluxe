// authStore.js

import { defineStore } from 'pinia';


export const useAuthStore = defineStore({
  id: 'auth',
  state: () => ({
    isLoggedIn: false,
    user: null,
    error: null,
  }),
  actions: {
    async login(email, password) {
        try {
          const response = await fetch(`http://localhost:8080/api/clientes/iniciar-sesion`, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, contrasena: password }),
          });
      
          if (response.ok) {
              const responseData = await response.json(); // Convertir la respuesta a JSON
              const { mensaje, idUsuario } = responseData; // Extraer el mensaje y el ID del cliente
  
              console.log(mensaje); // Imprimir el mensaje en la consola
              console.log(idUsuario); // Imprimir el ID del cliente en la consola
  
              // Actualizar el estado del store de autenticación con el ID del cliente
              this.isLoggedIn = true;
              this.user = { id: idUsuario };
              this.user.email = email;
              this.error = null;
          } else {
              // Manejar errores de inicio de sesión
              this.isLoggedIn = false;
              this.user = null;
              this.user.email = null;
              this.error = 'Credenciales inválidas';
          }
        } catch (error) {
          console.error('Error al iniciar sesión:', error);
          this.isLoggedIn = false;
          this.user = null;
          this.error = 'Error de conexión';
        }
      },
    logout() {
      // Lógica para cerrar sesión
      this.isLoggedIn = false;
      this.user = null;
      this.user.email = null;
      this.error = null;
      // Podrías eliminar el token JWT u otros datos de sesión de localStorage aquí
    },
  },
});
