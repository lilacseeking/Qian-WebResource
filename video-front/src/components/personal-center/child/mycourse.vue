<template>
    <div >
        <div style="border-bottom:1px solid #f2f2f2;padding: 10px;margin-bottom: 30px;"> <span > 我的课程</span>
         <!-- <span style="background:#007aff;color:#fff;float:right;padding:6px;"> 直播课表</span> -->
          </div>
   <div class="courseBody" >

       <div v-for="(item,index) in myCourselist.resultList" :key="index" class="item">
           <div class="subitem" style="width:200px;height:140px;">
               <img :src="item.thumbnail">
           </div>
           <div style="width:400px;">
               <el-row><h4 style="text-align:left;line-height:30px;">  {{ item.courseName}}</h4></el-row>
               <el-row>
                   <el-col :span="4"><span> 学习进度</span> </el-col>
                   <el-col :span="20"><el-progress :percentage="item.completion *100"></el-progress></el-col>
               </el-row>
           </div>
           <div >
                <!-- <el-button> 继续学习 </el-button>  -->
           <router-link :to="{path:'/video',query:{id:item.courseId}}" tag="el-button" class="nav-item">
        <span>继续学习</span>
      </router-link>
           </div>
       </div>

        <!-- <div class="item">
           <div class="subitem" style="width:200px;height:140px;">
               <img src="static/img/personal/course.jpg">
           </div>
           <div style="width:400px;">
               <el-row><h4 style="text-align:left;line-height:30px;"> java 基础</h4></el-row>
               <el-row>
                   <el-col :span="4"><span> 学习进度</span> </el-col>
                   <el-col :span="20"><el-progress :percentage="70"></el-progress></el-col>
               </el-row>
           </div>
           <div > <el-button> 继续学习 </el-button></div>
       </div>

        <div class="item">
           <div class="subitem" style="width:200px;height:140px;">
               <img src="static/img/personal/course.jpg">
           </div>
           <div style="width:400px;">
               <el-row><h4 style="text-align:left;line-height:30px;"> java 基础</h4></el-row>
               <el-row>
                   <el-col :span="4"><span> 学习进度</span> </el-col>
                   <el-col :span="20"><el-progress :percentage="70"></el-progress></el-col>
               </el-row>
           </div>
           <div > <el-button> 继续学习 </el-button></div>
       </div> -->

   </div>
    </div>
</template>

<script>

import { getCourseListByUser } from '../../../util/api'
import {mapState} from 'vuex'
export default {
  components: {},
  data: function() {
    return {
      myCourselist: {
        'count': 1,
        'countPage': 1,
        'currentPage': 1,
        'firstResult': 0,
        'resultList': [
          {
            'achieve': 0,
            'completion': 0,
            'courseId': 5,
            'creater': 1,
            'gmtCreate': 1553420010000,
            'guid': '02f50dbb3cc44a71a553a7005fd909bf',
            'id': 1,
            'isFinish': 'N',
            'userId': 6,
            'version': 0
          }
        ],
        'rows': 20
      }
    }
  },
  computed: {
    ...mapState({
      isLogin: 'isLogin',
      userInfo: 'userInfo'
    })
  },
  created() {
    this.getCourseListBuy()
  },
  methods: {
    getCourseListBuy() {
      const params = {
        'page': 1,
        'rows': 10,
        'userId': this.userInfo.id
      }
      getCourseListByUser(params).then(res => {
        const {code, content} = {...res.data}
        if (code === 0) {
          console.log('content', content)
          this.myCourselist = content
        }
      })
    }
  }
}
</script>

<style scoped>
.item{
    width: 100%;
    box-shadow: 1px 6px 7px #f2f2f2;
}
.item > div {
    display: inline-block;
    text-align: center;
    vertical-align: middle;
    margin-right: 20px;
}
.item  img {
    width: 100%;
    padding: 10px;
}
</style>

