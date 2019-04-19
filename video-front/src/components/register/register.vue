<template>
  <div class="register">
    <section>
      <!-- logo -->
      <img class="log" src="./youth_icon_img.png" alt="">
      <!-- 二维码-->
      <div class="arrow_code">
        <img class="arrow" src="./arrow_img.png" alt="">
        <i class="code">
          <img src="./qrcode@2x.png" alt="">
        </i>
      </div>
      <!-- 文字 -->
      <div class="text">
        <span>芊芊学堂</span>
      </div>
      <!-- line -->
      <img class="line" src="./content_line_img.png" alt="">
      <!-- 微信提示 -->
      <div class="wx_logo">
        <img src="./wechat_icon_img.png" alt="">
        <span>扫描二维码登录</span>
      </div>
    </section>
  </div>
</template>

<script type="text/ecmascript-6">

</script>

<style scoped lang="scss" rel="stylesheet/scss">
  @import "~common/scss/base";

  .register {
    min-width: $window-min-width;
    width: 100%;
    height: 100%;
    @include bg-image('./background_img.png');
    section{
      width: 400px;
      height: 450px;
      position: absolute;
      top: 50%;
      left:50%;
      @include prefixer(transform,translate(-50%,-50%),webkit moz o ms)
    }
    .log{
      display: block;
    }
    .arrow_code{
      margin-top: 26px;
      margin-left: 80px;
      .code{
        display: inline-block;
        width: 120px;
        height:120px;
        margin-top: 40px;
        img{
          width: 100%;
          height:100%;
        }
      }
      .arrow{
        vertical-align: top;
        margin-right: 18px;
      }
    }
    .text{
      margin-top: 28px;
      font-size: 36px;
      color: #4f4c4c;
      text-align: left;
    }
    .line{
      margin-top: 26px;
    }
    .wx_logo{
      margin-top: 20px;
      img{
        width: 41px;
        height:32px;
      }
      span{
        font-size: 32px;
        color: #b2b2b2;
        margin-left: 10px;
      }
    }
  }
</style>
