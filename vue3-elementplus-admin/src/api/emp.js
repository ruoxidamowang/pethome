import request from '@/utils/request'

export function loadAll(data) {
	return request({
		url: '/employee',
		method: 'post',
		data,
	})
}

export function loadAllRole() {
	return request({
		url: '/role',
		method: 'get',
	})
}

export function changeRole(data){
	return request({
		url: '/employee',
		method: 'put',
		data,
	})
}

export function remove(id) {
	return request({
		url: `/employee/${id}`,
		method: 'delete',
	})
}

export function removeAll(data) {
	return request({
		url: '/employee',
		method: 'delete',
		data
	})
}
