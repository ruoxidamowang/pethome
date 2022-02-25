/**
 * v-draggable 文字超出省略指令
 * @desc
 * @example
 * ```vue
 * <div v-ellipsis:100> 需要省略的文字是阿萨的副本阿萨的副本阿萨的副本阿萨的副本</div>
 * ```
 */
const ellipsis = {
  name: 'ellipsis',
  beforeMount(el, binding) {
    el.style.width = `${binding.arg || 100}px`
    el.style.whiteSpace = 'nowrap'
    el.style.overflow = 'hidden'
    el.style.textOverflow = 'ellipsis'
  },
}

export default ellipsis
