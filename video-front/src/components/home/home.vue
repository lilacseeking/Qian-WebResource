<template>
  <div class="home">
    <div class="home-header">
      <swiper-list :swiperData = "swiperData"></swiper-list>
      <!-- <live-preview></live-preview> -->
    </div>
    <div class="home-content">
      <div class="title">
        <i></i>
        <span>精·选·推·荐</span>
        <i></i>
      </div>
      <course-list  
      v-for="(item,index) in courseList"
      :key="index"
      :courseList = "item"
      title="校长面对面"
      :icon="principalIcon"
      isMore = "true" 
      ></course-list>
      <!-- <course-list title="名师公益课"
                   :icon="teacherIcon"
      ></course-list>
      <course-list title="精选课程包"
                   :icon="pageIcon"
                   :courseType="2"
      ></course-list>
      <course-list title="学生经验分享"
                   :icon="studentIcon"
      ></course-list> -->
      <!-- <mental-list title="心理健康课"
                   :icon="mentalIcon"></mental-list> -->
    </div>
    <!-- <q-footer></q-footer> -->
  </div>
</template>

<script type="text/ecmascript-6">
  import SwiperList from 'base/swiper-list/swiper-list'
  import LivePreview from 'base/live-preview/live-preview'
  import CourseList from 'base/course-list/course-list'
  import MentalList from 'base/mental-list/mental-list'
  import QFooter from 'components/q-footer/q-footer'

  import {getAllCourse, getPPTList} from '../../util/api'

  export default {
    data() {
      return {
        principalIcon: './images/homepage_principal_img.png',
        teacherIcon: './images/homepage_master_img.png',
        pageIcon: './images/homepage_package_img.png',
        studentIcon: './images/homepage_students_img.png',
        mentalIcon: './images/homepage_mental_img.png',
        courseList: [],
        swiperData: []
      }
    },
    components: {
      SwiperList,
      LivePreview,
      CourseList,
      MentalList,
      QFooter
    },
    mounted() {
      this.getAllCourses()
      this.getSwiper()
    },
    methods: {
      // 获取 轮播信息
      getSwiper() {
        const params = {
          'isUse': 'Y'
        }

        getPPTList(params).then(res => {
          const { code, content } = {...res.data}
          if (code === 0) {
            this.swiperData = content
          }
        })
      },
      getAllCourses() {
        const params = {
          'page': 1,
          'rows': 10,
          'name': '张老师',
          'type': 'HOME_PAGE'
        }
        getAllCourse(params).then(res => {
          if (res.data.code === 0) {
            let courseList = res.data.content
             // 如何 courseList.length >= 4  ,取前5个
            courseList.forEach((item, index) => {
              if (item.courseDTOList.length >= 5) {
                // courseList.courseDTOList = []
                courseList[index].courseDTOList = item.courseDTOList.slice(0, 5)
              }
            })
  
            this.courseList = courseList

            console.log('courseList', this.courseList)
          }
        })
      }
    }
  }
</script>

<style scoped lang="scss" rel="stylesheet/scss">
  @import "~common/scss/base";

  .home {
    // padding-top: 100px;
    // margin: 0 auto;
    .home-header {
      background-color: $backgroundColor-fff;
      width: 100%;
      padding: 10px 20px;
    }
    .home-content {
      min-width: $window-min-width;
      width: 100%;
      background-color: $backgroundColor-fff;
      padding-top: 68px;
      color: $font-2f2e34;
      font-size: $font-size-xlarge;
      box-shadow: 0 0px 16px $box-shadow-dcdfe5;
      .title {
        text-align: center;
        i {
          display: inline-block;
          vertical-align: middle;
          margin: 0 20px;
          @include widthHeigh(32px);
          @include bg-image('./homepage_recommend_img@2x.png');
        }
      }
    }
  }
</style>
