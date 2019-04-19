import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/login/Login'
import NotFound from './views/404.vue'
import Home from './views/home/Home'


// 用户管理
import UserMgm from './views/user/UserMgm'
// 课程管理
import AddVideoCourse from './views/course/AddVideoCourse'
import CourseMgm from './views/course/CourseMgm'
// 教师管理
import TeacherMgm from './views/teacher/TeacherMgm'
// 订单管理
import OrderMgm from './views/order/OrderMgm'

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
    ]
  },
  {
    path: '/',
    component: Home,
    name: '课程管理',
    iconCls: 'el-icon-menu',
    children: [
      {path:'/addCourse',component: AddVideoCourse, name:'新增课程'},
      {path:'/courseList',component: CourseMgm, name:'课程列表'},
    ]
  },
  {
    path: '/',
    component: Home,
    name: '教师管理',
    iconCls: 'el-icon-menu',
    children: [
      // {path:'/addTeacher',component: AddTeacherMgm, name:'新增教师'},
      {path:'/teacherList',component: TeacherMgm, name:'教师列表'},
    ]
  },
  {
    path: '/',
    component: Home,
    name: '订单管理',
    iconCls: 'el-icon-menu',
    children: [
      {path:'/orderList',component: OrderMgm, name:'订单列表'},
    ]
  },
];

Vue.use(Router);

export default new Router({
  routes: routes
})
