<template >

    <v-main class="indigo lighten-5 fill-height pl-2 pr-2 pt-3" >
      <v-card class="">
      
        <v-card class="d-flex justify-space-around blue-grey lighten-2">
          <v-btn class="my-4" color="amber accent-4" elevation="4" @click="addEmployee=true">Alta Empleado
            <v-icon right>mdi-cloud-upload</v-icon>
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
                >

      <template v-slot:[`item.nacimiento`]="{item}">
        {{ item.nacimiento | formatedDate }}
      </template>

      <template v-slot:[`item.fechaAlta`]="{item}">
        {{ item.fechaAlta | formatedDate }}
      </template>

      <template v-slot:[`item.actions`]="{ item }">
        <v-icon
          medium
          class="mr-2"
          @click="actualizar(item)" 
          title="editar"
          color="amber accent-4"
          >
          mdi-pencil
        </v-icon>
        <v-icon
          medium
          @click="bajaEmpleado(item)" 
          title="dar de baja"
          color="red"
          >
          mdi-delete
        </v-icon>
    </template>

  </v-data-table>
      <!--Formulario para agregar empleado -->
      <v-dialog v-model="addEmployee" max-width="500" persistent>
        <v-card color="dark">
          <v-card-title class="white--text blue-grey darken-3">Nuevo Empleado</v-card-title>
          <v-card-text>
            <v-form ref="form">
              <v-container>
                  <v-card-text class="red--text"><b>{{errorForm? 'Es obligatorio introducir todos los datos para dar de alta un nuevo empleado' : ''}}</b></v-card-text>
                  <v-row>
                    <v-col>
                      <v-text-field v-model="employee.nif"  :rules="nifRules" label="NIF" maxlength="9" required ></v-text-field>
                    </v-col>
                    <v-col>
                      <v-text-field v-model="employee.nombre" :rules="generalRules" label="Nombre" required  maxlength="30"></v-text-field>
                    </v-col>
                </v-row>
                <v-row>
                  <v-col>
                    <v-text-field v-model="employee.apellido1" :rules="generalRules" label="Primer apellido" required maxlength="40"></v-text-field>
                  </v-col>
                  <v-col>
                    <v-text-field v-model="employee.apellido2"  label="Segundo apellido" required maxlength="40"></v-text-field>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col>
                    <v-text-field v-model="employee.telefono1" :rules="phoneRules"  label="Teléfono" required maxlength="9"></v-text-field>
                  </v-col>
                  <v-col>
                   <v-text-field v-model="employee.telefono2"  label="Teléfono 2" required maxlength="9"></v-text-field>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col>
                    <v-text-field v-model="employee.email" :rules="emailRules" label="Email" required maxlength="40"></v-text-field>
                  </v-col>
                </v-row>
                 <v-row>
                  <v-col>
                  <v-menu
                    ref="menu"
                    v-model="menu"
                    :close-on-content-click="false"
                    transition="fab-transition"
                    offset-y
                    min-width="auto"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field
                        :rules="generalRules" 
                        :value="date | formatedDate"
                        label="Fecha nacimiento"
                        prepend-icon="mdi-calendar"
                        readonly
                        v-bind="attrs"
                        v-on="on"
                        clearable
                      ></v-text-field>
                    </template>
                    <v-date-picker
                      v-model="date"
                      :active-picker.sync="activePicker"
                      :max="today"
                      min="1950-01-01"
                      @change="save"
                      color="blue-grey darken-3"
                      first-day-of-week="1"
                      locale="es"
                    ></v-date-picker>
                  </v-menu>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col>
                    <v-select v-model="employee.edoCivil" :rules="generalRules" :items="civil" item-text="state" label="Estado civil" dense outlined required></v-select>
                  </v-col>
                  <v-col>
                    <v-select v-model="employee.serMilitar" :rules="generalRules"  :items="canertConducir" item-text="state"  label="Carnet de Conducir" dense outlined required></v-select>
                  </v-col>
                </v-row>
              </v-container>
            </v-form>
            <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn @click="cancelar" color="amber accent-4">Cancelar</v-btn>
            <v-btn @click="addNew" type="submit" color="green acent-2">Aceptar</v-btn>
          </v-card-actions>
          </v-card-text>
          
        </v-card>
      </v-dialog>
       <!-- Mensaje de alerta de empelado asignado a projectos-->
          <v-dialog v-model="msgAsignedProject" max-width="700">
              <v-alert prominent type="warning">
                <v-row align="center">
                   <v-col class="grow">
                      {{this.msgAsigned}}
                   </v-col>
                   <v-col class="shrink">
                      <v-btn @click="msgAsignedProject=false">OK</v-btn>
                   </v-col>
                </v-row>
              </v-alert>
          </v-dialog>
        <!-- Formulario para actualizar empleado -->
      <v-dialog v-model="update" max-width="500" persistent>
        <v-card color="dark">
          <v-card-title class="white--text blue-grey darken-3">Modificación de empleado</v-card-title>
          <v-card-text>
            <v-form ref="form2">
              <v-container>
                  <v-card-text class="red--text"><b>{{errorForm? 'No puede haber campos vacios' : ''}}</b></v-card-text>
                  <v-row>
                    <v-col>
                      <v-text-field v-model="employee.nif"  :rules="nifRules" label="NIF" maxlength="9" required ></v-text-field>
                    </v-col>
                    <v-col>
                      <v-text-field v-model="employee.nombre" :rules="generalRules" label="Nombre" required  maxlength="30"></v-text-field>
                    </v-col>
                </v-row>
                <v-row>
                  <v-col>
                    <v-text-field v-model="employee.apellido1" :rules="generalRules" label="Primer apellido" required maxlength="40"></v-text-field>
                  </v-col>
                  <v-col>
                    <v-text-field v-model="employee.apellido2"  label="Segundo apellido" required maxlength="40"></v-text-field>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col>
                    <v-text-field v-model="employee.telefono1" :rules="phoneRules"  label="Teléfono" required maxlength="9"></v-text-field>
                  </v-col>
                  <v-col>
                   <v-text-field v-model="employee.telefono2"  label="Teléfono 2" required maxlength="9"></v-text-field>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col>
                    <v-text-field v-model="employee.email" :rules="emailRules" label="Email" required maxlength="40"></v-text-field>
                  </v-col>
                </v-row>
                 <v-row>
                  <v-col>
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
                        :rules="generalRules"
                        :value="date2 | formatedDate"
                        label="Fecha nacimiento"
                        prepend-icon="mdi-calendar"
                        readonly
                        v-bind="attrs"
                        v-on="on"
                        clearable
                      ></v-text-field>
                    </template>
                    <v-date-picker
                      v-model="date2"
                      :active-picker.sync="activePicker2"
                      :max="today"
                      min="1950-01-01"
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
                    <v-select v-model="employee.edoCivil" :rules="generalRules"  :items="civil" item-text="state" item-value="value" label="Estado civil" dense outlined required></v-select>
                  </v-col>
                  <v-col>
                    <v-select v-model="employee.serMilitar" :rules="generalRules"  :items="canertConducir" item-text="state" item-value="value" label="Carnet de Conducir" dense outlined required></v-select>
                  </v-col>
                </v-row>
              </v-container>
            </v-form>
            <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn @click="cancelUpdate" color="amber accent-4">Cancelar</v-btn>
            <v-btn @click="updateEmployee(employee.idEmpleado)" type="submit" color="green acent-2">Modificar</v-btn>
          </v-card-actions>
          </v-card-text>
          
        </v-card>
      </v-dialog>

      </v-card>
    </v-main>
