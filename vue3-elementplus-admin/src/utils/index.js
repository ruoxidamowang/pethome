import { _isObject } from './is'

/**
 * 获取 url 参数
 * @param {string} url
 * @returns {Object}
 */
export function getQueryObject(url) {
  url = url == null ? window.location.href : url
  const search = url.substring(url.lastIndexOf('?') + 1)
  const obj = {}
  const reg = /([^?&=]+)=([^?&=]*)/g
  search.replace(reg, (rs, $1, $2) => {
    const name = decodeURIComponent($1)
    let val = decodeURIComponent($2)
    val = String(val)
    obj[name] = val
    return rs
  })
  return obj
}

/**
 * 复制数组
 * @param {Array} actual
 * @returns {Array}
 */
export function cleanArray(actual) {
  const newArray = []
  for (let i = 0; i < actual.length; i++) {
    if (actual[i]) {
      newArray.push(actual[i])
    }
  }
  return newArray
}

/**
 * 对象转url参数
 * @param {Object} json
 * @returns {Array}
 */
export function param(json) {
  if (!json) return ''
  return cleanArray(
    Object.keys(json).map((key) => {
      if (json[key] === undefined) return ''
      return encodeURIComponent(key) + '=' + encodeURIComponent(json[key])
    })
  ).join('&')
}

/**
 * @param {string} url
 * @returns {Object}
 */
export function param2Obj(url) {
  const search = decodeURIComponent(url.split('?')[1]).replace(/\+/g, ' ')
  if (!search) {
    return {}
  }
  const obj = {}
  const searchArr = search.split('&')
  searchArr.forEach((v) => {
    const index = v.indexOf('=')
    if (index !== -1) {
      const name = v.substring(0, index)
      const val = v.substring(index + 1, v.length)
      obj[name] = val
    }
  })
  return obj
}

/**
 * @param {string} val
 * @returns {string}
 */
export function html2Text(val) {
  const div = document.createElement('div')
  div.innerHTML = val
  return div.textContent || div.innerText
}

/**
 * Merges two objects, giving the last one precedence
 * @param {Object} target
 * @param {(Object|Array)} source
 * @returns {Object}
 */
export function objectMerge(target, source) {
  if (typeof target !== 'object') {
    target = {}
  }
  if (Array.isArray(source)) {
    return source.slice()
  }
  Object.keys(source).forEach((property) => {
    const sourceProperty = source[property]
    if (typeof sourceProperty === 'object') {
      target[property] = objectMerge(target[property], sourceProperty)
    } else {
      target[property] = sourceProperty
    }
  })
  return target
}

/**
 * @param {string} type
 * @returns {Date}
 */
export function getTime(type) {
  if (type === 'start') {
    return new Date().getTime() - 3600 * 1000 * 24 * 90
  } else {
    return new Date(new Date().toDateString())
  }
}

/**
 * @param {Function} func
 * @param {number} wait
 * @param {boolean} immediate
 * @description 不断触发一个函数后，执行第一次，只有大于设定的执行周期后才会执行第二次
 * @return {*}
 */
export function debounce(func, wait, immediate) {
  if (typeof func !== 'function')
    throw new TypeError('func must be a function!')
  if (typeof wait === 'undefined') {
    wait = 500
    immediate = false
  }
  if (typeof wait === 'boolean') {
    immediate = wait
    wait = 500
  }
  if (typeof immediate === 'undefined') {
    immediate = false
  }
  if (typeof wait !== 'number') throw new TypeError('wait must be a number!')
  if (typeof immediate !== 'boolean')
    throw new TypeError('immediate must be a boolean!')
  let timer = null,
    result
  return function proxy(...params) {
    let self = this,
      callNow = !timer && immediate
    if (timer) clearTimeout(timer)
    timer = setTimeout(() => {
      clearTimeout(timer)
      timer = null
      if (!immediate) result = func.apply(self, params)
    }, wait)
    if (callNow) result = func.apply(self, params)
    return result
  }
}

/**
 * @param {Function} func
 * @param {number} wait
 * @param {boolean} immediate
 * @description 首次触发，然后规定在一个单位时间内，只会触发一次函数
 * @return {*}
 */
export function throttle(method, wait, immediate) {
  let timeout
  let previous = 0
  return function(...args) {
    let context = this
    let now = new Date().getTime()
    // !previous代表首次触发或定时器触发后的首次触发，若不需要立即执行则将previous更新为now
    // 这样remaining = wait > 0，则不会立即执行，而是设定定时器
    if (!previous && immediate === false) previous = now
    let remaining = wait - (now - previous)
    if (remaining <= 0 || remaining > wait) {
      if (timeout) {
        clearTimeout(timeout)
        timeout = null
      }
      previous = now
      method.apply(context, args)
    } else if (!timeout) {
      timeout = setTimeout(() => {
        // 如果leading为false，则将previous设为0，
        // 下次触发时会与下次触发时的now同步，达到首次触发（对于用户来说）不立即执行
        // 如果直接设为当前时间戳，若停止触发一段时间，下次触发时的remaining为负值，会立即执行
        previous = immediate === false ? 0 : new Date().getTime()
        timeout = null
        method.apply(context, args)
      }, remaining)
    }
  }
}

/**
 * This is just a simple version of deep copy
 * Has a lot of edge cases bug
 * If you want to use a perfect deep copy, use lodash's _.cloneDeep
 * @param {Object} source
 * @returns {Object}
 */
export function deepClone(source) {
  if (!source && _isObject(source)) {
    throw new Error('error arguments', 'deepClone')
  }
  const targetObj = source.constructor === Array ? [] : {}
  Object.keys(source).forEach((keys) => {
    if (source[keys] && _isObject(source[keys])) {
      targetObj[keys] = deepClone(source[keys])
    } else {
      targetObj[keys] = source[keys]
    }
  })
  return targetObj
}

/**
 * 简单数组去重
 * @param {Array} arr
 * @returns {Array}
 */
export function uniqueArr(arr) {
  return Array.from(new Set(arr))
}

/**
 * 生成唯一字符串
 * @returns {string}
 */
export function createUniqueString() {
  const timestamp = +new Date() + ''
  const randomNum = parseInt((1 + Math.random()) * 65536) + ''
  return (+(randomNum + timestamp)).toString(32)
}

/**
 * @description 遍历 target 将object与target 对应值赋值
 */

export const mergeAssignment = function(target, object) {
  let ret = {}
  Object.keys(target).forEach((key) => {
    ret[key] = _isObject(object[key]) ? deepClone(object[key]) : object[key]
  })
  return ret
}

/**
 *
 * @description 遍历 target 将object与target 对应值赋值
 */

export const mergeRefProperty = function(target, object) {
  Object.keys(target).forEach((key) => {
    target[key] = _isObject(object[key]) ? deepClone(object[key]) : object[key]
  })
  return target
}

/**
 * 获取对象某个属性值
 * @param {Object} object
 * @param {(Array)} path
 * @param {(undefined)} defaultValue
 */

export const getObjVal = function(object, path = [], defaultValue) {
  if (!Array.isArray(path)) throw TypeError('path must is a Array')
  var baseGet = function(object, path) {
    var index = 0,
      length = path.length
    while (object != null && index < length) {
      object = object[toKey(path[index++])]
    }
    return index && index == length ? object : undefined
  }
  var toKey = function(value) {
    if (typeof value == 'string' || typeof value == 'symbol') {
      return value
    }
    var result = value + ''
    return result == '0' && 1 / value == -Infinity ? '-0' : result
  }
  var result = object == null ? undefined : baseGet(object, path)
  return result === undefined ? defaultValue : result
}
