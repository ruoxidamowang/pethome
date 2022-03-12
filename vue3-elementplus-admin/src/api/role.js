import request from '@/utils/request'

export function loadAll(data) {
  return request({
    url: '/role',
    method: 'post',
    data,
  })
}

export function addOrEdit(data) {
  return request({
    url: '/role',
    method: 'put',
    data,
  })
}

export function remove(id) {
  return request({
    url: `/role/${id}`,
    method: 'delete',
  })
}
