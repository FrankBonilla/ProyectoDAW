<template>
<v-main class="indigo lighten-5 fill-height pl-2 pr-2 pt-4" >
      <v-card class="">
        <v-card class="d-flex justify-space-around blue-grey lighten-2">
          <v-btn class="my-4" color="amber accent-4" elevation="4" title="exportar a excel" 
                 @click="confirmReport= true"
                 :disabled="!employees">Generar reporte 
            <v-icon right> mdi-inbox-arrow-down</v-icon>
          </v-btn>
        </v-card>
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
                no-data-text="No hay datos que mostrar" 
                no-results-text="No hay concidencias"
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
      <!-- Mensaje de regresar alta 2 -->
       <v-dialog v-model="msgAlta"
                  persistent
                  max-width="500">
          <v-card>
            <v-card-title class="text-h5 pb-5 white--text amber accent-4">
              <v-icon left>mdi-alert-octagon</v-icon>
                Restauración de alta.
            </v-card-title>
            <v-card-text class="pt-7"><b>{{this.msgAsigned}}</b></v-card-text>

            <v-card-actions class="justify-space-around pb-5">
              <v-btn color="blue-grey darken-3  white--text" 
                     @click="msgAlta=false"
                     >OK     
              </v-btn>
              <router-link to=/empleados/suscribe-employee>
                  <v-btn color="blue-grey darken-3  white--text">Ir a empleados de alta</v-btn>
              </router-link>
            </v-card-actions>
          </v-card>
       </v-dialog>
      
        <!-- Confirmación de generar reporte de todos los proyectos -->
        <v-dialog v-model="confirmReport"
                  persistent
                  max-width="500">
        <v-card>
        <v-card-title class="text-h5">
         Confirme operación
        </v-card-title>
        <v-card-text>Se descargará un archivo excel con los datos de esta tabla</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
              color="amber accent-4"
              text
              @click="confirmReport = false"
            >
            Cancelar
          </v-btn>
          <v-btn
              color="green darken-1"
              text
              @click="exportar"
           >
            Confirmar
          </v-btn>
        </v-card-actions>
      </v-card>
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
            //exportacion a excel
            confirmReport: false,
            //cabeceras de la tabla
            headers: [{text: 'NIF', align: 'center', filtrable: false, value: 'nif', class:"blue-grey darken-3 ; white--text"},
                      {text: 'Nombre', align: 'start', value: 'nombre',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Primer Apellido', align: 'start', value: 'apellido1',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Segundo Apellido', align: 'start', value: 'apellido2',sortable: false,class:"blue-grey darken-3 ; white--text"},
                      {text: 'Fecha Nacimiento', align: 'center', value: 'nacimiento', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Teléfono', align: 'start', value: 'telefono1', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Email', align: 'start', value: 'email',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Fecha Alta', align: 'center', value: 'fechaAlta',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Fecha Baja', align: 'center', value: 'fechaBaja', sortable: false,class:"blue-grey darken-3 ; white--text"},
                      {text: 'Estado Civil', align: 'center', value: 'edoCivil', width:'5%',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Carnet Conducir', align: 'center', value: 'serMilitar', width:'5%',sortable: false, class:"blue-grey darken-3 ; white--text"},
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
                confirmButtonColor: '#37474F',
                cancelButtonColor: '#FFAB00',
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
        },
        async exportar(){
          this.confirmReport = false
          await employeeService.exportUnsuscribeEmployeesToExcell()
        }
    },
    created(){
        this.listar()
    }
}
</script>