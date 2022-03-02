import request from '@/utils/request'

export function loadAll(data) {
	return request({
		url: '/shop',
		method: 'post',
		data,
	})
}

export function findAll() {
	return request({
		url: '/shop',
		method: 'get',
	})
}


export function addOrEdit(data) {
	return request({
		url: '/shop',
		method: 'put',
		data,
	})
}

export function delShop(id,path) {
	return request({
		url: '/shop/delShop',
		method: 'delete',
		params: {
			id:id,
			path:path
		}
	})
}

export function delLogo(path) {
	return request({
		url: '/shop',
		method: 'delete',
		path
	})
}

export function removeAll(data) {
	return request({
		url: '/shop',
		method: 'delete',
		data
	})
}
