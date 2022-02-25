
<template>
  <span></span>
</template>
<script>
import { onBeforeMount } from 'vue'
export default {
  name: 'AuthRedirect',
  setup() {
    onBeforeMount(() => {
      const hash = window.location.search.slice(1)
      if (window.localStorage) {
        window.localStorage.setItem('x-admin-oauth-code', hash)
        window.close()
      }
    })
    return {}
  },
}
</script>
