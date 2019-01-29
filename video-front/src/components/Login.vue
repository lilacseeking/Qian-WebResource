<template>
  <div>
    <p>{{aaa}}</p>
    <div>
      <el-form>
        <el-input></el-input>
        <el-input></el-input>
        <el-button type="success">登录</el-button>
        <el-button type="error">重置</el-button>
      </el-form>

    </div>
  </div>
</template>

<script>
    export default {
        name: "Login",
        data: {
          aaa:'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'
        }
    }
</script>

<style scoped>

</style>
