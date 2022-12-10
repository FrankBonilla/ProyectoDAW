<template>
<v-main class="indigo lighten-5 fill-height pl-2 pr-2 pt-3" >
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

      <template v-slot:[`item.status`]="{item}">
        <td v-if="item.fechaBaja"
          ><v-icon color="red" title="Inactivo">mdi-thumb-down</v-icon>
        </td>
        <td v-else
            style="color:green ; font-weight: bold"
            ><v-icon color="green" title="Activo">mdi-thumb-up</v-icon>
        </td>
      </template>

      </v-data-table>
     
    </v-card>
    </v-main>
    
</template>

<script>
import {employeeService} from '../services/employeeService'

export default {
    name: 'AllEmployees',
    data(){
        return {
            title: 'Todos los empleados',
            employees: [],
            search: '',
            //cabeceras de la tabla
            headers: [{text: 'NIF', align: 'center', filtrable: false, value: 'nif', class:"blue-grey darken-3 ; white--text"},
                      {text: 'Nombre', align: 'start', value: 'nombre',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Primer Apellido', align: 'start', value: 'apellido1',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Segundo Apellido', align: 'start', value: 'apellido2',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Teléfono', align: 'start', value: 'telefono1', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Email', align: 'start', value: 'email', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Estado Civil', align: 'center', value: 'edoCivil', width:'5%',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Carnet Conducir', align: 'center', value: 'serMilitar',width:'5%', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Fecha Alta', align: 'center', value: 'fechaAlta',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Fecha Baja', align: 'center', value: 'fechaBaja',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Estatus', align: 'center', value: 'status',sortable: false, class:"blue-grey darken-3 ; white--text"},]
            
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