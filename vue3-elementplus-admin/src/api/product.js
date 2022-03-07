import request from '@/utils/request'

export function loadAll(data) {
	return request({
		url: '/product',
		method: 'post',
		data,
	})
}

export function findAll() {
	return request({
		url: '/product',
		method: 'get',
	})
}


export function addOrEdit(data) {
	return request({
		url: '/product',
		method: 'put',
		data,
	})
}

export function delShop(id,path) {
	return request({
		url: '/product/delProduct',
		method: 'delete',
		params: {
			id:id,
			path:path
		}
	})
}

export function delLogo(path) {
	return request({
		url: '/product',
		method: 'delete',
		path
	})
}

export function removeAll(data) {
	return request({
		url: '/product',
		method: 'delete',
		data
	})
}
