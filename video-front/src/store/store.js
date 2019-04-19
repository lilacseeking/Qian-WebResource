import Vue from 'vue'
import Vuex, { Store } from 'vuex'

Vue.use(Vuex)

// 导出 vue，外部使用

export const store = new Store({
  state: {
    userInfo: JSON.parse(localStorage.getItem('userInfo')) || {},
    isLogin: JSON.parse(localStorage.getItem('isLogin')) || false,
    showLogin: JSON.parse(localStorage.getItem('showLogin')) || false
  },
  mutations: {
    setUserInfo (state, info) {
      state.userInfo = info
    },
    setIsLogin (state, val) {
      state.isLogin = val
    },
    setShowLogin (state, val) {
      state.showLogin = val
    }
  }

})