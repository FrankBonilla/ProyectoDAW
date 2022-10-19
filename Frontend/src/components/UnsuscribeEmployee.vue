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
              <th class="white--text">Fecha de baja</th>
              <th class="white--text">Estado Civil</th>
              <th class="white--text">Servicio Militar</th>
              <th class="white--text">Acciones</th>
            </tr>
          </thead>
          <tbody> <!--Recorremos el arreglo de autores -->
            <tr v-for="employee in employees" :key="employee.id_empleado">
              <td>{{ employee.nif }}</td>
              <td>{{ employee.nombre }}</td>
              <td>{{ employee.apellido1 }}</td>
              <td>{{ employee.apellido2 }}</td>
              <td>{{ employee.nacimiento.substring(0,10)}}</td>
              <td>{{ employee.telefono1 }}</td>
              <td>{{ employee.telefono2 }}</td>
              <td>{{ employee.email }}</td>
              <td>{{ employee.fechaBaja.substring(0,10)}}</td>
              <td>{{ employee.edoCivil }}</td>
              <td>{{ employee.serMilitar }}</td>
              <td >
                <v-row align="center" justify="space-around">
                <v-btn  fab dark x-small color="blue-grey darken-3" @click="darAlta(employee)"  title="dar alta nuevamente"> 
                  <v-icon dark small>mdi-arrow-left</v-icon>
                </v-btn> 
                <v-btn class="white--text" color="red" @click="deleteEmp(employee)" x-small title="eliminar"><b>X</b></v-btn>
                </v-row>
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
       </v-card>

       <v-dialog v-model="msgAlta" max-width="700">
              <v-alert prominent type="warning">
                <v-row align="center">
                   <v-col class="grow">
                      {{this.msgAsigned}}
                   </v-col>
              <v-col class="shrink">
              <v-btn @click="msgAlta=false">OK</v-btn>
              </v-col>
              <v-col>
                <router-link to=/empleados/suscribe-employee>
                  <v-btn>Ir a empleados de alta</v-btn>
                </router-link>
              </v-col>
            </v-row>
           </v-alert>
        </v-dialog>
    </v-main>
    
    
</template>

<script>
import {employeeService} from '../services/employeeService'
import swal from 'sweetalert2'

export default {
    name: 'UnsuscribeEmployee',
    
    data() {
        return {
            title: 'Empleados dados de baja',
            employees: [],
            msgAlta: false,
            msgAsigned: ''
        }
    },
    methods: {
        async listar(){
            this.employees = await employeeService.searchUnsuscribe()
        },
        deleteEmp(employee){

            swal.fire({
                title: 'Eliminar registro ?',
                text: `${employee.nombre} ${employee.apellido1} ${employee.apellido2} será borrado de la base de datos`,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3399ff',
                cancelButtonColor: '#ff3346',
                confirmButtonText: 'Si, continuar!',
                cancelButtonText: 'Cancelar'
              }).then((result) => {
                if (result.isConfirmed) {
                  this.confirmeDelete(employee.id_empleado) //si es confirmado llamamos al metodo para dar de baja definitiva
                  swal.fire(
                    'Eliminado!',
                    `${employee.nombre} ${employee.apellido1} ${employee.apellido2} ha sido dado borrado`,
                    'success'
                    )
                }
              })
        },
        async confirmeDelete(id){
            await employeeService.deleteEmployee(id)
            this.listar()
        },
        async darAlta(employee){

          let data = {
            id_empleado: employee.id_empleado+""
          }

          await employeeService.backEmployee(data)
          swal.fire('Alta realizada',`${employee.nombre} ${employee.apellido1} ${employee.apellido2} ha sido dado de alta nuevamente`,'success')
          this.listar()
          this.msgAlta = true
          this.msgAsigned = `${employee.nombre} ${employee.apellido1} ${employee.apellido2} ha sido dado de alta nuevamente, puede moficar sus datos en el listado empleados de alta`
        }
    },
    created(){
        this.listar()
    }
}
</script>