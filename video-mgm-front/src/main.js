// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './routes'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import store from './vuex/store'
import Vuex from 'vuex'
import 'nprogress/nprogress.css'
//进度条
import NProgress from 'nprogress'
import HttpInterceptors from './HttpInterceptors'
import VeHistogram from 'v-charts/lib/histogram'
import VeLine from 'v-charts/lib/line'
import cookieStore from './common/js/cookie'
import VideoPlayer from 'vue-video-player'
// import './views/user/custom-theme.css'
Vue.use(VideoPlayer)
Vue.component(VeHistogram.name, VeHistogram)
Vue.component(VeLine.name, VeLine)

// Vue.config.productionTip = false
//启用饿了么ui
Vue.use(ElementUI)
Vue.use(Vuex)
// require('!style-loader!css-loader!less-loader!./views/user/custom-theme.css');
router.beforeEach((to, from, next) => {
  NProgress.start();
  if (to.path == '/login') {
    sessionStorage.removeItem('user');
  }
  let user = JSON.parse(sessionStorage.getItem('user'));
  if (!user && to.path != '/login') {
    next({ path: '/login' })
  } else {
    next()
  }
})

router.afterEach(transition => {
  NProgress.done();
});

//cookie仓库
Vue.prototype.$cookieStore = cookieStore;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  store,
  render: h => h(App),
  HttpInterceptors,
})
