<template>
<v-app>
        
    <v-navigation-drawer app permanent >
      <v-sheet
        color="blue-grey darken-4"
        class="pa-4 fill-height"
      >
        <v-avatar
          class="mb-5 "
          color="grey darken-1"
          size="120">
          <v-img  src="../assets/perfil.jpg"></v-img>
        </v-avatar>

        <div class="white--text"><b>Frank D. Bonilla</b></div>
      
      <v-divider></v-divider>

      <v-row align="center" justify="center" class="mt-15">
        <v-col >
          <!-- usamos btn-links en lugar de router-link para poder usar los botones con esa funcionalidad -->
            <v-btn class="my-3 btn-links" active-class="active" to='/empleados' color="success" elevation="9" exact>Gestion de Empleados</v-btn>
          
            <v-btn class="my-3 btn-links" active-class="active" to='/proyectos' color="success" elevation="9" exact>Gestion de Proyectos</v-btn>
          
        </v-col> 
      </v-row>
      </v-sheet>
    </v-navigation-drawer>
    
      <v-app-bar  app color="blue-grey darken-4">
        <v-toolbar-title id="nav" >
          <router-link class="white--text" to="/inicio">Gestiones RRHH</router-link> 
        </v-toolbar-title>
        <v-spacer></v-spacer>
          <v-btn class="white--text"
                  style="font-weight: bold"
                  color="success"
                  elevation="9"
                  title="Cerrar Sesión"
                  @click="confirmLogOut = true"
                  exact>
                  <v-icon left color="blue-grey darken-4">mdi-clock-start</v-icon>
                  Logout
                  </v-btn>
      </v-app-bar>
      
      <v-main>
        <!-- Confirmación de cierre de sesión  -->
        <v-dialog v-model="confirmLogOut"
                  persistent
                  max-width="400">
        <v-card>
        <v-card-title class="text-h5">
         ¿ Desea cerrar sesión ?
        </v-card-title>
        <v-card-text>Se perderán los datos y/o procesos no confirmados</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
              color="amber accent-4"
              text
              @click="confirmLogOut = false"
            >
            Cancelar
          </v-btn>
          <v-btn
              color="green darken-1"
              text
              @click="logout"
           >
            Confirmar
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
        
         <v-scroll-x-transition mhide-on-leave>
          <!-- aqui renderizamos los componentes de menu empleados, y menu proyectos -->
          <router-view />
          
        </v-scroll-x-transition>
        
       </v-main>
      
    </v-app>
</template>

<script>

export default {
  name: 'Dashboard',

  data(){
    return {
      confirmLogOut: false
    }
  },

  methods: {

    logout(){
      localStorage.removeItem('token')
      this.$router.push({path: '/login'})
    }
    
  }

}
</script>
