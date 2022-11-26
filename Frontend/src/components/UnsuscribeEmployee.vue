<template>
<v-main class="indigo lighten-5 fill-height pl-2 pr-2 pt-4" >
      <v-card class="">
        <!--tabla de empleados -->
        <v-card-title class="justify-center blue-grey darken-3 white--text py-1">
            {{title}}
       <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Buscar por nombre, apellidos, email o NIF"
          dark
          single-line
          hide-details
          clearable
        ></v-text-field>
      </v-card-title>
      <v-data-table
                :headers="headers"
                :items="employees"
                :search="search"
                :hide-default-footer="employees.length < 10 ? true : false"
                :footer-props="{itemsPerPageText: 'Filas por página'}"
                >

        <template v-slot:[`item.nacimiento`]="{item}">
          {{ item.nacimiento | formatedDate }}
        </template>

        <template v-slot:[`item.fechaAlta`]="{item}">
          {{ item.fechaAlta | formatedDate }}
        </template>

        <template v-slot:[`item.fechaBaja`]="{item}">
          {{ item.fechaBaja | formatedDate }}
        </template>

        <template v-slot:[`item.actions`]="{ item }">
        <v-icon
          medium
          class="mr-2"
          @click="darAlta(item)"  
          title="dar alta nuevamente"
          color="amber accent-4"
          >
          mdi-arrow-left
        </v-icon>
        <v-icon
          medium
          @click="deleteEmp(item)" 
          title="eliminar"
          color="red"
          >
          mdi-delete
        </v-icon>
    </template>
  </v-data-table>
     
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
            msgAsigned: '',
            search: '',
            //cabeceras de la tabla
            headers: [{text: 'NIF', align: 'center', filtrable: false, value: 'nif', class:"blue-grey darken-3 ; white--text"},
                      {text: 'Nombre', align: 'start', value: 'nombre',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Primer Apellido', align: 'start', value: 'apellido1',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Segundo Apellido', align: 'start', value: 'apellido2',sortable: false,class:"blue-grey darken-3 ; white--text"},
                      {text: 'Fecha Nacimiento', align: 'center', value: 'nacimiento', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Teléfono', align: 'start', value: 'telefono1', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Teléfono 2', align: 'start', value: 'telefono2', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Email', align: 'start', value: 'email',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Fecha Alta', align: 'center', value: 'fechaAlta',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Fecha Baja', align: 'center', value: 'fechaBaja', sortable: false,class:"blue-grey darken-3 ; white--text"},
                      {text: 'Estado Civil', align: 'center', value: 'edoCivil', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Carnet Conducir', align: 'center', value: 'serMilitar', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Acciones', align: 'center', value: 'actions', sortable: false, class:"blue-grey darken-3 ; white--text"},]
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
                  this.confirmeDelete(employee.idEmpleado) //si es confirmado llamamos al metodo para dar de baja definitiva
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

          let idEmpleado = employee.idEmpleado
         
          await employeeService.backEmployee(idEmpleado)
          swal.fire('Alta realizada',`${employee.nombre} ${employee.apellido1} ha sido dado de alta nuevamente`,'success')
          this.listar()
          this.msgAlta = true
          this.msgAsigned = `${employee.nombre} ${employee.apellido1} ha sido dado de alta nuevamente, puede modificar sus datos en el listado empleados de alta`
        }
    },
    created(){
        this.listar()
    }
}
</script>