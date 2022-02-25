<template>
	<el-table-column
		:width="width"
		type="selection"
		:reserve-selection="keepSelection"
		:align="align"
	></el-table-column>
</template>

<script>
export default {
	name: 'CheckBoxColumn',
	props: {
		keepSelection: {
			type: Boolean,
			default: false
		},
		width: {
			// 复选框 宽度
			type: [Number, String],
			default: 55
		},
		align: {
			type: String,
			default: 'center'
		}
	}
}
</script>

<style></style>
