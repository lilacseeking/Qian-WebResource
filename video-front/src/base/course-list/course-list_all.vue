<!-- 课程列表 -->
<template>
<div>
  <ul v-if=" Object.keys(courseList.resultList).length > 0" class="course-list" >
    <div v-if="courseList.resultList.length > 0" class="box">
    <li v-for="(item,index) in courseList.resultList" :key="index">
      <router-link :to="{path:'/video',query:{id:item.id}}">
      <img :src="item.thumbnail" alt="">
      <p>
        <span>  {{ item.name}}</span>
      </p>
      <p>
        <span>{{  item.description }}</span>
      </p>
      <p class="price" v-show="courseType===1">
        <span v-if="item.isFree === 'Y'" style="color:green;">免费</span>
        <span  v-else>{{ item.price }} 元</span>
        <span class="icon">{{ item.tags}}</span>
      </p>
      <p class="price" v-show="courseType===2">
        <span>￥99.00</span>
        <span>更新至第5课/共20课</span>
      </p>
      </router-link>
    </li>
    </div>
  </ul>
  <div v-else>
    暂无数据
  </div>
</div>
</template>

<script >
  export default {
    name: 'courseList',
    props: {
      courseList: {
        type: Object,
        default: {}
      },
      title: {
        type: String,
        default: ''
      },
      icon: {
        type: String,
        default: ''
      },
      courseType: {
        type: Number,
        default: 1
      }
    },
    data: function() {
      return {
        test: 'sdasdas'
      }
    },
    created() {
    },
    mounted() {
      console.log('courseList', this.courseList)
    }
  }
</script>

<style scoped lang="scss" rel="stylesheet/scss">
  @import "~common/scss/base";

  .course-list {
    width: $home-content-width;
    margin: 36px auto 8px;
    .min-title {
      @include flex();
      justify-content: space-between;
      i {
        img {
          vertical-align: middle;
          margin-right: 6px;
          @include widthHeigh(24px);
        }
      }
      span {
        font-size: $font-size-medium;
        color: $font-2f2e34;
      }
      p:last-child {
        span {
          color: $font-4dadff;
          font-size: $font-size-xsmall;
          cursor: pointer;
        }
      }
    }
    .box{
      display: flex;
      justify-content: space-between;
      flex-wrap: wrap;
      flex-direction: row;
    li {
      cursor: pointer;
      width: 204px;
      margin-top: 20px;
      margin-right: 20px;
      float: left;
      img {
        width: 214px;
        height: 110px;
        border-radius: 10px;
      }
      p {
        span {
          @include over-Num-Line(2);
          font-size: $font-size-xsmall;
          color: $font-2f2e34;
        }
      }
      p:nth-of-type(1) {
        @include flex();
        @include flex-direction-v();
        -webkit-box-pack: justify;
        -webkit-justify-content: space-around;
        -ms-flex-pack: justify;
        justify-content: space-around;
        line-height: 24px;
        min-height: 48px;
      }
      p:nth-of-type(2) {
        line-height: 38px;
        span {
          font-size: $font-size-xxxsmall;
          color: $font-666;

        }
      }
      .price {
        span {
          float: left;
          font-size: $font-size-xxxsmall;
          color: $font-f0304f;
        }
        span:last-child {
          float: right;
          font-size: $font-size-xxxsmall;
          color: $font-2f2e34;
        }
        .icon{
          color: $font-666;
          &:before {
            content: url("./homepage_audience_img@2x.png");
            display: inline-block;
            vertical-align: top;
            margin-top: -5px;
            width: 20px;
            height: 20px;
          }
        }
      }
    }
    }
  }
</style>
