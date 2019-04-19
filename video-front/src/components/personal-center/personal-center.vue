<template>
  <div class="persona-center">
    <!-- 个人中心 slide -->
     <div class="slideNav">
       <h4> 个人中心</h4>
       <ul class="slideNav_content">
         <!-- <li><a> 我的课程</a></li>
         <li><a> 我的订单</a></li> -->

         <router-link tag="li" to="/personal-center/mycourse">
           <a>我的课程</a>
        </router-link>

        <router-link tag="li" to="personalCC">
           <a>我的订单</a>
        </router-link>

       </ul>
     </div>

     <!-- mainBody -->
     <div class="mainBody">
       <!-- <transition>
       <keep-alive> -->
         <router-view >

         </router-view>
       <!-- </keep-alive>
       </transition> -->
     </div>
  </div>
</template>

<script>
import { getAllCourse } from '../../util/api'

export default {
  data: function() {
    return {

    }
  },
  mounted() {
    this.getAllCourses()
  },
  methods: {
    getAllCourses() {
      const params = {
        'page': 1,
        'rows': 20,
        'name': '张老师',
        'type': 'HOME_PAGE'
      }
      getAllCourse(params).then(res => {
        console.log('fanhuide  shuju ', res)
      })
    }
  }
}
</script>


<style scoped lang="scss" rel="stylesheet/scss">
.persona-center {
  width: 1100px;
  margin: 0 auto;
  .slideNav{
    display: inline-block;
    padding: 10px;
    width: 100px;
    margin-right: 45px;
    background: #FFF;
    h4{
      font-size: 20px;
    line-height: 50px;
    }
    .slideNav_content{

      li{
            line-height: 36px;
        a{
          cursor: pointer;
        }
      }
    }
  }
  .mainBody{
display: inline-block;
background: #FFF;
width: 908px;
vertical-align: top;
padding: 10px;
min-height: calc(100vh - 100px);
  }
}
</style>
