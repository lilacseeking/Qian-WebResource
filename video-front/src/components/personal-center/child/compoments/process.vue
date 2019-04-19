<template>
    <div> <div style="display:inline-block;"><span> 学习进度</span> </div><div style="display:inline-block;"><el-progress :percentage="70"></el-progress> </div> </div>
</template>

<script>
export default {
  name: 'process',
  props: {
    process: 30
  },
  data: function() {
    return {
    }
  }
}
</script>
<style>

</style>