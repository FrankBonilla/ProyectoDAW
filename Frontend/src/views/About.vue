<template>
 <v-main class="pl-1 pt-6 indigo lighten-5">
  <v-row class="about" align="center" justify="space-around">
    <router-link to='/empleados'>
    <v-btn color="success"> Empleados de alta </v-btn>
    </router-link>
    <v-btn color="success" >Empleados dados de baja </v-btn>
    <v-btn color="success">Todos los empleados</v-btn>
  </v-row>
  <v-row>
    <router-view/>
  </v-row>
  
  </v-main>
</template>
