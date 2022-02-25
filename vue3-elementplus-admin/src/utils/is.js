// 判断数据类型函数
function toRawType(value) {
	return Object.prototype.toString.call(value).slice(8, -1)
}

/** 字符串 类型
 * @param {string} str
 * @returns {Boolean}
 */
export function _isString(str) {
	if (typeof str === 'string' || str instanceof String) {
		return true
	}
	return false
}

/** null 类型
 * @param {null} arg
 * @returns {Boolean}
 */
export function _isNull(arg) {
	return toRawType(arg) === 'Null'
}

/** undefined 类型
 * @param {undefined} arg
 * @returns {Boolean}
 */
export function _isUndefined(arg) {
	return arg === void 0
}

/** boolean 类型
 * @param {boolean} arg
 * @returns {Boolean}
 */
export function _isBoolean(arg) {
	return toRawType(arg) === 'Boolean'
}

/** function 类型
 * @param {function} arg
 * @returns {Boolean}
 */
export function _isFunction(arg) {
	return toRawType(arg) === 'Function'
}

/** array 类型
 * @param {Array} arg
 * @returns {Boolean}
 */
export function _isArray(arg) {
	if (typeof Array.isArray === 'undefined') return toRawType(arg) === 'Array'
	return Array.isArray(arg)
}

/** object 类型
 * @param { object } arg
 * @returns {Boolean}
 */
export function _isObject(arg) {
	return toRawType(arg) === 'Object'
}

/** regExp 类型
 * @param { regExp } arg
 * @returns {Boolean}
 */
export function _isRegExp(arg) {
	return toRawType(arg) === 'RegExp'
}

/** symbol 类型
 * @param { symbol } arg
 * @returns {Boolean}
 */
export function _isSymbol(arg) {
	return toRawType(arg) === 'Symbol'
}

/** date 类型
 * @param { date } arg
 * @returns {Boolean}
 */
export function _isDate(arg) {
	return toRawType(arg) === 'Date'
}

/** 空对象 {},[],null,及其它非对象数据
 * @param { obj } arg
 * @returns {Boolean}
 */
export function _isEmptyObject(arg) {
	for (const name in arg) {
		return false
	}
	return true
}
