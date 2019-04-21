import Vue from 'vue'
import App from './App.vue'
import uploader from 'vue-simple-uploader'




Vue.use(uploader)


  // Vue.config.productionTip = false /* eslint-disable no-new */ new Vue({   el: '#app',   router,   store,   components: { App },   template: '<App/>' })
new Vue({
  render: h => h(App),
}).$mount('#app')
