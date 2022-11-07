<template>
    <v-main class="indigo lighten-5 fill-height pl-2 pr-2 pt-3">
      <v-card  >
        <!--tabla de proyectos dados de baja -->
        <v-card-title class="justify-center blue-grey darken-3 white--text py-1">
            {{title}}
        </v-card-title>
      <v-simple-table class="text-left" >
        <template v-slot:default>
          
          <thead class="blue-grey darken-3">
            <tr>
              <th class="white--text">Descripción</th>
              <th class="white--text">Fecha de inicio</th>
              <th class="white--text">Fecha fin</th>
              <th class="white--text">Lugar</th>
              <th class="white--text">Observaciones</th>
              <th class="white--text">Fecha baja</th>
              <th class="white--text">Acciones</th>
            </tr>
          </thead>
          <tbody> <!--Recorremos el arreglo de proyectos -->
            <tr v-for="proyect in projects" :key="proyect.id_proyecto">
              <td>{{ proyect.descripcion }}</td>
              <td>{{ proyect.fechaInicio | formatedDate }}</td>
              <td v-if="proyect.fechaFin">{{ proyect.fechaFin | formatedDate }}</td>
              <td v-else style="color:orange"> <b>No definida</b> </td>
              <td :style="proyect.lugar? 'color: black' : 'color: orange;font-weight: bold' ">
                {{ proyect.lugar? proyect.lugar : 'Sin asignar' }}</td>
              <td>{{ proyect.observaciones }}</td>
              <td>{{ proyect.fechaBaja | formatedDate }}</td>
              <td>
                <v-btn class="white--text" color="red" small @click="borrar(proyect)" title="eliminar"><b>X</b></v-btn>
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
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
            projects: []
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