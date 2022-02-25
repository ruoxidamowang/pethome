/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
	return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(str) {
	const valid_map = ['admin', 'editor', 'enterprise', 'project']
	return valid_map.indexOf(str.trim()) >= 0
}

/**
 * @param {string} url
 * @returns {Boolean}
 */
export function validateURL(url) {
	const urlregex = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
	return urlregex.test(url)
}

/**
 * @description 手机号
 * @param {string} str
 * @returns {Boolean}
 */
export function checkPhone(mobile) {
	const phone = /^[1][3-9][0-9]{9}$/
	if (phone.test(mobile)) {
		return true
	}
	return false
}

// 统一社会信用代码验证
export function checkSocialCreditCode(Code) {
	let patrn = /^[0-9A-Z]+$/
	//18位校验及大写校验
	if (Code.length != 18 || patrn.test(Code) == false) {
		console.log('不是有效的统一社会信用编码！')
		return false
	} else {
		let Ancode //统一社会信用代码的每一个值
		let Ancodevalue //统一社会信用代码每一个值的权重
		let total = 0
		let weightedfactors = [1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28] //加权因子
		let str = '0123456789ABCDEFGHJKLMNPQRTUWXY'
		//不用I、O、S、V、Z
		for (let i = 0; i < Code.length - 1; i++) {
			Ancode = Code.substring(i, i + 1)
			Ancodevalue = str.indexOf(Ancode)
			total = total + Ancodevalue * weightedfactors[i]
			//权重与加权因子相乘之和
		}
		let logiccheckcode = 31 - (total % 31)
		if (logiccheckcode == 31) {
			logiccheckcode = 0
		}
		let Str = '0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,J,K,L,M,N,P,Q,R,T,U,W,X,Y'
		let Array_Str = Str.split(',')
		logiccheckcode = Array_Str[logiccheckcode]

		let checkcode = Code.substring(17, 18)
		if (logiccheckcode != checkcode) {
			console.log('不是有效的统一社会信用编码！')
			return false
		}
		return true
	}
}

/**
 * @description 判断是否是身份证号(第二代)
 * @param str
 * @returns {boolean}
 */
export function checkIdCard(str) {
	const reg = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/
	return reg.test(str)
}

// 附件校验
export function checkEnclosure(str) {
	const _t = str.toLowerCase()
	return /(.*)\.(jpg|gif|ico|jpeg|png|doc|docx|pdf|xls|xlsx|excel)$/.test(_t)
}

/**
 * 小写字母
 * @param {string} str
 * @returns {Boolean}
 */
export function validLowerCase(str) {
	const reg = /^[a-z]+$/
	return reg.test(str)
}

/**
 * 大写字母
 * @param {string} str
 * @returns {Boolean}
 */
