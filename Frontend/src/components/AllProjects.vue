<template>
<v-main class="indigo lighten-5 fill-height pl-2 pr-2 pt-3">
      <v-card  >
        <!--tabla de proyectos dados de baja -->
        <v-card-title class="justify-center blue-grey darken-3 white--text py-1">
            {{title}}
        <v-spacer></v-spacer>
        <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Buscar..."
            dark
            single-line
            hide-details
            clearable
          ></v-text-field>
      </v-card-title>
       <v-data-table
                :headers="headers"
                :items="projects"
                :search="search"
                :hide-default-footer="projects.length < 10 ? true : false"
                :footer-props="{itemsPerPageText: 'Filas por página'}"
                no-data-text="No hay datos que mostrar" 
                no-results-text="No hay concidencias"
                >
         <template v-slot:[`item.fechaInicio`]="{item}">
            {{ item.fechaInicio | formatedDate }}
         </template>

         <template v-slot:[`item.fechaFin`]="{item}">
          <td v-if="item.fechaFin">
              {{ item.fechaFin | formatedDate }}
          </td>
          <td v-else
              style="color:orange ; font-weight: bold"
          >No definida
          </td>
         </template>

        <template v-slot:[`item.lugar`]="{item}">
          <td v-if="item.lugar">
              {{ item.lugar }}
          </td>
          <td v-else
              style="color:orange ; font-weight: bold"
          >Sin asignar
          </td>
        </template>
        <template v-slot:[`item.fechaBaja`]="{item}">
            {{ item.fechaBaja | formatedDate }}
        </template>

        <template  v-slot:[`item.tiempo`]="{item}">
          <td v-if="item.fechaFin"
              :style="difference(item.fechaFin) > 0 ? 'color: green ; font-weight: bold' : 'color: red ; font-weight: bold'">
            {{ difference(item.fechaFin)+' días' }} 
          </td>
          <td v-else style="color: orange ; font-weight: bold">
              Indefinido
          </td>
        </template>

        <template v-slot:[`item.status`]="{item}">
        <td v-if="item.fechaBaja"
          > <v-icon color="red" title="Inactivo">mdi-thumb-down</v-icon>
        </td>
        <td v-else
            ><v-icon color="green" title="Activo">mdi-thumb-up</v-icon>
        </td>
      </template>
    </v-data-table>

    </v-card>
  </v-main>
    
</template>

<script>
import {projectService} from '../services/projectService'

export default {
    name: 'AllProjects',
    data(){
        return {
            title: 'Todos los Proyectos',
            projects: [],
            today: new Date(),
            //cabeceras de la tabla principal
            search: '',
            headers: [{text: 'Código',value: 'id_proyecto', width: '5%', align: 'center', filtrable: false,sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Descripción', align: 'start', filtrable: false, value: 'descripcion',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Fecha Inicio', align: 'start', value: 'fechaInicio',width:'5%', sortable: false,class:"blue-grey darken-3 ; white--text"},
                      {text: 'Fecha Fin', align: 'start', value: 'fechaFin',width:'5%',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Lugar', align: 'start', value: 'lugar', sortable: false,width:'15%', class:"blue-grey darken-3 ; white--text"},
                      {text: 'Observaciones', align: 'start', value: 'observaciones', width:'20%', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Fecha Baja', align: 'start', value: 'fechaBaja', width:'10%',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Tiempo Restante', align: 'start', value: 'tiempo', width:'5%',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Estatus', align: 'center', width:'5%', value: 'status', sortable: false, class:"blue-grey darken-3 ; white--text"},],
        }
    },
    methods: {
        async listar(){
            this.projects = await projectService.getAll()
            console.table(this.projects)
        },
        difference(fecha){
           
            let diasRestantes = ((Date.parse(fecha)- this.today)/(1000*60*60*24)).toFixed()
            return diasRestantes
        }
        
    },
    created(){
        this.listar()
    }
}
</script>