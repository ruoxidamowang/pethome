<template>
	<el-table-column :width="columnAttrs.width" :label="columnAttrs.label" :fixed="columnAttrs.fixed" align="center">
		<template #default="{ row }" v-if="btns.length > 0">
			<el-button
				v-for="(item, index) in btns"
				:key="index"
				v-show="!isHideBtn(item, row)"
				v-bind="{ ...item.props }"
				:style="{ ...item.style }"
				:disabled="isDisabledBtn(item, row)"
				@click="handleTableButton(item.method, row, index)"
				:class="['btn-' + item.method, 'btn-right']"
				size="mini"
			>
				<template v-if="item.render">
					<div v-html="item.render(row)"></div>
				</template>
				{{ !!item.label ? item.label : '' }}
			</el-button>
		</template>
	</el-table-column>
</template>

<script>
export default {
	name: 'OptBttonsColumn',
	props: {
		columnAttrs: {
			// 操作列属性
			type: Object,
			default: () => ({
				width: '',
				label: '',
				fixed: ''
			})
		},
		btns: {
			// 操作列按钮
			type: Array,
			default: () => []
		}
	},
	computed: {},
	emits: ['handleTableButton'],
	methods: {
		isHideBtn(item, row) {
			if (this.isFunction(item.hidden)) return item.hidden(row) || false
			if (!item.hidden) return item.hidden
		},
		isDisabledBtn(item, row) {
			if (this.isFunction(item.disabled)) return item.disabled(row) || false
			if (!item.disabled) return item.disabled
		},
		// 按钮事件
		handleTableButton(method, row, index) {
			this.$emit('handleTableButton', { method, row, index })
		},
		isFunction(arg) {
			return Object.prototype.toString.call(arg) === '[object Function]'
		}
	}
}
</script>

<style></style>
