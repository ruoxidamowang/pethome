import { debounce, throttle } from '@/utils'

const debounceAndThrottle = (name) => ({
  name: 'debounceAndThrottle',
  beforeMount(el, binding) {
    let {
      func,
      wait = 300,
      immediate = true,
      params = [],
      event = 'click',
    } = binding.value

    const handle = name === 'debounce' ? debounce : throttle,
      proxy = function proxy(...args) {
        return func.call(this, ...params.concat(args))
      }

    el.$event = event
    el.$handle = handle(proxy, wait, immediate)
    el.addEventListener(el.$event, el.$handle)
  },
  unmounted(el) {
    el.removeEventListener(el.$event, el.$handle)
  },
})

export default debounceAndThrottle
