<template>
<div class="video">
    <!-- 视频课程学习页面 -->
    <div class="box">
      <div class="videoBox">
        <!-- <video   :poster="video.Png" class="video"  controls id="vd">
          <source  preload="auto" :src="video.url" type="video/mp4"> -->
          <!-- <source  preload="auto" src="http://www.lilacseeking.com/dhfajklsbgnbvnawebvnkgbvnaethbgadv%20aks.mp4" type="video/mp4"> -->
        <!-- </video> -->

        <!-- <videoPlayer> </videoPlayer> -->

        <d-player :options="options" ref="player"></d-player>
      </div>

      <div class="course">
        <el-row><h4 class="coursetitle"> 目录 </h4> </el-row>
        <el-row>

          <el-row v-for="(item,index) in courseList" :key="index" class="item">
            <el-col :span="18">
              <span> 任务 1 ：</span> <span> {{ item.chapterDescription }}</span>
            </el-col>
            <el-col :span="2">
              {{ item.time }}
            </el-col>
            <el-col :span="4">
              <el-button v-if="item.isFree === 'Y'" type="success" @click="_play(item)"> 学习课程</el-button>
              <el-button v-else type="danger" @click="_fuyBoxFunc(item.courseId)" plain> 购买课程</el-button>
            </el-col>
          </el-row>

        </el-row>
      </div>

      <!-- is buy course  compoment -->
      <div v-show="isBuyBox" class="buyBox">
        <div class="buyCourse">
          <!--  course detail -->
          <div>
            <h3 style="line-height: 36px;"> {{ courseInfo.name}} </h3>
            <div> <img :src="courseInfo.thumbnail"> </div>
            <p style="margin-top:20px"> <span> 描述:</span> <span >  {{ courseInfo.description }}</span> </p>
            <p> <span> 标签：</span> <span> {{ courseInfo.tags}} </span> </p>

          </div>
          <!--   is buy course -->
          <div v-if="isBuy" style="background:#c9e8c9;">
            <h2 style="text-align:center;"> 是否购买 </h2>
            <div style="text-align:right">
            <el-button @click="isBuyBox=!isBuyBox"> 否</el-button>
            <el-button @click="_createOrder(courseId)" type="primary"> 是</el-button>
            </div>
          </div>
          <!--  创建订单  、 购买订单 、、 -->
          <div v-else>
            <h2 style="text-align:center">  确认支付  </h2>
            <p>  已生成表单，请确认支付</p>
              <div style="text-align:right">
            <el-button @click="isBuyBox=!isBuyBox ; isBuy =!isBuy"> 取消</el-button>
            <el-button @click="_payF(order.orderNo)" type="primary"> 支付</el-button>
            </div>
          </div>
        </div>
      </div>

    </div>
</div>
</template>

<script>
import VueDPlayer from 'vue-dplayer'
import 'vue-dplayer/dist/vue-dplayer.css'

import { getCourseList, getCourseInfo, createOrder, payOrder } from '../../util/api'

import { mapState } from 'vuex'
require('video.js/dist/video-js.css')
require('vue-video-player/src/custom-theme.css')

