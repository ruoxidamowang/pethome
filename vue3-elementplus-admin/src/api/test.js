import request from '@/utils/request'

export function loadAll() {
	return request({
		url: '/loadAll',
		method: 'get',
	})
}
