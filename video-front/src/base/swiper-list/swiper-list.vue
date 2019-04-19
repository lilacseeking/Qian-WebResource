<!-- 轮播图组件 -->
<template>
  <div class="swiper-list">
    <div class="swiper-container">
      <a
        class="button prev button-hover"
        @click="prevSlider"
      >
        <i></i>
      </a>
      <a class="button next" @click="nextSlider">
        <i></i>
      </a>
      <swiper v-if="swiperData.length > 0" class="swiper" :options="swiperOption" ref="mySwiper">
        <!-- slides -->
        <swiper-slide v-for="(item,index) in swiperData" :key="index">
          <img :src="item.url" alt="">
        </swiper-slide>
        <!-- <swiper-slide>
          <img src="https://cdn2.qnzsvk.cn/pc_static/pc_1.0/20180112/images/surface-swiper@2x-2.png" alt="">
        </swiper-slide>
        <swiper-slide>
          <img src="https://cdn2.qnzsvk.cn/pc_static/pc_1.0/20180112/images/surface-swiper@2x.png" alt="">
        </swiper-slide> -->
        <!-- Optional controls -->
        <div ref="pagination" class="swiper-pagination" slot="pagination"></div>
      </swiper>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  import {swiper, swiperSlider} from 'vue-awesome-swiper'

  export default {
    props: {
      swiperData: {
        type: Array,
        default: []
      }
    },
    data() {
      return {
        swiperOption: {
          // 只有一个slider的时候，没有切换键
          watchOverflow: true,
          notNextTick: true,
          releaseOnEdges: true,
          /* speed: 1000,
          autoplay: {
            delay: 3000
          }, */
          autoplayDisableOnInteraction: true,
          preventClicks: false,
          // 循环轮播
          loop: true,
          pagination: {
            el: '.swiper-pagination',
            clickable: true
          }
        }
      }
    },
    computed: {
      swiper() {
        return this.$refs.mySwiper.swiper
      }
    },
    components: {
      swiper,
      swiperSlider
    },
    mounted() {
    },
    methods: {
      // 左右切换
      prevSlider() {
        this.$refs.mySwiper.swiper.slidePrev()
      },
      nextSlider() {
        this.$refs.mySwiper.swiper.slideNext()
      }
    }
  }
</script>

<style scoped lang="scss" rel="stylesheet/scss">
  @import "~common/scss/base";

  .swiper-list {
    width: 100%;
    height: 340px;
    .swiper-container {
      position: relative;
      width: $home-content-width;
      .button {
        display: block;
        position: absolute;
        top: 50%;
        z-index: 10;
        @include widthHeigh(40px);
        &:hover{
          background-color: $backgroundColor-00003;
        }
      }
      .prev {
        left: 0;
        i {
          @include widthHeigh(40px);
          @include bg-image('./banner_left_btn_n@2x.png')
        }
      }
      .next {
        right: 0;
        i {
          @include widthHeigh(40px);
          @include bg-image('./banner_right_btn_n@2x.png')

        }
      }
      .swiper {
        height: 340px;
        margin: 0 auto;
        .swiper-slide {
          img {
            width: 100%;
            height: 100%;
            box-shadow: 0 0 16px $box-shadow-d3d4d7;
          }
        }
      }
    }
  }
</style>
