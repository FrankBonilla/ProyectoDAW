<template>
<v-app class="section-container">

    <v-row class="signin">
      <v-col cols="4" class="right">
        <h2 >LOGIN</h2>

          <v-form ref="form" class="pa-8 mt-15">
              <v-alert type="error" v-if="error" class="mb-7" style="font-weight: bold">
                Contraseña o usuario incorrecto
              </v-alert>
              <v-text-field
                v-model="username"
                label="Nombre de usuario"
                :rules="generalRules"
                outlined
                dark
                filled
                dense
              ></v-text-field>
            
              <v-text-field
                v-model="password"
                label="Contraseña"
                :append-icon="showPass ? 'mdi-eye' : 'mdi-eye-off'"
                @click:append="showPass = !showPass"
                :rules="generalRules"
                outlined
                dense
                dark
                filled
                :type="showPass ? 'text' : 'password'"
              ></v-text-field>
            
            <div class="text-center" >
              <v-btn class="signin-btn" @click="getAutenticated()" rounded color="success" dark>
                <b>Iniciar Sesión</b>
              </v-btn>
            </div>
          </v-form>

      </v-col>
      <v-col cols="8" class="left">
        <h1>Bienvenidos al Proyecto</h1>
      </v-col>

    </v-row>

</v-app>
</template>

<script>

import axios from 'axios'

export default {

  name: 'Login',
  data(){
    return{
        username: '',
        password: null,
        showPass: false,
        generalRules: [v => !!v || 'este campo es obligatorio'],
        error: false,
    }
  },
  methods: {

    getAutenticated(){

        if(this.$refs.form.validate()){
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
            //guardamos el token recibido por el backend
            localStorage.setItem('token', response.data.tokenAcceso)
            axios.defaults.headers.common['Authorization'] = 'Bearer '+response.data.tokenAcceso
            //axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
            this.$router.push({path: '/'})
            
        })
        .catch(e => {
            console.log(e)
            //console.log(e.response.status)
            if(e.response.status == 401){
              this.error = true
            }
        })
        }
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