// let vd = document.getElementById('vd')
import {updateUserCourseRate} from '../../util/api.js'
export default {
  components: {
    'd-player': VueDPlayer
  },
  data: function() {
    return {
      options: {
        video: {
          url: 'http://static.smartisanos.cn/common/video/t1-ui.mp4',
          // url: 'http://www.lilacseeking.com/dhfajklsbgnbvnawebvnkgbvnaethbgadv%20aks.mp4',
          pic: 'http://static.smartisanos.cn/pr/img/video/video_03_cc87ce5bdb.jpg'
        },
        autoplay: false,
        contextmenu: [
          {
            text: 'GitHub',
            link: 'https://github.com/MoePlayer/vue-dplayer'
          }
        ]
      },
      video: {
        Png: 'http://lilacseeking.oss-cn-beijing.aliyuncs.com/17102400792c354298.jpg',
        src: 'http://vjs.zencdn.net/v/oceans.mp4'
      },
      player: null,
      playerOptions: {
        height: '360',
        width: '200',
        sources: [{
          type: 'rtmp/avi',
          // src: 'https://media.w3.org/2010/05/sintel/trailer.mp4'
          src: './1.mp4'
        }],
        techOrder: ['flash', 'html5'],
        autoplay: false,
        controls: true,
        poster: 'https://surmon-china.github.io/vue-quill-editor/static/images/surmon-9.jpg',
        preload: 'auto', // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
        language: 'zh-CN',
        aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
        fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
        notSupportedMessage: '此视频暂无法播放，请稍后再试'
      },
      courseList: [
        {
          chapterDescription: 'C++是世界上最好的编程语言。',
          chapterId: '第一章',
          chapterNane: '绪论',
          courseId: 5,
          fileName: '安装编译环境',
          time: '20:35',
          url: 'http://lilacseeking.oss-cn-beijing.aliyuncs.com/0c7c7c51-6fe2-4526-9ba9-afcd5a2d9217.avi',
          views: '5478'
        },
        {
          chapterDescription: 'C++是世界上最好的编程语言。',
          chapterId: '第一章',
          chapterNane: '绪论',
          courseId: 5,
          fileName: '安装编译环境',
          time: '20:35',
          url: 'http://lilacseeking.oss-cn-beijing.aliyuncs.com/0c7c7c51-6fe2-4526-9ba9-afcd5a2d9217.avi',
          views: '5478'
        },
        {
          chapterDescription: 'C++是世界上最好的编程语言。',
          chapterId: '第一章',
          chapterNane: '绪论',
          courseId: 5,
          fileName: '安装编译环境',
          time: '20:35',
          url: 'http://lilacseeking.oss-cn-beijing.aliyuncs.com/0c7c7c51-6fe2-4526-9ba9-afcd5a2d9217.avi',
          views: '5478'
        }
      ],
      courseInfo: {},
      courseId: 0,
      order: {},
      isBuy: true,
      isBuyBox: false,  // 判断是否显示 购买框
      contentDTO:{
        userId:'',
        courseId:'',
        chapterId:''
      }
    }
  },
  computed: {
    ...mapState({
        //  isLogin: 'isLogin',
        //  userInfo: 'userInfo',
      showLogin: 'showLogin'
    })
  },
  mounted () {
    this._getCourseList()

    this.player = this.$refs.player.dp
  },
  methods: {
    // 播放 视频， 目前; 将参数（视频地址）放播放其中
    _play(item) {
      // 判断 是否 免费   yes 播放视频    no: 提示购买
      this.player.switchVideo({
        url: item.url
      });
      this.player.play();
      // 更新用户课程学习状态
      this.contentDTO.chapterId = item.chapterId;
      this.contentDTO.courseId = item.courseId;
      this.contentDTO.userId = JSON.parse(localStorage.getItem("userInfo")).id;
      updateUserCourseRate(this.contentDTO).then(res => {
        if (res.code == 1) {
          this.$message.error("用户进度记录失败！")
        }
      })
    },
    //
    _createOrder(courseId) {
      const params = {
        'courseId': courseId,
        'userId': this.$store.state.userInfo.id
      }
      createOrder(params).then(res => {
        const { code, content } = {...res.data}
        if (code === 0) {
          this.isBuy = !this.isBuy
          this.order = content
        }
      })
    },
    //
    _payF(orderNo) {
      const params = {
        'orderNo': orderNo
      }

      payOrder(params).then(res => {
        const { code, content } = {...res.data}
        if (code === 0) {
          console.log('content---createOrder', content)
          this.isBuy = !this.isBuy
          this.isBuyBox = false
          this._getCourseList()
        }
      })
    },
    //
    _fuyBoxFunc(courseId) {
        // 1、是否登录 ，提示登录  ，2 提示购买，视频
      if (this.$store.state.isLogin) {
          // 2 提示购买，视频
        this.isBuyBox = true

        this.courseId = courseId
        this._getCourseInfo(courseId)
      } else {
          // 1\登录 2\提示购买
        this.$store.commit('setShowLogin', true)
      }
    },
    //  fetch course detail
    _getCourseInfo(courseId) {
      const params = {
        'userId': this.$store.state.userInfo.id,
        'courseId': courseId
      }
      getCourseInfo(params).then(res => {
        const { code, content } = {...res.data}
        if (code === 0) {
          this.courseInfo = content
          console.log('content', content)
        }
      })
    },
    // fetch  course list
    _getCourseList () {
      const params = {
        id: this.$route.query.id
      }
      console.log('params', params)
      getCourseList(params).then(res => {
        const {code, content} = {...res.data}
        if (code === 0) {
          console.log('课程列表', content)
          if (content.length > 0) {
            this.courseList = content
          }
        }
      })
    },
    onPlayerPlay(ev) {},
    onPlayerPause(ev) {},
    playerReadied() {},
    playerStateChanged(ev) {}
  }
}
</script>

<style scoped>
.video{
    width: 100%;
}
.box{
    width: 1100px;
    background: #ffffff;
    margin: 0 auto;
    padding-top: 10px;
}
.videoBox {
  width: 800px;
    height: 500px;
    margin: 10px auto;
    padding: 20px 10px;
    border: 10px solid #9d6969
}
.videoBox video {
  width: 100%;
  height: 100%;
}

.course {
      padding: 30px 80px 10px 80px;
}
.coursetitle{
  width: 40px;
  text-align: center;
  color: green;
  border-bottom: 1px solid green;
  line-height: 30px;
  margin-bottom: 20px;
}
.item {
  line-height: 46px;
}

.buyBox{
  position: fixed;
    top: 0px;
    left: 0px;
    background: #fff;
    margin: auto;
    width: 100%;
    height: 100%;
}
.buyCourse {
    margin: 160px auto;
    width: 400px;
    padding: 10px;
    border: 1px solid #aea7a7;
    font-size: 18px;
    color: #5f6163;
}
</style>
