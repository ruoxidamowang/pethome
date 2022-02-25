<template>
	<div></div>
</template>
<script>
import { defineComponent, unref } from 'vue'
import { useRouter } from 'vue-router'

export default defineComponent({
	name: 'Redirect',
	setup() {
		const { currentRoute, replace } = useRouter()

		const { params, query } = unref(currentRoute)
		const { path } = params
		// 重定向路劲
		const _path = Array.isArray(path) ? path.join('/') : path

		replace({
			path: _path ? '/' + _path : '/',
			query
		})

		return {}
	}
})
</script>
