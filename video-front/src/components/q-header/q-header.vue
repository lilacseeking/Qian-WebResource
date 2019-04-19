<template>
  <div class="q-nav">
    <div class="nav-logo">
      <router-link to="/">
      <i></i>
      </router-link>
    </div>
    <div class="nav-list">
      <router-link to="/home" tag="div" class="nav-item">
        <span>首页</span>
      </router-link>
      <router-link to="/allCourse" tag="div" class="nav-item">
        <span>全部课程</span>
      </router-link>
      <template v-if="isLogin">
      <router-link to="/personal-center" tag="div" class="nav-item">
        <span>个人中心</span>
      </router-link>
      </template>
    </div>
    <search></search>

  </div>
</template>

<script type="text/ecmascript-6">
  import Search from 'base/search/search'
  import {mapState} from 'vuex'

  export default {
    components: {
      Search
    },
    computed: {
      ...mapState({
        isLogin: 'isLogin'
      })
    }
  }
</script>

<style scoped lang="scss" rel="stylesheet/scss">
  @import "~common/scss/base";

  .q-nav {
    position: fixed;
    top: 0;
    height: 60px;
    width: 100%;
    background-color: $backgroundColor-fff;
    box-shadow: 0 0 16px $box-shadow-d3d4d7;
    z-index: 100;
    .nav-logo {
      float: left;
      width: 92px;
      padding: 10px 30px;
      i {
        width: 120px;
    height: 40px;
        @include bg-image('./logo@2x.png');
      }
    }
    .nav-list {
      float: left;
    }
    .nav-item {
      cursor: pointer;
      padding: 0 40px;
      line-height: 60px;
      text-align: center;
      float: left;
      span {
        font-size: $font-size-small;
        color: $nav-font-5f6163;
      }
      &.router-link-active {
        span {
          color: $nav-active-font-111;
        }
      }
    }

  }
</style>
