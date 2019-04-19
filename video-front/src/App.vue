<template>
  <div id="app">
    <!--<register></register>-->
    <q-header></q-header>
    <div class="main">
    <!-- <keep-alive> -->
      <router-view></router-view>
    <!-- </keep-alive> -->
    </div>
  </div>
</template>

<script>
  import Register from 'components/register/register'
  import QHeader from 'components/q-header/q-header'

  export default {
    components: {
      Register,
      QHeader
    }
  }
</script>

<style  lang="scss"  rel="stylesheet/scss">
  #app {
    .swiper-pagination-bullet-active {
      background-color: #fff;
    }
  }
  .main{
    padding-top: 64px;
    // margin: 0 auto;
     background-color: #f2f2f2;
     min-height: calc(100vh - 60px);
  }

</style>