</template>

<script>
import {employeeService} from '../services/employeeService'
import swal from 'sweetalert2'


export default {
    name: 'TableEmployee',
    data(){
        return {
            title: 'Empleados activos en la empresa',
            today: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
            employees: [],
            search: '',
            employee: {
                idEmpleado: '',
                nif: '',
                nombre: '',
                apellido1: '',
                apellido2: '',
                nacimiento: '',
                telefono1: '',
                telefono2: '',
                email: '',
                fechaAlta: '',
                edoCivil: '',
                serMilitar: ''

            },
            addEmployee: false,
            civil: [ {state: 'Soltero/a', value:'S'},{state: 'Casado/a', value: 'C'}],
            canertConducir: [{state: 'Si', value:'S'},{state: 'No', value: 'N'}],
            update: false,
            //datos del calendario agregar
            activePicker: null,
            date: null,
            menu: false,
            //datos el calendario editar
            activePicker2: null,
            date2: null,
            menu2: false,
            //datos de verificación de campos del formulario
            errorForm: false,
            emailRules: [ v => !!v || 'el campo email es obligatorio',
                          v => /.+@.+\..+/.test(v) || 'debe ser un email válido'],
            nifRules: [ v =>  /^\d{8}[a-zA-Z]$/.test(v) || 'debe ser un nif válido',
                        v => !!v || 'debe introducir un nif'],
            phoneRules: [ v => !!v || 'este campo es obligatorio',
                          v => (v && v.length >= 9)|| 'al menos 9 dígitos'],
            generalRules: [v => !!v || 'este campo es obligatorio'], 
            //datos de verificación de projectos asignados a empleado
            msgAsigned: '',
            msgAsignedProject: false,
            //cabeceras de la tabla
            headers: [{text: 'NIF', align: 'center', value: 'nif', filtrable: false, sortable:false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Nombre', align: 'start', value: 'nombre', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Primer Apellido', align: 'start', value: 'apellido1', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Segundo Apellido', align: 'start', value: 'apellido2', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Fecha Nacimiento', align: 'center', value: 'nacimiento', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Teléfono', align: 'start', value: 'telefono1', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Teléfono 2', align: 'start', value: 'telefono2', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Email', align: 'start', value: 'email',sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Fecha Alta', align: 'center', value: 'fechaAlta', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Estado Civil', align: 'center', value: 'edoCivil', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Carnet Conducir', align: 'center', value: 'serMilitar', sortable: false, class:"blue-grey darken-3 ; white--text"},
                      {text: 'Acciones', align: 'center', value: 'actions', sortable: false, class:"blue-grey darken-3 ; white--text"},]
        }
    },
    watch: {
      menu(val){
        val && setTimeout(() => (this.activePicker = 'YEAR'))
      },
      menu2(val){
        val && setTimeout(() => (this.activePicker2 = 'YEAR'))
      }
    },
    methods: {

       async listar(){
         
          this.employees = await employeeService.getEmployeesAct()
            
        },
      async  addNew(){
        //validamos si el formulario cumple con los campos requeridos
         if(this.$refs.form.validate()){
           //si los cumple seguimos con el alta del nuevo empleado
            let data = {
              nif: this.employee.nif.toUpperCase(),
              nombre: this.employee.nombre,
              apellido1: this.employee.apellido1,
              apellido2: this.employee.apellido2,
              nacimiento: this.date,
              telefono1: this.employee.telefono1,
              telefono2: this.employee.telefono2,
              email: this.employee.email,
              fechaAlta: new Date().toISOString(),
              edoCivil: this.employee.edoCivil,
              serMilitar: this.employee.serMilitar
            }//hacemos la inserción del nuevo empleado en la base de datos
            
              await employeeService.addEmployee(data)
              swal.fire('Alta realizada',`${this.employee.nombre} ${this.employee.apellido1} ${this.employee.apellido2} ha sido dado de alta`,'success')

              this.addEmployee = false
              this.errorForm = false
              this.$refs.form.reset()
              this.listar()
         }else{
            this.errorForm = true
         }
        },
        async bajaEmpleado(employee){
          //pasamos el id del empleado para verificar si esta asignado a algun proyecto
          //antes de darle de baja
          //consultamos si tiene projectos asignados
          let lista = await employeeService.searchProjectOfEmp(employee.idEmpleado)
          if(lista != null && lista.length != 0){

            let name = employee.nombre+" "+employee.apellido1+" "+employee.apellido2
            //asignamos la lista de projectos 
            this.msgAsigned = "No se puede dar de baja al empleado: "+name.toUpperCase()+" porque está asignado a el/los proyecto/s: "
                              +lista.join(", ").toUpperCase();
            //activamos el msj que mostrará que el empleado tiene projectos asignados
            this.msgAsignedProject = true
            //console.log(lista.length)
          }else{
            //si no tiene projectos asignados le damos de baja
            //console.log("Este empleado no tiene proyectos asignados")
            swal.fire({
                title: 'Realizar baja de empleado?',
                text: `${employee.nombre} ${employee.apellido1} ${employee.apellido2} será dado de baja`,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#37474F',
                cancelButtonColor: '#FFAB00',
                confirmButtonText: 'Si, continuar!',
                cancelButtonText: 'Cancelar'
              }).then((result) => {
                if (result.isConfirmed) {
                  this.darBaja(employee.idEmpleado) //si es confirmado llamamos al metodo para dar de baja definitiva
                  swal.fire(
                    'Baja realizada!',
                    `${employee.nombre} ${employee.apellido1} ${employee.apellido2} ha sido dado de baja`,
                    'success'
                    )
                }
              })
          } 
          
        },
        async darBaja(idEmpleado){
          //damos de baja y listamos
            await employeeService.terminateEmployee(idEmpleado)
            this.listar()

        }, //método para el calendario
        save (date) {
        this.$refs.menu.save(date)
        },// metodo del calendario a actualizar
        save2(date2){
          this.$refs.menu2.save(date2)
        },
        cancelar(){
          this.$refs.form.reset()
          this.addEmployee = false
          this.errorForm = false
        },
        actualizar(emple){
          this.update = true
          
                this.employee.idEmpleado = emple.idEmpleado,
                this.employee.nif = emple.nif,
                this.employee.nombre = emple.nombre,
                this.employee.apellido1 = emple.apellido1,
                this.employee.apellido2 = emple.apellido2,
                this.employee.telefono1 = emple.telefono1,
                this.employee.telefono2 = emple.telefono2,
                this.employee.email = emple.email,
                this.employee.fechaAlta = emple.fechaAlta,
                this.employee.edoCivil = emple.edoCivil,
                this.employee.serMilitar = emple.serMilitar
                //establecemos el valor por default la fecha de nacimiento
                this.date = emple.nacimiento
                this.date2 = this.date
                
          
        },
        async updateEmployee(idEmpleado){

          if(this.$refs.form2.validate()){

            let datos = {
              idEmpleado: idEmpleado,
              nombre: this.employee.nombre,
              apellido1: this.employee.apellido1,
              apellido2: this.employee.apellido2,
              nacimiento: this.date2,
              telefono1: this.employee.telefono1,
              telefono2: this.employee.telefono2,
              email: this.employee.email,
              fechaAlta: this.employee.fechaAlta,
              edoCivil: this.employee.edoCivil,
              serMilitar: this.employee.serMilitar,
              nif: this.employee.nif.toUpperCase()
            }
          console.log(this.employee)

            await employeeService.updateEmployee(datos)
            swal.fire('Modificación realizada','Los datos del empleado han sido modificados correctamente','success')
            this.$refs.form2.reset()
            this.errorForm = false
            this.date = null
            this.date2 = null
            this.listar()
            this.update = false
          }else{
              this.errorForm = true
            }

          
        },
        cancelUpdate(){
          this.date = null
          this.date2 = null
          this.errorForm = false
          //this.$refs.form.reset()
          this.$refs.form2.reset()
          this.update = false
        },
        
    },
    created(){
        //listamos a los empleados al crear el componente
        this.listar()
               
    }
}
</script>