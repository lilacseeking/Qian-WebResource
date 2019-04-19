<template>
  <div class="search">
    <div class="search-box">
      <p class="search-text">
        <input type="text" v-model="searchT">
        <i @click="searchF"></i>
      </p>
      <!-- <p class="icon"
         :class="{show:menuFlage}"
         @mouseenter="mouseEnter"
         @mouseleave="mouseLeave"
      >
        <i></i>
      </p> -->
<!-- 切换登陆状态和未登录 -->
      <div class="icon" > 
        <div v-if="!isLogin">
        <a  @click="_set_showLogin(true)"> 登录</a> /
        <a  @click="showRegister =!showRegister"> 注册</a> 
       </div>
        <el-row v-else>
          <el-dropdown>
            <span class="el-dropdown-link">
              {{ userInfo.username }}
              <i class="el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item> <a @click="_loginOut"> 退出用户登录 </a></el-dropdown-item>
              <el-dropdown-item> <a @click="_regTeacher"> 注册教师 </a></el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          </el-row> 
        </div>

    </div>
    <!-- <home-menu
      v-show="menuFlage"
      @enterMenu="mouseEnter"
      @leaveMenu="mouseLeave"
    ></home-menu> -->

<template v-if="showLogin">
    <login @Cancel="_set_showLogin(false)"></login>
</template>

    <template style="" v-if="showRegister">
      <register @Cancel="showRegister =!showRegister"></register>
    </template>
  </div>
</template>

<script type="text/ecmascript-6">
  // import HomeMenu from 'base/home-menu/home-menu'
   import login from '../../components/loginRegister/login'
   import register from '../register/register'

   import { loginOut } from '../../util/api'
   import { mapState } from 'vuex'
   import Bus from '../../components/bus.js'

 export default {
     data: function() {
       return {
         searchT: '',
         showRegister: false,
         menuFlage: false,
         timer: '',
         user: {
           isLogin: false,
           userInfo: {}
         }
       }
     },
     components: {
      // HomeMenu
       login,
       register
     },
     computed: {
       ...mapState({
         isLogin: 'isLogin',
         userInfo: 'userInfo',
         showLogin: 'showLogin'
       })
     },
     created() {
      //  判断 state 中 是否存在 token 和 username
      //  1 、有，默认已经登录
      //  2、 空，未登录
       console.log('this.$store.state.userInfo.---', this.isLogin)

      //  if (this.userInfo.token && this.userInfo.username) {
      //    console.log('this.user.userInfo', this.user)
      //  }
       console.log('this.user.isLogin', this.user.isLogin)
     },
     methods: {
      //
       _regTeacher() {
         this.$router.push('/teacherRegister')
       },
      //  search function
       searchF() {
        // fetch  value，
        // 1、 check empty
        // 2,  go  SearchCourse  route
         console.log('ssssss', this.searchT)
         if (this.searchT.length > 0) {
           const sV = this.searchT
           if (this.$route.path.indexOf('SearchCourse') > 0) {
             Bus.$emit('searchV', sV)
           } else {
            //  没有包含，进行跳转
             this.$router.push({path: '/SearchCourse', query: {searchV: sV}})
           }
           console.log('luyu', typeof this.$route.path)
         } else {
           this.$message(' 输入内容为空，请填写搜索内容')
         }
       },
      //
       _set_showLogin(val) {
         this.$store.commit('setShowLogin', val)
       },
      //  用户登录退出
       _loginOut() {
         const params = {
           mobile: this.userInfo.mobile,
           username: this.userInfo.username
         }

         loginOut(params).then(res => {
           const {code} = {...res.data}
           if (code === 0) {
            //  this.isLogin = false
            //  this.userInfo = {}
             this.$store.commit('setUserInfo', '')
             this.$store.commit('setIsLogin', false)
             this.$router.replace('/')

             if (window.localStorage) {
               let storage = window.localStorage
               storage.clear()
             } else {
               console.logt('浏览器支持localStorage')
             }

             this.$message({
               message: '退出登录成功',
               type: 'success'
             })
           }
         })
       },
       mouseEnter() {
         clearTimeout(this.timer)
         this.menuFlage = true
       },
       mouseLeave() {
         this.timer = setInterval(() => {
           this.menuFlage = false
         }, 500)
       }
     }
 }
</script>

<style scoped lang="scss" rel="stylesheet/scss">
  @import "~common/scss/base";

  .search {
    float: left;
    position: absolute;
    right: 30px;
    .search-box {
      @include flex();
      @include flex-align-center(center);
      height: 60px;
    }
    .search-text {
      display: inline-block;
      position: relative;
      input {
        outline: none;
        padding: 0 30px 0 10px;
        width: 320px;
        height: 40px;
        border: none;
        border-radius: 20px;
        box-sizing: border-box;
        background-color: $backgroundColor-f5f6f7;
        box-shadow: inset 0px 0px 12px $box-shadow-eeeff0;
      }
      i {
        cursor: pointer;
        position: absolute;
        top: 27%;
        right: 10px;
        @include widthHeigh(20px);
        @include bg-image('./topnav_search_btn_n@2x.png');
      }
    }
    .icon {
      cursor: pointer;
      margin-left: 147px;
      vertical-align: sub;
      display: inline-block;
      box-sizing: border-box;
      padding: 5px 7px;
      i {
        width: 20px;
        height: 2px;
        border-top: 2px solid $font-666;
        border-bottom: 2px solid $font-666;
        background-clip: content-box;
        background-color: $font-666;
        padding: 7px 0;
      }
    }
    .show {
      box-shadow: 0 0 4px $box-shadow-d3d4d7;
    }
  }
</style>