export function validUpperCase(str) {
	const reg = /^[A-Z]+$/
	return reg.test(str)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validAlphabets(str) {
	const reg = /^[A-Za-z]+$/
	return reg.test(str)
}

/**
 * @param {string} email
 * @returns {Boolean}
 */
export function validEmail(email) {
	const reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
	return reg.test(email)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function isString(str) {
	if (typeof str === 'string' || str instanceof String) {
		return true
	}
	return false
}

/**
 * @param {Array} arg
 * @returns {Boolean}
 */
export function isArray(arg) {
	if (typeof Array.isArray === 'undefined') {
		return Object.prototype.toString.call(arg) === '[object Array]'
	}
	return Array.isArray(arg)
}

/**
 * @param {Array} arg
 * @returns {Boolean}
 */
export function isFunction(arg) {
	return Object.prototype.toString.call(arg) === '[object Function]'
}

// 图片格式验证 jpg,png.word.ppt.excel.pdf
export function validImage(str) {
	const _t = str.toLowerCase()
	return /(.*)\.(jpg|jpeg|png)$/.test(_t)
}

//邮箱验证
export function validateEMail(rule, value, callback) {
	const reg = /^([a-zA-Z0-9]+[-_\.]?)+@[a-zA-Z0-9]+\.[a-z]+$/
	if (value == '' || value == undefined || value == null) {
		callback()
	} else {
		if (!reg.test(value)) {
			callback(new Error('请输入正确的邮箱'))
		} else {
			callback()
		}
	}
}

// 验证内容是否包含英文数字以及下划线
export function isPassword(rule, value, callback) {
	const reg = /^[_a-zA-Z0-9]+$/
	if (value == '' || value == undefined || value == null) {
		callback()
	} else {
		if (!reg.test(value)) {
			callback(new Error('仅由英文字母，数字以及下划线组成'))
		} else {
			callback()
		}
	}
}

//自动检验数值的范围
export function checkMax20000(rule, value, callback) {
	if (value == '' || value == undefined || value == null) {
		callback()
	} else if (!Number(value)) {
		callback(new Error('请输入[1,20000]之间的数字'))
	} else if (value < 1 || value > 20000) {
		callback(new Error('请输入[1,20000]之间的数字'))
	} else {
		callback()
	}
}

// 验证是否1-99之间 正整数
export function isOneToNinetyNine(rule, value, callback) {
	if (!value) {
		return callback(new Error('输入不可以为空'))
	}
	setTimeout(() => {
		if (!Number(value)) {
			callback(new Error('请输入正整数'))
		} else {
			const re = /^[1-9][0-9]{0,1}$/
			const rsCheck = re.test(value)
			if (!rsCheck) {
				callback(new Error('请输入正整数，值为【1,99】'))
			} else {
				callback()
			}
		}
	}, 0)
}

// 验证是否正整数
export function isInteger(rule, value, callback) {
	if (!value) {
		return callback(new Error('输入不可以为空'))
	}
	setTimeout(() => {
		if (!Number(value)) {
			callback(new Error('请输入正整数'))
		} else {
			const re = /^[0-9]*[1-9][0-9]*$/
			const rsCheck = re.test(value)
			if (!rsCheck) {
				callback(new Error('请输入正整数'))
			} else {
				callback()
			}
		}
	}, 0)
}

// 两位小数验证
export function validateValidity(rule, value, callback) {
	if (!/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(value)) {
		callback(new Error('最多两位小数！！！'))
	} else {
		callback()
	}
}

// 密码校验
export const validatePsdReg = (rule, value, callback) => {
	if (!value) {
		return callback(new Error('请输入密码'))
	}
	if (!/^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$)([^\u4e00-\u9fa5\s]){6,20}$/.test(value)) {
		callback(
			new Error('请输入6-20位英文字母、数字或者符号（除空格），且字母、数字和标点符号至少包含两种')
		)
	} else {
		callback()
	}
}

// 身份证校验
export const ID = (rule, value, callback) => {
	if (!value) {
		return callback(new Error('身份证不能为空'))
	}
	if (!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value)) {
		callback(new Error('请输入正确的二代身份证号码'))
	} else {
		callback()
	}
}

//账号校验
export const validateCode = (rule, value, callback) => {
	if (!value) {
		return callback(new Error('请输入账号'))
	}
	if (!/^(?![0-9]*$)(?![a-zA-Z]*$)[a-zA-Z0-9]{6,20}$/.test(value)) {
		callback(new Error('账号必须为6-20位字母和数字组合'))
	} else {
		callback()
	}
}

// 手机号验证
export const validatePhone = (rule, value, callback) => {
	if (!value) {
		callback(new Error('输入不能为空'))
	} else {
		const phoneTest = /^1[3456789]\d{9}$/
		if (!phoneTest.test(value)) {
			callback(new Error('手机号格式错误'))
		} else {
			callback()
		}
	}
}

// 验证码验证
export const validUserCode = (rule, value, callback) => {
	if (!value) {
		callback(new Error('输入不能为空'))
	} else {
		if (value.length < 4) {
			callback(new Error('输入验证码长度不足4位'))
		} else {
			callback()
		}
	}
}

/**
 * @description 表单验证
 */
// 手机号验证
export const validateTel = (rule, value, callback) => {
	if (!value) {
		callback(new Error('手机号不能为空！'))
	} else if (!checkPhone(value)) {
		callback(new Error('请输入正确手机号！'))
	} else {
		callback()
	}
}
