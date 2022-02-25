import request from '@/utils/request'

export function loadAll(data) {
	return request({
		url: '/department',
		method: 'post',
		data,
	})
}

export function findAll() {
	return request({
		url: '/department',
		method: 'get',
	})
}

export function loadAllEmp() {
	return request({
		url: '/employee',
		method: 'get',
	})
}

export function addOrEdit(data) {
	return request({
		url: '/department',
		method: 'put',
		data,
	})
}

export function remove(id) {
	return request({
		url: `/department/${id}`,
		method: 'delete',
	})
}

export function removeAll(data) {
	return request({
		url: '/department',
		method: 'delete',
		data
	})
}
