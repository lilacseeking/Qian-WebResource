<template>
  <div id="app">
    <transition name="fade"
                mode="out-in">
      <router-view></router-view>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style lang="scss">
  .custom-tab {
  .el-tabs__nav-wrap::after {
    z-index: -1 !important;
  }
  .el-tabs__item {
    font-size: 16px !important;
  }
  }
  .custom-form {
  .el-form-item__content {
    margin-left: 0px !important;
  }
  }
  .sendCodeBtn {
  .el-button--small, .el-button--small.is-round {
    padding: 12px 15px !important;
  }
  }
  .el-submenu .el-menu {
    background-color: #e4e8f1;
  }
  .operation-container {
  .el-input {
    width: 100% !important;
  }
  .el-select {
    width: 100% !important;
  }
  .el-date-editor--daterange.el-input, .el-date-editor--daterange.el-input__inner, .el-date-editor--timerange.el-input, .el-date-editor--timerange.el-input__inner {
    width: 100% !important;
  }
  }
  .operation-datepicker {
  .el-date-editor--daterange.el-input {
    width: 100% !important;
  }
  }
  .operation-inputnumber {
  .el-input-number--small {
    width: 100% !important;
  }
  }
  .fixscroll {
    overflow-y: auto !important;
    width: 100% !important;
  }
  .operation-bar {
    margin-left: 20px;
    margin-bottom: 5px;
    padding:10px 0 5px;
  }
  .operation-datepick2 {
  .el-date-editor.el-input {
    width: 80% !important;
  }
  }
  .operation-datepick {
  .el-date-editor.el-input {
    width: 100% !important;
  }
  }
  .operation-select2 {
  .el-select {
    width: 80%;
  }
  }
  .operation-inputnumber2 {
  .el-input-number--small {
    width: 80% !important;
  }
  }
  .operation-append {
  .el-input__icon {
    left: 60% !important;
  }
  }
  .operation-append-width {
  .el-input__inner {
    width: 60px;
  }
  }
  .svg-icon {
    width: 1em;
    height: 1em;
    vertical-align: -0.15em;
    fill: currentColor;
    overflow: hidden;
  }
  html, body, #root {
    height: 100%;
  }
  html, body {
    height: 100%;
    width: 100%;
  }
  body, div, dl, dt, dd, ul, ol, li, h1, h2, h3, h4, h5, h6, pre, code, form, fieldset, legend, input, textarea, p, blockquote, th, td, hr, button, article, aside, details, figcaption, figure, footer, header, hgroup, menu, nav, section {
    margin: 0;
    padding: 0;
  }

  ul, ol {
    list-style: none;
  }

  html, body, #root {
    height: 100%;
  }

  body {
    text-rendering: optimizeLegibility;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }
  *, *::before, *::after {
    box-sizing: border-box;
  }
  body {
    -webkit-font-smoothing: antialiased;
    text-rendering: optimizeLegibility;
    height: 100%;
    width: 100%;
    margin: 0;
    padding: 0;
    font-family: "Helvetica Neue For Number", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "Helvetica Neue", Helvetica, Arial, sans-serif;
    font-size: 14px;
    line-height: 1.5;
    color: rgba(0, 0, 0, 0.65);
    background-color: #fff;
  }

  #app {
    height: 100%;
  }

  .el-submenu [class^=fa] {
    vertical-align: baseline;
    margin-right: 10px;
  }

  .el-menu-item [class^=fa] {
    vertical-align: baseline;
    margin-right: 10px;
  }

  .toolbar {
    background: #eef1f6;
    padding: 10px;
  //border:1px solid #dfe6ec;
    margin: 10px 0px;
  .el-form-item {
    margin-bottom: 10px;
  }
  }

  .fade-enter-active,
  .fade-leave-active {
    transition: all .2s ease;
  }

  .fade-enter,
  .fade-leave-active {
    opacity: 0;
  }
</style>
