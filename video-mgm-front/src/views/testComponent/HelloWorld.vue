<template>
  <section>
    <el-input v-model="param.name" @keyup.enter.native="hello" :onkeyup="hello()" size="small"/>
    <h1>{{param.text}}</h1>
  </section>
</template>

<script>
  import {helloWorldService} from './HelloWorldService'

  export default {
    data() {
      return {
        param: {
          text: '',
          name: '',
        },
      }
    },
    mounted() {
      this.$nextTick(()=>{
        this.$message({type: 'success', message: 'HelloWorld'});
      });
    },
    methods: {
      hello() {
        helloWorldService.hello(this.param).then(res => {
          this.param.text = res;
        })
      },
    },
  }
</script>

<style scoped>

</style>
