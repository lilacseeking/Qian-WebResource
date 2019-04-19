<!-- 心理健康课列表 -->
<template>
  <ul class="mental-list">
    <div class="min-title">
      <p>
        <i>
          <img :src="icon" alt="">
        </i>
        <span>{{title}}</span>
      </p>
      <router-link tag="p" to="/more">
        <span> 查看更多>></span>
      </router-link>
    </div>
    <li>
      <div class="item-image">
        <img src="https://cdn2.qnzsvk.cn/pc_static/pc_1.0/20180112/images/surface4.png" alt="">
      </div>
      <div class="item-text">
        <p>
          <span>勤勉迎六月，睿智夺金杯————寄语2018届的高三理科生</span>
        </p>
        <p>
          <span>成都树德实验中学&nbsp;刘小平</span>
        </p>
        <p>
          <span>免费</span>
          <span>1.6万</span>
        </p>
      </div>
    </li>
    <li>
      <div class="item-image">
        <img src="https://cdn2.qnzsvk.cn/pc_static/pc_1.0/20180112/images/surface4.png" alt="">
      </div>
      <div class="item-text">
        <p>
          <span>勤勉迎六月，睿智夺金杯————寄语2018届的高三理科生</span>
        </p>
        <p>
          <span>成都树德实验中学&nbsp;刘小平</span>
        </p>
        <p>
          <span>免费</span>
          <span>1.6万</span>
        </p>
      </div>
    </li>
    <li>
      <div class="item-image">
        <img src="https://cdn2.qnzsvk.cn/pc_static/pc_1.0/20180112/images/surface4.png" alt="">
      </div>
      <div class="item-text">
        <p>
          <span>勤勉迎六月，睿智夺金杯————寄语2018届的高三理科生</span>
        </p>
        <p>
          <span>成都树德实验中学&nbsp;刘小平</span>
        </p>
        <p>
          <span>免费</span>
          <span>1.6万</span>
        </p>
      </div>
    </li>
  </ul>
</template>

<script type="text/ecmascript-6">
  export default {
    props: {
      title: {
        type: String,
        default: ''
      },
      icon: {
        type: String,
        default: ''
      }
    }
  }
</script>

<style scoped lang="scss" rel="stylesheet/scss">
  @import "~common/scss/base";

  .mental-list {
    width: $home-content-width;
    margin: 36px auto 0;
    overflow: hidden;
    padding-bottom: 40px;
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

    li {
      cursor: pointer;
      width: 364px;
      margin-top: 20px;
      margin-right: 20px;
      float: left;
      @include flex();
      .item-image {
        img {
          @include widthHeigh(140px);
          border-radius: 10px;
          margin-right: 10px;
        }
      }
      .item-text {
        @include flex();
        @include space-between(space-around);
        flex-direction: column;
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
          @include space-between(space-around);
          line-height: 24px;
          min-height: 48px;
        }
        p:nth-of-type(2) {
          line-height: 38px;
          span {
            font-size: $font-size-xxsmall;
            color: $font-666;
          }
        }
        p:nth-of-type(3) {
          span {
            float: left;
            font-size: $font-size-xxsmall;
            color: $font-f0304f;
          }
          span:last-child {
            float: right;
            font-size: $font-size-xxsmall;
            color: $font-666;
            &:before {
              content: url("./homepage_audience_img@2x.png");
              display: inline-block;
              vertical-align: middle;
              width: 20px;
              height: 20px;
            }
          }
        }
      }
    }
  }
</style>

