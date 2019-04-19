<!-- 直播列表 -->
<template>
  <div class="live-preview">
    <div class="live-item">
      <div class="live-img">
        <img src="https://cdn2.qnzsvk.cn/pc_static/pc_1.0/20180112/images/surface4.png" alt="">
        <span>直播中</span>
      </div>
      <div class="live-text">
        <p>
          <span>勤勉迎六月，睿智夺金杯————寄语2018届的高三理科生</span>
        </p>
        <p>
          <span>成都树德实验中学&nbsp;刘小平</span>
          <time-down :end-time="startTime"></time-down>
        </p>
      </div>
    </div>
    <div class="live-item">
      <div class="live-img">
        <img src="https://cdn2.qnzsvk.cn/pc_static/pc_1.0/20180112/images/surface4.png" alt="">
        <span class="wating">即将开始</span>
      </div>
      <div class="live-text">
        <p>
          <span>中考探秘——工业流程图</span>
        </p>
        <p>
          <span>成都树德实验中学&nbsp;刘小平</span>
          <time-down :end-time="startTime"></time-down>
        </p>
      </div>
    </div>

  </div>
</template>

<script type="text/ecmascript-6">
  import TimeDown from 'base/time-down/time-down'

  export default {
    data() {
      return {
        startTime: '1516161147'
      }
    },
    components: {
      TimeDown
    }
  }
</script>

<style scoped lang="scss" rel="stylesheet/scss">
  @import "~common/scss/base";
  @import "~common/scss/mixin";

  .live-preview {
    @include flex();
    @include space-between(space-between);
    width: $home-content-width;
    margin: 58px auto 0;
    .live-item {
      cursor: pointer;
      width: 524px;
      @include flex();
      @include space-between(space-between);
      .live-img {
        position: relative;
        img {
          width: 320px;
          height: 164px;
          border-radius: 10px;
        }
        span {
          position: absolute;
          top: -12px;
          right: -12px;
          background: linear-gradient(left, $backgroundColor-e91b47, $backgroundColor-ff6262);
          padding: 8px 12px;
          border-radius: 28px;
          border: 2px solid $color-fff;
          font-size: $font-size-xsmall;
          color: $color-fff;
        }
        .wating {
          background: linear-gradient(left, $backgroundColor-faaa1f, $backgroundColor-ffd24a)
        }
      }
      .live-text {
        @include flex();
        flex-direction: column;
        justify-content: space-around;
        margin-left: 15px;
        p {
          color: $font-2f2e34;
          font-size: $font-size-small;
        }
        p:nth-of-type(1) {
          @include over-Num-Line(2);
          line-height: 24px;
        }
        p:nth-of-type(2) {
          line-height: 35px;
          span {
            display: block;
            color: $font-666;
            font-size: $font-size-xsmall;
          }
          .living {
            color: $font-7abfff;
          }
          .timer {
            font-size: $font-size-xxsmall;
            color: $font-f0304f;
          }
        }
      }
    }
  }
</style>
