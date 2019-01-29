import Vue from 'vue'
import Router from 'vue-router'
import Login from './components/Login'
import HelloWorld from './components/HelloWorld'
import Home from 'Home'
import About from 'About'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path:'/home',
      name:'home',
      component:Home,
    },
    {
      path:'/about',
      name:'about',
      component:About,
    },
    {
      path: '/',
      redirect: '/home'
    }
    // {
    //   path: '/',
    //   name: 'Login',
    //   component: Home,
    //   children:[
    //     {path: '/login', component: Login, name: '统一登录'},
    //     {path: '/hello', component: HelloWorld, name: '初始化'},
    //
    //     ]
    // }

  ]
})
