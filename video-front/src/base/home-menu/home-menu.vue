<template>
  <ul class="menu">
   
<a>登录</a> / <a> 注册</a>
    <!-- <li class="menu-item "
        @mouseenter="mouseenter(0)"
        @mouseleave="mouseleave"
        :class="{hover:itemHoverIndex===0}"
        >
      <p>校长面对面</p>
      <span>走进名校,领略校园风光,与校长面对面</span>
      <i></i>
    </li>
    <li class="menu-item"
        @mouseenter="mouseenter(1)"
        @mouseleave="mouseleave"
        :class="{hover:itemHoverIndex===1}">
      <p>名师公益课</p>
      <span></span>
      <i></i>
    </li>
    <li class="menu-item"
        @mouseenter="mouseenter(2)"
        @mouseleave="mouseleave"
        :class="{hover:itemHoverIndex===2}">
      <p>精选课程包</p>
      <i></i>
    </li>
    <li class="menu-item"
        @mouseenter="mouseenter(3)"
        @mouseleave="mouseleave"
        :class="{hover:itemHoverIndex===3}">
      <p>学生经验分享</p>
      <i></i>
    </li>
    <li class="menu-item"
        @mouseenter="mouseenter(4)"
        @mouseleave="mouseleave"
        :class="{hover:itemHoverIndex===4}">
      <p>心理健康课</p>
      <i></i>
    </li> -->
  </ul>
</template>

<script type="text/ecmascript-6">
  export default {
    data() {
      return {
        bgcShow: false,
        itemHoverIndex: ''
      }
    },
    methods: {
      mouseenter(index) {
        this.itemHoverIndex = index
        this.$emit('enterMenu')
      },
      mouseleave() {
        this.itemHoverIndex = null
        this.$emit('leaveMenu')
      }
    }
  }
</script>

<style scoped lang="scss" rel="stylesheet/scss">
  @import "~common/scss/base";

  .menu {
    position: absolute;
    right: 0;
    top: 65px;
    width: 384px;
    border-radius: 8px;
    background-color: $backgroundColor-f7f8fa;
    .menu-item {
      cursor: pointer;
      position: relative;
      padding: 10px 0;
      text-align: center;
      border-bottom: 1px solid $color-dfe1e2;
      p {
        font-size: $font-size-small;
        color: $font-2f2e34;
        font-weight: bold;
        line-height: 30px;
      }
      span {
        font-size: $font-size-xxsmall;
        color: $font-666;
      }
      i {
        @include widthHeigh(20px);
        @include bg-image('./topnav_tagbar_all_btn@2x.png');
        position: absolute;
        right: 5px;
        top: 30%;
      }
    }
    li:last-child {
      border: none;
    }
    .hover {
      background-color: $backgroundColor-fff;
      box-shadow: 0 0 1px $backgroundColor-fff;
    }
  }
</style>
