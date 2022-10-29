import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import router from './router'

Vue.config.productionTip = false

Vue.filter('formatedDate', function (value){
  if (!value) return null
        let formatedDate = value.split("-").reverse().join("/")
        return formatedDate
})

new Vue({
  vuetify,
  router,
  render: h => h(App)
}).$mount('#app')
