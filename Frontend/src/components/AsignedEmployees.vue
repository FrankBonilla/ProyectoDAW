<template>
<v-card>
<v-card-title class="amber accent-4 pb-7">{{ project.id_proyecto }} - {{project.descripcion}}
    
</v-card-title>
<v-card-subtitle class="pt-4 white--text text-start blue-grey darken-3">
    <v-row>
      <v-col><div class="mt-6"><b>Empleados asignados</b></div></v-col>
      <v-col>
    <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Buscar por nombre, apellidos, email o NIF"
          single-line
          hide-details
          clearable
          dark
        ></v-text-field>
      </v-col>
    </v-row>
</v-card-subtitle>
 <v-data-table
            :headers="headers"
            :items="employees"
            :search="search"
            :hide-default-footer="employees.length < 10 ? true : false"
            :footer-props="{itemsPerPageText: 'Filas por página'}"
            no-data-text="No hay datos que mostrar" 
            no-results-text="No hay concidencias">

  </v-data-table>
</v-card>
</template>

<script>

export default {
  name: 'AsignedEmployees',
    props: {
        project: Object,
        employees: Array,
    },

  data(){
    return {
    search: '',
    //cabeceras de la tabla
    headers: [{text: 'NIF', align: 'center', value: 'nif', filtrable: false, sortable:false, class:"blue-grey darken-3 ; white--text"},
              {text: 'Nombre', align: 'start', value: 'nombre', sortable: false, class:"blue-grey darken-3 ; white--text"},
              {text: 'Primer Apellido', align: 'start', value: 'apellido1', sortable: false, class:"blue-grey darken-3 ; white--text"},
              {text: 'Segundo Apellido', align: 'start', value: 'apellido2', sortable: false, class:"blue-grey darken-3 ; white--text"},
              {text: 'Teléfono', align: 'start', value: 'telefono1', sortable: false, class:"blue-grey darken-3 ; white--text"},
              {text: 'Email', align: 'start', value: 'email',sortable: false, class:"blue-grey darken-3 ; white--text"},]
    }
  }  
}
</script>