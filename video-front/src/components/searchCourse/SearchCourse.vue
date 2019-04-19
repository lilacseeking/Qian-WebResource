<template>
  <div class="home">
    <div class="home-content">
      <div class="title">
        <i></i>
        <span>搜·索·课·程</span>
        <i></i>
      </div>
      <course-list  
      :courseList = "courseList"
      title="校长面对面"
      :icon="principalIcon"
      ></course-list>
  </div>
  </div>
</template>

<script >
  import CourseList from 'base/course-list/course-list_all'
  import { getAllCourse } from '../../util/api'
  import Bus from '../bus.js'

  export default {
    data() {
      return {
        principalIcon: './images/homepage_principal_img.png',
        teacherIcon: './images/homepage_master_img.png',
        pageIcon: './images/homepage_package_img.png',
        studentIcon: './images/homepage_students_img.png',
        mentalIcon: './images/homepage_mental_img.png',
        courseList: []
      }
    },
    components: {
      CourseList
    },
    mounted() {
      Bus.$on('searchV', val => { // 监听first组件的txt事件
        console.log('zujian -----------', val)
        this.getAllCourses(val)
      })
      this.getAllCourses()
    },
    updated() {
      // this.getAllCourses()
    },
    beforeUpdated() {
      // this.getAllCourses()
    },
    methods: {
      getAllCourses(val) {
        console.log('valvalval', val)
        const params = {
          'page': 1,
          'rows': 10,
          'name': val || this.$route.query.searchV,
          'type': ''
        }

        console.log('this.$router.params.searchV', this.$route.query.searchV)
        getAllCourse(params).then(res => {
          if (res.data.code === 0) {
            this.courseList = res.data.content
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
      padding-bottom: 40px;
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
          @include bg-image('../home/homepage_recommend_img@2x.png');
        }
      }
    }
  }
</style>
