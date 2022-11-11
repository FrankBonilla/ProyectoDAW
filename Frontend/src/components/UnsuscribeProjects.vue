<template>
    <v-main class="indigo lighten-5 fill-height pl-2 pr-2 pt-3">
      <v-card >
        <!--tabla de proyectos dados de baja -->
        <v-card-title class="justify-center blue-grey darken-3 white--text py-1">
            {{title}}
      <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Buscar por nombre"
          dark
          single-line
          hide-details
        ></v-text-field>
      </v-card-title>
      <v-data-table
                :headers="headers"
                :items="projects"
                :search="search"
                :hide-default-footer="projects.length < 10 ? true : false"
                :footer-props="{itemsPerPageText: 'Filas por página'}"
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
            headers: [{text: 'Descripción', align: 'start', filtrable: false, value: 'descripcion', class:"blue-grey darken-3 ; white--text"},
                      {text: 'Fecha Inicio', align: 'start', value: 'fechaInicio', class:"blue-grey darken-3 ; white--text"},
                      {text: 'Fecha Fin', align: 'start', value: 'fechaFin', class:"blue-grey darken-3 ; white--text"},
                      {text: 'Lugar', align: 'start', value: 'lugar', class:"blue-grey darken-3 ; white--text"},
                      {text: 'Observaciones', align: 'start', value: 'observaciones', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Fecha Baja', align: 'start', value: 'fechaBaja', class:"blue-grey darken-3 ; white--text"},
                      {text: 'Acciones', align: 'center', value: 'actions', sortable: false, class:"blue-grey darken-3 ; white--text"},],
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
                confirmButtonColor: '#3399ff',
                cancelButtonColor: '#ff3346',
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
        }
    },
    created(){
        this.listar()
    }
    
}
</script>