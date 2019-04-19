import axios from 'axios'
import {store} from '../store/store'
import router from '../router'

import {
  // Message,
  MessageBox
} from 'element-ui'

// import store from 'store'
// import Cookies from 'vue-js-cookie'
// import QS from 'qs'
 // 创建axios实例
const service = axios.create({
  // baseURL: ' http://192.168.199.229:8077',
  baseURL: ' http://47.107.110.132:8077',
  //  baseURL: ' http://localhost:8077',
  timeout: 15000 // 请求超时时间
});

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 每次发送请求之前判断vuex中是否存在token
    // 如果存在，则统一在http请求的header都加上token，这样后台根据token判断你的登录情况
    // 即使本地存在token，也有可能token是过期的，所以在响应拦截器中要对返回状态进行判断

    //  set token  mobile  into request header
    function setHeaders (userInfo) {
      config.headers['token'] = userInfo.token
      config.headers['mobile'] = userInfo.mobile
    }
    if (store.state.userInfo.token) {
      setHeaders(store.state.userInfo)
      return config
    } else {
      let storage
      if (window.localStorage) {
        storage = window.localStorage
        let userInfo = JSON.parse(storage.getItem('userInfo'))

        //  判断localstorage 中是否 有 token 信息，
        //  1、有----》存
        //  2、false ----》 提示 login
        if (userInfo) {
          setHeaders(userInfo)
          return config
        } else {
          return config
        }
      } else {
        console.logt('浏览器支持localStorage')
      }
    }

    // return config
  },
    error => {
      return Promise.error(error)
    })

// 响应拦截器
service.interceptors.response.use(
  response => {
  // 如果返回的状态码为200，说明接口请求成功，可以正常拿到数据
  // 否则的话抛出错误

    if (response.status === 200) {
      if (response.data.code === 1) {
        if (response.data.msg === 'HAVE_NOT_LOGIN_IN') {
          MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出', {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            localStorage.clear()
            router.push('/')
            location.reload() // 为了重新实例化vue-router对象 避免bug
          })

          // return Promise.reject(response)
        }
      } else {
        return Promise.resolve(response)
      }
    } else {
      return Promise.reject(response)
    }
  }
  // 服务器状态码不是2开头的的情况
  // 这里可以跟你们的后台开发人员协商好统一的错误状态码
  // 然后根据返回的状态码进行一些操作，例如登录过期提示，错误提示等等
  // 下面列举几个常见的操作，其他需求可自行扩展
  // error => {
  //   console.log('error.response.', error)
  //   if (error.response.status) {
  //     switch (error.response.status) {
  //  // 401: 未登录
  //  // 未登录则跳转登录页面，并携带当前页面的路径
  //  // 在登录成功后返回当前页面，这一步需要在登录页操作。
  //       case 401:
  //         // router.replace({
  //         //   path: '/login',
  //         //   query: {
  //         //     redirect: router.currentRoute.fullPath
  //         //   }
  //         // })
  //         break

  //  // 403 token过期
  //  // 登录过期对用户进行提示
  //  // 清除本地token和清空vuex中token对象
  //  // 跳转登录页面
  //       case 403:
  //         // Toast({
  //         //   message: '登录过期，请重新登录',
  //         //   duration: 1000,
  //         //   forbidClick: true
  //         // })
  //   // 清除token
  //         localStorage.removeItem('token')
  //         // store.commit('loginSuccess', null)
  //   // 跳转登录页面，并将要浏览的页面fullPath传过去，登录成功后跳转需要访问的页面
  //         // setTimeout(() => {
  //         //   router.replace({
  //         //     path: '/login',
  //         //     query: {
  //         //       redirect: router.currentRoute.fullPath
  //         //     }
  //         //   })
  //         // }, 1000)
  //         break

  //  // 404请求不存在
  //       case 404:
  //         // Toast({
  //         //   message: '网络请求不存在',
  //         //   duration: 1500,
  //         //   forbidClick: true
  //         // })
  //         break
  //  // 其他错误，直接抛出错误提示
  //       default:
  //         // Toast({
  //         //   message: error.response.data.message,
  //         //   duration: 1500,
  //         //   forbidClick: true
  //         // })
  //     }
  //     return Promise.reject(error.response)
  //   }
  // }
  )

export default service
