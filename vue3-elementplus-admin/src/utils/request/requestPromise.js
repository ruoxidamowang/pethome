// post
export const postPromise = (service, url, params) =>
	new Promise((resolve, reject) => {
		service
			.post(url, params)
			.then(res => {
				resolve(res)
			})
			.catch(err => {
				reject(err)
			})
	})

// get
export const getPromise = (service, url, params) =>
	new Promise((resolve, reject) => {
		service
			.get(url, { params })
			.then(res => {
				resolve(res)
			})
			.catch(err => {
				reject(err)
			})
	})

// 导出
export const exportPromise = (service, url, params) =>
	new Promise((resolve, reject) => {
		service({
			method: 'get',
			url: url,
			params,
			responseType: 'blob'
		})
			.then(response => {
				const res = response.data
				const filename = decodeURI(
					response.headers['content-disposition'].split(';')[1].split('filename=')[1]
				)
				resolve({ res, filename })
			})
			.catch(err => {
				reject(err)
			})
	})

// 上传
export const uploadPromise = (service, url, files, config) =>
	new Promise((resolve, reject) => {
		service
			.post(url, files, {
				'Content-Type': 'multipart/form-data',
				...config
			})
			.then(res => {
				resolve(res.data)
			})
			.catch(err => reject(err))
	})
