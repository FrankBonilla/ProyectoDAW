import Vue from 'vue'
import VueRouter from 'vue-router'
//import App from '../App.vue'
import Dashboard from '../components/Dashboard.vue'
import Fondo from '../views/Home.vue'
import Login from '../components/Login'
import TableEmployee from '../components/TableEmployee'
import menuEmployee from '../components/menuEmployee'
import UnsuscribeEmployee from '../components/UnsuscribeEmployee'
import AllEmployees from '../components/AllEmployees'
import MenuProjects from '../components/MenuProjects'
import TableProject from '../components/TableProject'
import UnsuscribeProjects from '../components/UnsuscribeProjects'
import AllProjects from '../components/AllProjects'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login 
  },
  {
    path: '/',
    name: 'Dashboard',
    component: Dashboard,

    children: [{
        path: '/inicio',
        name: 'inicio',
        component: Fondo
    },
    {
      path: '/empleados',
      name: 'Empleados',
      component: menuEmployee,

      children: [{
          path:'suscribe-employee',
          component: TableEmployee
        }, {
          path: 'unsuscribe-employee',
          component: UnsuscribeEmployee
        }, {
          path: 'all-employees',
          component: AllEmployees
        }]
    },
    {
      path: '/proyectos',
      name: 'Proyectos',
      component: MenuProjects
      ,
        children: [{
            path: 'alta',
            component: TableProject
        },{
            path: 'baja',
            component: UnsuscribeProjects
        },{
            path: 'todos',
            component: AllProjects
          }]
    }]
  },
 
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


export default router
