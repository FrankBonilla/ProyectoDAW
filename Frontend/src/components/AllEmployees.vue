<template>
<v-main class="indigo lighten-5 fill-height pl-2 pr-2 pt-3" >
      <v-card class="">
        <!--tabla de empleados -->
        <v-card-title class="justify-center blue-grey darken-3 white--text py-1">
            {{title}}
        </v-card-title>
      <v-simple-table class="text-left">
        <template v-slot:default>
          <thead class="blue-grey darken-3">
            <tr>
              <th class="white--text">NIF</th>
              <th class="white--text">Nombre</th>
              <th class="white--text">Primer Apeliido</th>
              <th class="white--text">Segundo apellido</th>
              <th class="white--text">Fecha nacimiento</th>
              <th class="white--text">Teléfono</th>
              <th class="white--text">Teléfono 2</th>
              <th class="white--text">Email</th>
              <th class="white--text">Estado Civil</th>
              <th class="white--text">Servicio Militar</th>
              <th class="white--text">Estatus</th>
            </tr>
          </thead>
          <tbody> <!--Recorremos el arreglo de empleados -->
            <tr v-for="employee in employees" :key="employee.id_empleado">
              <td>{{ employee.nif }}</td>
              <td>{{ employee.nombre }}</td>
              <td>{{ employee.apellido1 }}</td>
              <td>{{ employee.apellido2 }}</td>
              <td>{{ employee.nacimiento.substring(0,10)}}</td>
              <td>{{ employee.telefono1 }}</td>
              <td>{{ employee.telefono2 }}</td>
              <td>{{ employee.email }}</td>
              <td>{{ employee.edoCivil }}</td>
              <td>{{ employee.serMilitar }}</td>
              <!-- hacemos dos ternarios, uno para establecer el color de la letra -->
              <!-- y otro que devolverá el estatus del empleado segun el valor del atributo f_baja -->
              <td :style="employee.fechaBaja? 'color: red' : 'color: green'"><b>{{ employee.fechaBaja? 'INACTIVO' : 'ACTIVO'}}</b></td>
              
            </tr>
          </tbody>
        </template>
      </v-simple-table>
       </v-card>
    </v-main>
    
</template>

<script>
import {employeeService} from '../services/employeeService'

export default {
    name: 'AllEmployees',
    data(){
        return {
            title: 'Todos los Empleados',
            employees: [],
            
        }
    },
    methods: {
        async listar(){
           this.employees = await employeeService.getAll()
        }
    },
    created(){
        this.listar()
    }
}
</script>