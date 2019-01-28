import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/login/Login'
import NotFound from './views/404.vue'
import Home from './views/home/Home'
import HelloWorld from './views/testComponent/HelloWorld'
import Test from './views/user/test'
import AddTeacherMgm from './views/teacher/AddTeacherMgm'

// 用户管理
import UserMgm from './views/user/userMgm'
// 课程管理
import AddVideoCourse from './views/course/AddVideoCourse'

let routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login,
    hidden: true
  },
  {
    path: '/404',
    name: 'NotFound',
    component: NotFound,
    hidden: true
  },
  {
    path: '/',
    component: Home,
    name: '用户管理',
    iconCls: 'el-icon-menu',
    children: [
      {path:'/',redirect:'userMgm', hidden: true},
      {path:'/userMgm',component: UserMgm, name:'用户列表'},
      // {path:'/test',component: Test, name:'测试路由'},
    ]
  },
  {
    path: '/',
    component: Home,
    name: '课程管理',
    iconCls: 'el-icon-menu',
    children: [
      // {path:'/',redirect:'userMgm', hidden: true},
      {path:'/addCourse',component: AddVideoCourse, name:'新增课程'},
      // {path:'/test',component: Test, name:'测试路由'},
    ]
  },
  {
    path: '/',
    component: Home,
    name: '教师管理',
    iconCls: 'el-icon-menu',
    children: [
      // {path:'/',redirect:'userMgm', hidden: true},
      {path:'/addTeacher',component: AddTeacherMgm, name:'新增教师'},
      // {path:'/test',component: Test, name:'测试路由'},
    ]
  },
];

Vue.use(Router);

export default new Router({
  routes: routes
})
