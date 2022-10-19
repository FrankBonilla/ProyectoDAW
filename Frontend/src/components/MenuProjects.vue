<template>
    <v-main class="pl-1 pt-6 indigo lighten-5 fill-height">
        <v-row class="about" align="center" justify="space-around">
            
            <v-btn class="btn-links" active-class="active" to='/proyectos/alta' color="success"> Proyectos de alta </v-btn>
            
            <v-btn class="btn-links" active-class="active" to="/proyectos/baja" color="success" >Proyectos de baja </v-btn>
             
            <v-btn class="btn-links" active-class="active" to="/proyectos/todos" color="success">Todos los proyectos</v-btn>
            
        </v-row>
        <v-row >
            <v-scale-transition hide-on-leave>
                <router-view />
            </v-scale-transition>
        </v-row>
  </v-main>
</template>

<script>
export default {
    name: 'MenuProjects'
}
</script>