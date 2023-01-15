<template>
    <v-main class="indigo lighten-5 fill-height pl-2 pr-2 pt-3">
      <v-card >
        
        <v-card class="d-flex justify-space-around blue-grey lighten-2">
          <v-btn class="my-4" color="amber accent-4" elevation="4" title="exportar a excel" 
                @click="confirmReport= true"
                :disabled="!projects"
                >Generar reporte 
            <v-icon right> mdi-inbox-arrow-down</v-icon>
          </v-btn>
        </v-card>
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
                no-results-text="No hay concidencias">
                
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

         <template v-slot:[`item.actions`]="{ item }">
            <v-icon
              medium
              @click="borrar(item)"
              title="eliminar"
              color="red"
              >
              mdi-delete
            </v-icon>
        </template>
      </v-data-table>
    </v-card>
     <!-- Confirmación de generar reporte de todos los proyectos -->
        <v-dialog v-model="confirmReport"
                  persistent
                  max-width="300">
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

import {projectService} from '../services/projectService'
import swal from 'sweetalert2'

export default {
    name: 'UnsuscribeProjects',
    
    data() {
        return {
            title: 'Proyectos dados de baja',
            projects: [],
             //cabeceras de la tabla principal
            search: '',
            headers: [{text: 'Código',value: 'id_proyecto', width: '5%', align: 'center', filtrable: false,sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Descripción', align: 'start', filtrable: false, sortable: false, value: 'descripcion', class:"blue-grey darken-3 ; white--text"},
                      {text: 'Fecha Inicio', align: 'start', value: 'fechaInicio',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Fecha Fin', align: 'start', value: 'fechaFin', sortable: false,class:"blue-grey darken-3 ; white--text"},
                      {text: 'Lugar', align: 'start', value: 'lugar',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Observaciones', align: 'start', value: 'observaciones', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Fecha Baja', align: 'start', value: 'fechaBaja',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Acciones', align: 'center', value: 'actions', sortable: false, class:"blue-grey darken-3 ; white--text"},],
            confirmReport: false
        }
        
    },
    methods: {
        async listar(){
            this.projects = await projectService.unsuscribeProyect()
        },
        async borrar(proyect){
            swal.fire({
                title: 'Eliminar de forma definitiva el proyecto?',
                text: `${proyect.descripcion} será eliminado`,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#37474F',
                cancelButtonColor: '#FFAB00',
                confirmButtonText: 'Si, continuar!',
                cancelButtonText: 'Cancelar'
              }).then((result) => {
                if (result.isConfirmed) {
                  this.borrarProyecto(proyect.id_proyecto) //si es confirmado llamamos al metodo para dar de baja definitiva
                  swal.fire(
                    'Acción realizada!',
                    `${proyect.descripcion} ha sido eliminado`,
                    'success'
                    )
                }
              })

          this.listar()

        },
        async borrarProyecto(id){

          await projectService.deleteProject(id)
          this.listar()
        },
        async exportar(){
          this.confirmReport = false
          await projectService.exportUnsuscribeProjectsToExcell()
          console.log('Exportando')
        }
    },
    created(){
        this.listar()
    }
    
}
</script>