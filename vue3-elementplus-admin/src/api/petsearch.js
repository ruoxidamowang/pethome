import request from '@/utils/request'

export function loadAll(data) {
	return request({
		url: '/petSearch',
		method: 'post',
		data,
	})
}

export function findAll() {
	return request({
		url: '/petSearch',
		method: 'get',
	})
}

export function addOrEdit(data) {
	return request({
		url: '/petSearch',
		method: 'put',
		data,
	})
}

export function remove(id) {
	return request({
		url: `/petSearch/${id}`,
		method: 'delete',
	})
}

export function removeAll(data) {
	return request({
		url: '/petSearch',
		method: 'delete',
		data
	})
}
