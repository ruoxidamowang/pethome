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

export function remove(id) {
	return request({
		url: `/shop/${id}`,
		method: 'delete',
	})
}

export function removeAll(data) {
	return request({
		url: '/shop',
		method: 'delete',
		data
	})
}
