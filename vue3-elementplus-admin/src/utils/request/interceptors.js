export const requestInterceptors = requestHook => config => {
	requestHook && requestHook(config)
	return config
}

export const requestInterceptorsCatch = requestCatchHook => error => {
	requestCatchHook && requestCatchHook(error)
	return Promise.reject(error)
}

export const responseInterceptors = responseHook => response => {
	const res = response.data
	if (response.config.responseType == 'blob') {
		return Promise.resolve(response)
	} else {
		if (res === '' || res === void 0 || res === null) {
			console.log(response)
			$error(`Error：${response.config.url} response.data does not exist ！`)
			return res
		} else if (res.code == 1 || res.code === 20000) {
			// 正确响应
			res.data === null && console.log(response)
			return Promise.resolve(res)
		} else if (res.code == 5001 || res.code === 50008 || res.code === 50012 || res.code === 50014) {
			// 过期
			return responseHook && responseHook(response)
		}
		$error(res.msg || res.message || 'unknown error --- ')
		return Promise.reject(res)
	}
}

export const responseInterceptorsCatch = responseCatchHook => error => {
	if (error && error.response) {
		switch (error.response.status) {
			case 400:
				error.message = '请求错误(400)'
				break
			case 401:
				error.message = '未授权，请重新登录(401)'
				break
			case 403:
				if (error.response.data && error.response.data.code === 5001) {
					error.message = error.response.data.msg
				} else {
					error.message = '拒绝访问(403)'
				}
				break
			case 404:
				error.message = '请求出错(404)'
				break
			case 408:
				error.message = '请求超时(408)'
				break
			case 500:
				error.message = '服务器错误(500)'
				break
			case 501:
				error.message = '服务未实现(501)'
				break
			case 502:
				error.message = '网络错误(502)'
				break
			case 503:
				error.message = '服务不可用(503)'
				break
			case 504:
				error.message = '网络超时(504)'
				break
			case 505:
				error.message = 'HTTP版本不受支持(505)'
				break
			default:
				error.message = `连接出错(${error.response.status})!`
		}
	}

	if (error.message && error.message.indexOf('timeout') !== -1) {
		error.message = '请求超时'
	}

	if (error.response.data && error.response.data.code === 5001) {
		// 过期
		return responseCatchHook && responseCatchHook(error)
	}

	error.message && $error(error.message)

	return Promise.reject({ ...error.response })
}
