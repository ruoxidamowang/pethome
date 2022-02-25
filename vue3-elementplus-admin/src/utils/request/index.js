import axios from 'axios'
import store from '@/store'
import {
	requestInterceptors,
	requestInterceptorsCatch,
	responseInterceptors,
	responseInterceptorsCatch
} from './interceptors'
import { getPromise, postPromise, exportPromise, uploadPromise } from './requestPromise'
import { getToken } from '@/utils/auth'

const { devMockURL } = require('@/api')

const service = axios.create({
	// baseURL: devMockURL,
	baseURL: "http://localhost:8080",
	timeout: 1000, // request timeout
	headers: {
		'Content-Type': 'application/json;charset=UTF-8'
	}
})

// 请求前的处理
const requestHook = function(config) {
	const token = getToken()
	if (token) {
		// 请求头自定义密钥
		config.headers['X-Token'] = token
	}
}
// 请求前的错误处理
const requestCatchHook = function(error) {
	console.log(error) // for debug
}

//过期回调
const responseExpireHook = function() {
	store.dispatch('user/resetToken').then(() => {
		location.reload()
	})
}

// request interceptor
service.interceptors.request.use(
	requestInterceptors(requestHook),
	requestInterceptorsCatch(requestCatchHook)
)

// response interceptor
service.interceptors.response.use(
	responseInterceptors(responseExpireHook),
	responseInterceptorsCatch(responseExpireHook)
)

//文件上传请求头
export const uploadHeaders = {
	Authorization: getToken()
}

export const get = (url, params) => getPromise(service, url, params) // get 请求
export const post = (url, params) => postPromise(service, url, params) // post 请求
export const exportExcel = (url, params) => exportPromise(service, url, params) // 导出
export const upload = (url, files, config) => uploadPromise(service, url, files, config) // 上传

export default service
