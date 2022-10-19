<template>
    <v-main class="pl-1 pt-6 indigo lighten-5 fill-height">

        <v-row class="about" align="center" justify="space-around">
            <v-btn class="btn-links" active-class="active" to='/empleados/suscribe-employee' @click="background=false" color="success"> Empleados de alta </v-btn>
            
            <v-btn class="btn-links" active-class="active" to="/empleados/unsuscribe-employee" @click="background=false" color="success" >Empleados dados de baja </v-btn>
            
            <v-btn class="btn-links" active-class="active" to="/empleados/all-employees" @click="background=false" color="success">Todos los empleados</v-btn>

        </v-row>
        <v-row >
            
            <!-- Ocultamos la imagen de fondo cuando se cargue cualquiera de las tablas 
            <v-row v-show="background" class="mt-5">
             <v-col justify="center" align="center">
                <v-img src="../assets/rrhh.png" :width="600"></v-img>
             </v-col>
            </v-row>
            -->
            <v-scale-transition hide-on-leave>
                <router-view />
            </v-scale-transition>
        </v-row>
  </v-main>
</template>

<script>
export default {
    name: 'menuEmployee',
    data(){
        return {
            //background: true
        }
    },
    
}
</script>
