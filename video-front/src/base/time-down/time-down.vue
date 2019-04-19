<template>
  <span class="living" :class="{timer:!this.flag}">{{time}}</span>
</template>
<script>
  export default {
    data() {
      return {
        time: '',
        flag: false
      }
    },
    mounted() {
      let time = setInterval(() => {
        if (this.flag === true) {
          clearInterval(time)
        }
        this.timeDown()
      }, 1000)
    },
    props: {
      endTime: {
        type: String,
        default: ''
      }
    },
    methods: {
      timeDown() {
        const endTime = this.endTime
        const nowTime = new Date()
        let leftTime = parseInt((endTime * 1000 - nowTime.getTime()) / 1000)
        let d = parseInt(leftTime / (24 * 60 * 60))
        let h = this.formate(parseInt(leftTime / (60 * 60) % 24))
        let m = this.formate(parseInt(leftTime / 60 % 60))
        let s = this.formate(parseInt(leftTime % 60))
        if (leftTime <= 0) {
          this.flag = true
          this.$emit('time-end')
          this.time = `直播中`
        } else {
          this.time = `倒计时：${d}天${h}小时${m}分${s}秒`
        }
      },
      formate(time) {
        if (time >= 10) {
          return time
        } else {
          return `0${time}`
        }
      }
    }
  }
</script>

