<template>
  <div id="app">
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
          <v-img  src="./assets/perfil.jpg"></v-img>
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
          <router-link class="white--text" to="/">Proyecto</router-link> 
        </v-toolbar-title>
      </v-app-bar>
      <v-main>
        <v-scroll-x-transition mhide-on-leave>
          <!-- aqui renderizamos los componentes de menu empleados, y menu proyectos -->
          <router-view />
        </v-scroll-x-transition>
      </v-main>
      
    </v-app>
  </div>
</template>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
