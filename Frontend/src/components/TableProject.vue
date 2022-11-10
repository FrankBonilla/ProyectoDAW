<template >
    <v-main class="indigo lighten-5 fill-height pl-2 pr-2 pt-3">
      <v-card  >
        <v-card class="d-flex justify-space-around blue-grey lighten-2">
          <v-btn class="my-4" color="amber accent-4" elevation="4" @click="addProject=true">Alta Proyecto
            <v-icon right>mdi-cloud-upload</v-icon>
          </v-btn>
          <v-btn class="my-4" color="amber accent-4" elevation="4" @click="asigEmp=true">Asignar Empleado
            <v-icon right>mdi-account-circle</v-icon>
          </v-btn>
        </v-card>
        <!--tabla de empleados -->
        <v-card-title class="justify-center blue-grey darken-3 white--text py-1">
            {{title}}
        </v-card-title>
      <v-simple-table class="text-left" >
        <template v-slot:default>
          
          <thead class="blue-grey darken-3">
            <tr>
              <th class="white--text">Descripción</th>
              <th class="white--text">Fecha Inicio</th>
              <th class="white--text">Fecha Fin</th>
              <th class="white--text">Lugar</th>
              <th class="white--text">Observaciones</th>
              <th class="white--text">Acciones</th>
            </tr>
          </thead>
          <tbody> <!--Recorremos el arreglo de proyectos -->
            <tr v-for="proyect in projects" :key="proyect.id_proyecto">
              <td>{{ proyect.descripcion }}</td>
              <td>{{ proyect.fechaInicio | formatedDate }}</td>
              <td v-if="proyect.fechaFin">{{ proyect.fechaFin | formatedDate }}</td>
              <td v-else style="color:orange"><b> No definida</b></td>
              <td :style="proyect.lugar? 'color: black' : 'color: orange;font-weight: bold' ">
                {{ proyect.lugar? proyect.lugar : 'Sin asignar' }}</td>
              <td>{{ proyect.observaciones }}</td>
              <td>
                <v-btn class="mx-2" fab dark x-small color="blue-grey darken-3" @click="actualizar(proyect)" title="editar"> 
                  <v-icon dark>mdi-pencil</v-icon>
                </v-btn> 
                <v-btn class="white--text" color="blue-grey darken-3" small @click="bajaProyecto(proyect)" title="dar de baja"><b>X</b></v-btn>
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
      <!-- Formulario para dar de alta proyectos-->
       <v-dialog v-model="addProject" max-width="500">
        <v-card color="dark">
          <v-card-title >Nuevo Proyecto</v-card-title>
          <v-card-text>
            <v-form ref="form">
              <v-container>
                  <v-card-text class="red--text"><b>{{errorForm? 'Debe completar todos los campos obligatorios del proyecto' : ''}}</b></v-card-text>
                  <v-row>
                    <v-col>
                      <v-text-field v-model="project.descripcion"  :rules="generalRules" label="Descripción" maxlength="125" required></v-text-field>
                    </v-col>
                  </v-row>
                <v-row>
                  <v-col>
                    <v-text-field v-model="project.lugar" label="Lugar" prepend-icon="mdi-map-marker" required maxlength="30"></v-text-field>
                  </v-col>
                </v-row> 
                <v-row>
                  <v-col> <!--Data Picker de fecha inicio -->
                    <v-menu
                      ref="menu"
                      v-model="menu"
                      :close-on-content-click="false"
                      transition="scale-transition"
                      offset-y
                      min-width="auto"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                          :rules="generalRules" 
                          :value="date | formatedDate"
                          label="Fecha de inicio"
                          prepend-icon="mdi-calendar"
                          readonly
                          v-bind="attrs"
                          v-on="on"
                        ></v-text-field>
                      </template>
                      <v-date-picker
                        v-model="date"
                        :active-picker.sync="activePicker"
                        min="1950-01-01"
                        @change="save"
                        color="blue-grey darken-3"
                        first-day-of-week="1"
                        locale="es"
                      ></v-date-picker>
                    </v-menu>
                  </v-col>
                  <v-col><!--Data Picker de fecha fin -->
                    <v-menu
                      ref="menu2"
                      v-model="menu2"
                      :close-on-content-click="false"
                      transition="scale-transition"
                      offset-y
                      min-width="auto"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                          :value="date2 | formatedDate"
                          label="Fecha fin"
                          prepend-icon="mdi-calendar"
                          readonly
                          v-bind="attrs"
                          v-on="on"
                        ></v-text-field>
                      </template>
                      <v-date-picker
                        v-model="date2"
                        :active-picker.sync="activePicker2"
                        :min="today"
                        @change="save2"
                        color="blue-grey darken-3"
                        first-day-of-week="1"
                        locale="es"
                      ></v-date-picker>
                    </v-menu>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col>
                   <v-textarea v-model="project.observaciones" label="Observaciones" required maxlength="300"></v-textarea>
                  </v-col>
                </v-row>
              </v-container>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn @click="cancelAddPro" color="amber accent-4">Cancelar</v-btn>
            <v-btn @click="addNew" type="submit" color="green acent-2">Aceptar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <!-- Formulario para asignar empleados-->
      <v-dialog v-model="asigEmp" max-width="600">
        <v-card color="dark">
          <v-card-title class="text--center">Asignación de Empleados</v-card-title>
          <v-card-text>
            <v-form ref="form3">
              <v-container>
                <v-card-text class="red--text"><b>{{errorForm? 'Es obligatorio introducir todos los datos para dar de alta un nuevo empleado' : ''}}</b></v-card-text>
                <v-row> <!--  cuando en los items trabajamos con objetos agregamos item-text para el valor a mostar e item-value para el valor del campo-->
                <!--usamos el v-on:change para  -->
                  <v-select v-model="idProject" v-on:change="presionar"  :items="projects" item-text="descripcion" item-value="id_proyecto" label="Proyectos" dense outlined ></v-select>
                </v-row>
              </v-container>
              <v-container v-model="empleados">
                <!-- Seleccion de empleados -->
           
                <v-data-table  :headers="headers" 
                               :items="empleados" 
                               item-key="idEmpleado" 
                               :footer-props="{itemsPerPageText: 'Filas por página'}"
                               :hide-default-footer="empleados.length > 10 ? false : true">
                  <!-- alerta si no hay datos -->
                  <template v-slot:no-data>
                    <v-alert :value="true" color="warning" icon="mdi-cloud-alert" shaped outlined>
                      Seleccione un proyecto
                    </v-alert>
                  </template>

                  <template v-slot:[`item.asignado`]="{item}" >
                    <v-simple-checkbox v-model="item.asignado" :ripple="false" color="success" @click="seleccionado(item)">

                    </v-simple-checkbox>
                  </template>
                </v-data-table> 

              </v-container>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn @click="asigNew" type="submit" color="green acent-2">Aceptar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <!-- Mensaje de alerta de projecto con empleados asignados-->
          <v-dialog v-model="msgAsignedEmp" max-width="700">
              <v-alert prominent type="warning">
                <v-row align="center">
                   <v-col class="grow">
                      {{this.msgAsigned}}
                   </v-col>
              <v-col class="shrink">
              <v-btn @click="msgAsignedEmp=false">OK</v-btn>
              </v-col>
            </v-row>
           </v-alert>
          </v-dialog>
      <!-- Formulario para actualizar proyecto -->
      <v-dialog v-model="update" max-width="500">
        <v-card color="dark">
          <v-card-title >Proyecto a modificar</v-card-title>
          <v-card-text>
            <v-form ref="form2">
              <v-container>
                  <v-card-text class="red--text"><b>{{errorForm? 'Debe introducir todos los campos obligatorios' : ''}}</b></v-card-text>
                  <v-row>
                    <v-col>
                      <v-text-field v-model="project.descripcion"  :rules="generalRules" label="Descripción" maxlength="125" required></v-text-field>
                    </v-col>
                  </v-row>
                <v-row>
                  <v-col>
                    <v-text-field v-model="project.lugar" label="Lugar" prepend-icon="mdi-map-marker" required maxlength="30"></v-text-field>
                  </v-col>
                </v-row> 
                <v-row>
                  <v-col> <!--Data Picker de fecha inicio -->
                    <v-menu
                      ref="menu3"
                      v-model="menu3"
                      :close-on-content-click="false"
                      transition="scale-transition"
                      offset-y
                      min-width="auto"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                          :rules="generalRules" 
                          :value="date3 | formatedDate"
                          label="Fecha de inicio"
                          prepend-icon="mdi-calendar"
                          readonly
                          v-bind="attrs"
                          v-on="on"
                        ></v-text-field>
                      </template>
                      <v-date-picker
                        v-model="date3"
                        :active-picker.sync="activePicker3"
                        min="1950-01-01"
                        @change="save3"
                        color="blue-grey darken-3"
                        first-day-of-week="1"
                        locale="es"
                      ></v-date-picker>
                    </v-menu>
                  </v-col>
                  <v-col><!--Data Picker de fecha fin -->
                    <v-menu
                      ref="menu4"
                      v-model="menu4"
                      :close-on-content-click="false"
                      transition="scale-transition"
                      offset-y
                      min-width="auto"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                          :value="date4 | formatedDate"
                          label="Fecha fin"
                          prepend-icon="mdi-calendar"
                          readonly
                          v-bind="attrs"
                          v-on="on"
                        ></v-text-field>
                      </template>
                      <v-date-picker
                        v-model="date4"
                        :active-picker.sync="activePicker4"
                        :min="today"
                        @change="save4"
                        color="blue-grey darken-3"
                        first-day-of-week="1"
                        locale="es"
                      ></v-date-picker>
                    </v-menu>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col>
                   <v-textarea v-model="project.observaciones" label="Observaciones" required maxlength="300"></v-textarea>
                  </v-col>
                </v-row>
              </v-container>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn @click="cancelUpdate" color="amber accent-4">Cancelar</v-btn>
            <v-btn @click="updateProject" type="submit" color="green acent-2">Modificar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      </v-card>
    </v-main>
