import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
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
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/empleados',
    name: 'Empleados',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: menuEmployee
    ,
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
  },{
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
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
