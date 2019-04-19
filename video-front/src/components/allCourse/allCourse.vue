<template>
  <div class="home">
    <div class="home-content">
      <div class="title">
        <i></i>
        <span>全·部·课·程</span>
        <i></i>
      </div>
      <course-list
      v-if="courseList.resultList.length > 0"
      :courseList = "courseList"
      title="校长面对面"
      :icon="principalIcon"
      ></course-list>
      <!--  加载更多 -->
      <el-row >
        <el-row style="margin-bottom:20px;"> 
          <course-list
            v-if="courseList_more.resultList.length > 0"
            :courseList = "courseList_more"
            title="校长面对面"
            :icon="principalIcon"
            ></course-list>
           </el-row>
        <el-row style="width:1200px;margin: 10px auto">  <el-button v-show="isLoadMore" @click="_loadMore" style="width:100%;">  加载更多 ... </el-button> </el-row>
      </el-row>
  </div>
  </div>
</template>

<script >
  import CourseList from 'base/course-list/course-list_all'
  import { getAllCourse } from '../../util/api'

  export default {
    data() {
      return {
        principalIcon: './images/homepage_principal_img.png',
        teacherIcon: './images/homepage_master_img.png',
        pageIcon: './images/homepage_package_img.png',
        studentIcon: './images/homepage_students_img.png',
        mentalIcon: './images/homepage_mental_img.png',
        courseList: {
          resultList: []
        },
        courseList_more: {
          resultList: []
        },
        page: 0,
        isLoadMore: true
      }
    },
    components: {
      CourseList
    },
    created() {
      this.page++
      this.getAllCourses(this.page, 10, res => {
        this.courseList = res
      })
    },
    mounted() {
      // this.getAllCourses()
    },
    methods: {
      //  加载更多
      _loadMore () {
        this.page++
        this.getAllCourses(this.page, 10, res => {
          this.courseList_more.resultList = this.courseList_more.resultList.concat(res.resultList)
          // 判断是否还有数据
          console.log('this.page', this.page * 10)
          console.log('res.count', res.count)
          if (this.page * 10 >= res.count) {
            this.isLoadMore = false
          }
        })
      },
      getAllCourses(page, rows, callback) {
        const params = {
          'page': page,
          'rows': rows,
          'name': '',
          'type': ''
        }
        getAllCourse(params).then(res => {
          if (res.data.code === 0) {
            //  res.data.content
            callback(res.data.content)
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
