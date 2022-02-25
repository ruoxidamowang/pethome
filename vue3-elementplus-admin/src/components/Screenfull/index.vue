<template>
  <div>
    <svg-icon
      :icon-class="isFullscreen ? 'exit-fullscreen' : 'fullscreen'"
      @click="click"
    />
  </div>
</template>

<script>
import screenfull from 'screenfull'
import { getCurrentInstance, onUnmounted, onMounted, ref } from 'vue'

export default {
  name: 'Screenfull',
  mounted() {
    this.init()
  },
  beforeUnmount() {},
  setup() {
    const isFullscreen = ref(false)
    const { proxy } = getCurrentInstance()

    onMounted(() => {
      init()
    })

    onUnmounted(() => {
      destroy()
    })

    function init() {
      if (screenfull.isEnabled) {
        screenfull.onchange(
          () => (isFullscreen.value = screenfull.isFullscreen)
        )
      }
    }

    function destroy() {
      if (screenfull.isEnabled) {
        screenfull.onchange(
          () => (isFullscreen.value = screenfull.isFullscreen)
        )
      }
    }

    function click() {
      if (!screenfull.isEnabled) {
        proxy.$message({
          message: '您的浏览器不支持全屏！',
          type: 'warning',
        })
        return false
      }
      screenfull.toggle()
    }

    return {
      isFullscreen,
      init,
      destroy,
      click,
    }
  },
}
</script>

<style scoped>
.screenfull-svg {
  display: inline-block;
  cursor: pointer;
  fill: #5a5e66;
  width: 20px;
  height: 20px;
  vertical-align: 10px;
}
</style>
