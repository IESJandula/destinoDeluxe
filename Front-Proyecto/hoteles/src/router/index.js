import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import IniciarSesion from '../views/IniciarSesion.vue'
import Registrarse from '@/views/Registrarse.vue'
import SearchHoteles from '@/views/SearchHoteles.vue'
import Hotel from '../views/HotelView.vue'
import ReservasView from '@/views/ReservasView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/iniciar-sesion',
      name: 'IniciarSesion',
      component: IniciarSesion
    },
    {
      path: '/registrarse',
      name: 'Registrarse',
      component: Registrarse
    },
    {
      path: '/hoteles',
      name: 'Hoteles',
      component: SearchHoteles
    },
    {
      path: '/hotel/:id',
      name: 'HotelDetalle',
      component: Hotel
    },
    {
      path: '/reservas',
      name: 'Reservas',
      component: ReservasView
    }
  ]
})

export default router
