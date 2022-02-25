<template>
  <el-scrollbar
    ref="scrollContainer"
    :vertical="false"
    class="scroll-container"
    @wheel.prevent="handleScroll"
  >
    <slot />
  </el-scrollbar>
</template>

<script>
import {
  ref,
  computed,
  onMounted,
  onBeforeUnmount,
  getCurrentInstance,
} from 'vue'
const tagAndTagSpacing = 4 // tagAndTagSpacing

export default {
  name: 'ScrollPane',
  emits: ['scroll'],
  setup(props, { emit }) {
    const left = ref(0)
    const scrollContainer = ref(null)
    const scrollWrapper = computed(() => scrollContainer.value.wrap)
    const { proxy } = getCurrentInstance()

    onMounted(() => {
      scrollWrapper.value.addEventListener('scroll', () => emit('scroll'), true)
    })

    onBeforeUnmount(() => {
      scrollWrapper.value.removeEventListener('scroll', () => emit('scroll'))
    })

    function handleScroll(e) {
      const eventDelta = e.wheelDelta || -e.deltaY * 40
      scrollWrapper.value.scrollLeft =
        scrollWrapper.value.scrollLeft + eventDelta / 4
    }

    function moveToTarget(currentTag) {
      const $container = proxy.$el
      const $containerWidth = $container.offsetWidth

      const tagList = proxy.$parent.itemRefs

      let firstTag = null
      let lastTag = null

      // find first tag and last tag
      if (tagList.length > 0) {
        firstTag = tagList[0]
        lastTag = tagList[tagList.length - 1]
      }

      if (firstTag === currentTag) {
        scrollWrapper.value.scrollLeft = 0
      } else if (lastTag === currentTag) {
        scrollWrapper.value.scrollLeft =
          scrollWrapper.value.scrollWidth - $containerWidth
      } else {
        // find preTag and nextTag
        const currentIndex = tagList.findIndex((item) => item === currentTag)
        const prevTag = tagList[currentIndex - 1]
        const nextTag = tagList[currentIndex + 1]
        
        // the tag's offsetLeft after of nextTag
        const afterNextTagOffsetLeft =
          nextTag.$el.offsetLeft + nextTag.$el.offsetWidth + tagAndTagSpacing

        // the tag's offsetLeft before of prevTag
        const beforePrevTagOffsetLeft =
          prevTag.$el.offsetLeft - tagAndTagSpacing

        if (
          afterNextTagOffsetLeft >
          scrollWrapper.value.scrollLeft + $containerWidth
        ) {
          scrollWrapper.value.scrollLeft =
            afterNextTagOffsetLeft - $containerWidth
        } else if (beforePrevTagOffsetLeft < scrollWrapper.value.scrollLeft) {
          scrollWrapper.value.scrollLeft = beforePrevTagOffsetLeft
        }
      }
    }

    return {
      left,
      scrollWrapper,
      scrollContainer,
      handleScroll,
      moveToTarget,
    }
  },
}
</script>

<style lang="scss" scoped>
.scroll-container {
  white-space: nowrap;
  position: relative;
  overflow: hidden;
  width: 100%;
  ::v-deep(.el-scrollbar__bar) {
    bottom: 0px;
  }
  ::v-deep(.el-scrollbar__wrap) {
    height: 49px;
  }
}
</style>
