<template>
  <v-container class="section-container">
    <v-row class="signin">
      <v-col cols="8" class="left">
        <h1>Bienvenidos al Proyecto</h1>
      </v-col>
      <v-col cols="4" class="right">
        <h2>LOGIN</h2>
          <v-form>
            
              <v-text-field
                v-model="username"
                :error-messages="errors"
                label="Nombre de usuario"
                required
                outlined
                dark
                filled
                dense
              ></v-text-field>
            
              <v-text-field
                v-model="password"
                :error-messages="errors"
                label="Contraseña"
                :append-icon="showPass ? 'mdi-eye' : 'mdi-eye-off'"
                @click:append="showPass = !showPass"
                required
                outlined
                dense
                dark
                filled
                :type="showPass ? 'text' : 'password'"
              ></v-text-field>
            
            <div class="text-center">
              <v-btn class="signin-btn" @click="getAutenticated()" rounded color="white" dark>
                Iniciar Sesión
              </v-btn>
            </div>
          </v-form>
    
      </v-col>
    </v-row>
  </v-container>
</template>

<script>

import axios from 'axios'

export default {
  name: 'Login',
  data(){
    return{
        username: '',
        password: null,
        showPass: false
    }
  },
  methods: {

    getAutenticated(){

        axios({
            method: 'post',
            url: 'http://localhost:8080/api/auth/iniciarSesion',
            params:{
                username: this.username,
                password: this.password
            }
        })
        .then(response => {
            console.log(response.data)
            localStorage.setItem('token', response.data.tokenAcceso)
            axios.defaults.headers.common['Authorization'] = 'Bearer '+response.data.tokenAcceso
            //axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
            this.$router.push({path: '/'})
            
        })
        .catch(e => {
            console.log(e)
        })     
    }
  },
  computed: {
    params() {
      return {
        username: this.username,
        password: this.password
      }
    }
  },
 
}
</script>

