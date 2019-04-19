<template>
  <div class="q-footer">
    <ul>
      <li>
        <p>联系我们</p>
        <span>TEL:234324324</span>
        <span>QQ：1232132</span>
      </li>
      <li>
        <p>企业合作</p>
        <span>TEL:234324324</span>
      </li>
      <div class="icon">
        <p>
          <span>芊芊学堂</span>
        </p>
        <img src="./qrcode@2x.png" alt="">
      </div>
    </ul>

  </div>
</template>

<script type="text/ecmascript-6">

</script>

<style scoped lang="scss" rel="stylesheet/scss">
  @import "~common/scss/base";

  .q-footer {
    min-width: $window-min-width;
    width: 100%;
    height: 286px;
    padding-top: 60px;
    padding-bottom: 30px;
    box-sizing: border-box;
    background-color: $backgroundColor-edeef0;
    ul {
      position: relative;
      height: 100%;
      width: $home-content-width;
      margin: 0 auto;

      @include flex();
      li {
        margin-right: 78px;
        p {
          font-size: $font-size-xsmall;
          color: $font-2f2e34;
          line-height: 60px;
          font-weight: bold;
        }
        span {
          font-size: $font-size-xxsmall;
          color: $font-666;
          line-height: 30px;
          display: block;
        }
      }
    }
    .icon {
      position: absolute;
      bottom: 0;
      right: 0;
      p{
        span{
          font-size: $font-size-xxsmall;
          color: $font-666;
        }
        &:before{
          content: url("./wechat_logo@2x.png");
          @include widthHeigh(22px);
          display: inline-block;
          vertical-align: middle;
          margin-right: 5px;
        }
      }
      img {
        margin-top: 10px;
        width: 164px;
        height: 164px;
      }
    }
  }
</style>
