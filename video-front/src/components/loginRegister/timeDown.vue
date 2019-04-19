<template>
    <div class="timer" v-html="message"></div>
</template>

<script>
export default {
  name: 'timeDown',
  data() {
    return {
      message: '',
      count: 0
    }
  },
  props: ['seconds', 'type'],
  mounted() {
    this.count = this.seconds
    this.run()
  },
  methods: {
    run() {
      this.count--
      this.timerString(this.count)
      if (this.count > 0) {
        setTimeout(this.run, 1000)
      } else {
        this.$emit('end')
      }
    },
    timerString(sec) {
      var html = ''
      var day = this.toDouble(Math.floor(sec / 86400))
      var hour = this.toDouble(Math.floor((sec % 86400) / 3600))
      var min = this.toDouble(Math.floor((sec % 3600) / 60))
      var s = this.toDouble(Math.floor((sec % 3600) % 60))
      if (day > 0) {
        html += '<span>' + day + '</span>天'
      }
      if (this.type === 'zn') {
        html += '<span>' + hour + '</span>时<span>' + min + '</span>分<span>' + s + '</span>秒'
      } else if (this.type === 'sec') {
        html += '<span>' + s + '</span>'
      } else {
        html += '<span>' + hour + '</span>:<span>' + min + '</span>:<span>' + s + '</span>'
      }
      this.message = html
    },
    toDouble(number) {
      if (number < 10) {
        number = '0' + number
      }
      return number
    }
  }
}
</script>

<style lang="scss">
.timer{
    display: inline;
}
</style>