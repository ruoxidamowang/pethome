<template>
	<el-table-column :align="align" :label="label" :width="width">
		<template #default="{ $index }">{{ $index + 1 }}</template>
	</el-table-column>
</template>

<script>
export default {
	name: 'CheckBoxColumn',
	props: {
		align: {
			type: String,
			default: 'center'
		},
		label: {
			type: String,
			default: '序号'
		},
		width: {
			// 宽度
			type: [Number, String],
			default: 50
		}
	}
}
</script>

<style></style>
