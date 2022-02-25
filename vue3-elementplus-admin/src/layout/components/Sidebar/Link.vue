<template>
  <component :is="type" v-bind="linkProps">
    <slot />
  </component>
</template>

<script>
import { computed } from 'vue'
import { isExternal } from '@/utils/validate'

export default {
  props: {
    to: {
      type: String,
      required: true,
    },
  },
  setup(props) {
    const isExternalVal = computed(() => isExternal(props.to))
    const type = computed(() => (isExternal.value ? 'a' : 'router-link'))
    const linkProps = computed(() => {
      return isExternalVal.value
        ? {
            href: props.to,
            target: '_blank',
            rel: 'noopener',
          }
        : {
            to: props.to,
          }
    })
    return {
      type,
      linkProps,
    }
  },
}
</script>
