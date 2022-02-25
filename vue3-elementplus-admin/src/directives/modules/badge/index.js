import { createApp } from 'vue'

/**
 * v-badge 徽标指令
 * @desc
 * @example
 * @param {String} normal/dot 徽标形状normal为正常徽标；dot 仅为一个点	默认 normal
 * @param {String} success、error、info、warning 徽标形状normal为正常徽标；dot 仅为一个点 默认 error
 * @param {Number} number 徽标上显示的数字
 * ```vue
 * <div v-badge.dot.info="badgeCount" style="height:50px;width:50px;background:#999"> </div>
 * ```
 */
const SUCCESS = '#72c140'
const ERROR = '#ed5b56'
const WARNING = '#f0af41'
const INFO = '#4091f7'
const HEIGHT = 20
let flag = false

const badge = {
  name: 'badge',
  beforeMount(el, binding) {
    const { modifiers, value } = binding
    const modifiersKey = Object.keys(modifiers)
    let isDot = modifiersKey.includes('dot')
    let backgroundColor = ''
    if (modifiersKey.includes('success')) {
      backgroundColor = SUCCESS
    } else if (modifiersKey.includes('warning')) {
      backgroundColor = WARNING
    } else if (modifiersKey.includes('info')) {
      backgroundColor = INFO
    } else {
      backgroundColor = ERROR
    }

    const targetTemplate = isDot
      ? `<div style="position:absolute;top:-5px;right:-5px;height:10px;width:10px;border-radius:50%;background:${backgroundColor}"></div>`
      : `<div style="background:${backgroundColor};position:absolute;top:-${HEIGHT /
          2}px;right:-${HEIGHT /
          2}px;height:${HEIGHT}px;min-width:${HEIGHT}px;border-radius:${HEIGHT /
          2}px;text-align:center;line-height:${HEIGHT}px;color:#fff;padding:0 5px;">${value}</div>`

    el.style.position = el.style.position || 'relative'

    const badge = createApp({
      template: targetTemplate,
    })

    if (flag) {
      badge.unmount()
    }
    badge.mount(el)
    flag = true
  },
}

export default badge
