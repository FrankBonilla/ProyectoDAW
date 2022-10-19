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
              <th class="white--text">Timpo Restante</th>
              <th class="white--text">Estatus</th>
            </tr>
          </thead>
          <tbody> <!--Recorremos el arreglo de proyectos -->
            <tr v-for="proyect in projects" :key="proyect.id_proyecto">
              <td>{{ proyect.descripcion }}</td>
              <td>{{ proyect.f_inicio.substring(0,10) }}</td>
              <td>{{ proyect.f_fin? proyect.f_fin.substring(0,10) : 'Sin definir' }}</td>
              <td>{{ proyect.lugar? proyect.lugar : 'Sin asignar' }}</td>
              <td>{{ proyect.observaciones }}</td>
              <!--Si hay fecha fin del proyecto entonces la calculamos ocn la fecha de inicio -->
              <td>{{proyect.f_fin? ((Date.parse(proyect.f_fin)- today)/(1000*60*60*24)).toFixed()+' días' : 'Indefinido'}}</td>
              <td :style="proyect.f_baja? 'color: red' : 'color: green'"><b>{{ proyect.f_baja? 'INACTIVO' : 'ACTIVO'}}</b></td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
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
            today: new Date()
        }
    },
    methods: {
        async listar(){
            this.projects = await projectService.getAll()
            console.table(this.projects)
        },
        difference(fecha1, fecha2){
            console.log(fecha1,fecha2)
        }
        
    },
    created(){
        this.listar()
    }
}
</script>