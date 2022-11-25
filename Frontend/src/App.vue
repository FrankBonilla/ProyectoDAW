<template>
  <div id="app">
    
        <v-scroll-x-transition mhide-on-leave>
          <!-- aqui renderizamos los componentes de menu empleados, y menu proyectos -->
          <router-view />
        </v-scroll-x-transition>
     
  </div>
</template>

<script>


export default {
  name: 'App',
     
}
</script>

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