</template>

<script>
//importamos el servicio de proyectos donde estsn las consultas
import {projectService} from '../services/projectService'
import {employeeService} from '../services/employeeService'
import swal from 'sweetalert2'


export default {
    name: 'TableProject',
    data() {
        return {
            title: 'Proyectos',
            today: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
            projects: [],
            project: {
                id_proyecto: '',
                descripcion: '',
                fechaInicio: '',
                fechaFin: '',
                fechaBaja: '',
                lugar: '',
                observaciones: ''
            },
            empleados: [],
            empleado: '',
            addProject: false,
            update: false,
            errorForm: false,
            asigEmp: false,
            generalRules: [v => !!v || 'este campo es obligatorio'],
            //propiedades de los calendarios
            activePicker: null,
            date: null,
            menu: false,
            activePicker2: null,
            date2: null,
            menu2: false,
            //propiedades de los calenadrios de actualizar proyecto
            activePicker3: null,
            date3: null,
            menu3: false,
            activePicker4: null,
            date4: null,
            menu4: false,
            //propiedades checkbox
            singleSelect: false,
            headers: [{text: 'Nombre', align: 'start', sortable: false,value: 'nombre'},
                      {text: 'Primer apellido', align: 'start', sortable: false,value: 'apellido1'},
                      {text: 'Segundo apellido', align: 'start', sortable: false,value: 'apellido2'},
                      { text: 'Asignado', value: 'asignado' }],
            idProject: '',
            selected: [],
             //datos de verificación de projectos asignados a empleado
            msgAsigned: '',
            msgAsignedEmp: false
      

        }
    },
    methods: { //METODOS DEL PROYECTO
        async listar(){
            this.projects = await projectService.getProjectsAct()
            
        },
       async bajaProyecto(proyect){

         let lista = await projectService.searchEmpInProjects(proyect.id_proyecto)
          console.log(lista)
          if(lista && lista.length > 0){
            //asignamos la lista de projectos 
            this.msgAsigned = "No se puede dar de baja al proyecto: "+proyect.descripcion.toUpperCase()+" porque está asignado al menos un recurso";
            //activamos el msj que mostrará que el empleado tiene projectos asignados
            this.msgAsignedEmp = true
            //console.log(lista.length)
          }else{
            //si no tiene projectos asignados le damos de baja
            //console.log("Este empleado no tiene proyectos asignados")
            swal.fire({
                title: 'Realizar baja del proyecto ?',
                text: `${proyect.descripcion.toUpperCase()}  será dado de baja`,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#37474F',
                cancelButtonColor: '#FFAB00',
                confirmButtonText: 'Si, continuar!',
                cancelButtonText: 'Cancelar'
              }).then((result) => {
                if (result.isConfirmed) {
                  this.darBaja(proyect.id_proyecto) //si es confirmado llamamos al metodo para dar de baja definitiva
                  swal.fire(
                    'Baja realizada!',
                    `${proyect.descripcion.toUpperCase()} ha sido dado de baja`,
                    'success'
                    )
                }
              })
            
          }
        },
        async darBaja(id){
            await projectService.terminateProject(id)
            this.listar()
        },
       async addNew(){
          if(this.$refs.form.validate()){
            
            let data = {
                descripcion: this.project.descripcion,
                fechaInicio: this.date,
                fechaFin: this.date2,
                fechaBaja: this.project.fechaBaja,
                lugar: this.project.lugar,
                observaciones: this.project.observaciones
            }
              //console.log(data)
              //hacemos la insercion del nuevo proyecto
              await projectService.saveProyect(data)
              //console.log(`se ha guardado el proyecto ${data.descripcion}`)
              swal.fire('Alta realizada',`${data.descripcion.toUpperCase()} ha sido dado de alta`,'success')
              this.$refs.form.reset()
              this.listar()
              this.errorForm = false
              this.addProject = false
          }else{

              this.errorForm = true
          }

          
        },
        cancelAddPro(){
            this.addProject = false
            this.$refs.form.reset()
            this.errorForm = false

        },//METODOS DE LA ASIGNACION
        asigNew(){
            //al terminar con la tabla de asignacion reseteamos los siguientes valores 
            this.empleados = []
            this.idProject = ''
            this.asigEmp = false

          
        },//METODO DE LOS CALENDARIOS
        save (date) {
        this.$refs.menu.save(date)
        },
        save2 (date2) {
        this.$refs.menu2.save(date2)
        },//metodos del calendario actualizar
        save3 (date3){
          this.$refs.menu3.save(date3)
        },save4 (date4){
            this.$refs.menu4.save(date4)
        },
        async presionar(){
          console.log(`haz seleccionado el proyecto ${this.idProject}`)
          //pasamos le id del proyecto para hacer la consulta 
          //hacemos la conulta de los empleados
          let datos  = await employeeService.getStatusEmp(this.idProject)
          this.empleados= datos
          
        },
        seleccionado(employee){
          console.log(`haz seleccionado un empleado ${employee.idEmpleado}`)
          //evaluamos en la condicion si no esta agregado lo hacemos y de lo contrario lo removemos
          if(employee.asignado == false){
            projectService.removeEmployee(this.idProject,employee.idEmpleado)
            console.log(`Haz eliminado al empleado ${employee.nombre}`)
            
          }else{
            projectService.asignarEmpleado(this.idProject,employee.idEmpleado)
            console.log(`Haz agregado al empleado ${employee.nombre}`)
            
          }
        },
        actualizar(proyecto){

          this.update = true
          
                this.project.id_proyecto = proyecto.id_proyecto,
                this.project.descripcion = proyecto.descripcion
                this.project.lugar = proyecto.lugar,
                this.project.observaciones = proyecto.observaciones
                //establecemos las fechas por default del proyecto
                this.date = proyecto.fechaInicio.substring(0,10)
                this.date2 = proyecto.fechaFin? proyecto.fechaFin.substring(0,10) : null
                //las fechas por default que apareceran en los calendarios serán las fechas anteriormente asignadas
                //y trabajaremos sobre las nuevas
                this.date4 = this.date2
                this.date3 = this.date

        },
       async updateProject(){

         if(this.$refs.form2.validate()){

            let datos = {
            id_proyecto: this.project.id_proyecto,
            descripcion: this.project.descripcion,
            fechaInicio: this.date3,
            fechaFin: this.date4,
            fechaBaja: null,
            lugar: this.project.lugar,
            observaciones: this.project.observaciones
           }
            await projectService.updateProject(datos)
            swal.fire('Modificación realizada','Los datos del proyecto han sido modificados correctamente','success')
            this.$refs.form2.reset()
            this.date = null
            this.date2 = null
            this.date4 = null
            this.date3 = null
            this.errorForm = false
            this.listar()
            this.update = false

         }else{
           
           this.errorForm = true
         } 
       },
       cancelUpdate(){
         this.date = null
         this.date2 = null
         this.date4 = null
         this.date3 = null
         this.errorForm = false
         this.$refs.form2.reset()
         this.update = false
       }
    },
    created(){
      //al crearse el componente llamamos al metodo listar para que nos traigo todos los registros
        this.listar()
    }
}
</script>