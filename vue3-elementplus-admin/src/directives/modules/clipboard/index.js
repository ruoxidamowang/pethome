import { Message } from '@/components/Message'
import Clipboard from 'clipboard'

if (!Clipboard) {
  throw new Error('you should npm install `clipboard` --save at first ')
}

const clipboard = {
  name: 'clipboard',
  beforeMount(el, binding) {
    createdClipboard(el, binding.arg, binding.value)
  },
  updated(el, binding) {
    if (binding.arg === 'success') {
      el._v_clipboard_success = binding.value
    } else if (binding.arg === 'error') {
      el._v_clipboard_error = binding.value
    } else {
      el._v_clipboard.text = function() {
        return binding.value
      }
      el._v_clipboard.action = function() {
        return 'copy'
      }
    }
  },
  unmounted(el, binding) {
    if (binding.arg === 'success') {
      delete el._v_clipboard_success
    } else if (binding.arg === 'error') {
      delete el._v_clipboard_error
    } else {
      el._v_clipboard.destroy()
      delete el._v_clipboard
    }
  },
}

function createdClipboard(el, arg, value) {
  if (arg === 'success') {
    el._v_clipboard_success = value
  } else if (arg === 'error') {
    el._v_clipboard_error = value
  } else {
    const clipboard = new Clipboard(el, {
      text() {
        return value
      },
      action() {
        return 'copy'
      },
    })
    clipboard.on('success', (e) => {
      const callback = el._v_clipboard_success
      if (callback) {
        callback(e)
      } else {
        Message.success('复制成功')
      }
    })
    clipboard.on('error', (e) => {
      const callback = el._v_clipboard_error
      if (callback) {
        callback(e)
      } else {
        Message.success('复制失败')
      }
    })
    el._v_clipboard = clipboard
  }
}

export default clipboard
