<template>
	<!-- 图片以及预览 -->
	<div v-if="colItem.colType === 'image'">
		<el-image style="width: 50px; height: 50px" :src="src" :preview-src-list="previewSrcList">
			<template #error>
				<div style="color: #5584ff">无</div>
			</template>
		</el-image>
	</div>
	<!-- 列按钮/html  -->
	<div v-else-if="colItem.colType === 'html'">
		<div style="cursor: pointer;" @click="handleClick(colItem.method)" v-html="htmlContent"></div>
	</div>
	<!-- 格式化 -->
	<div v-else-if="isFunction(colItem.format)">
		{{ colItem.format(row[colItem.prop], row, colItem) }}
	</div>
	<!-- prop 默认/数组 -->
	<div v-else @click="colItem.method && handleClick(colItem.method)">
		{{ isArray(colItem.prop) ? propFilter(colItem.prop, row) : row[colItem.prop] }}
	</div>
</template>

<script>
import { isFunction, isArray } from '@/utils/validate'
export default {
	name: 'TextColumn',
	props: {
		row: {
			type: Object,
			default: () => {}
		},
		colItem: {
			type: Object,
			default: () => {}
		},
		src: {
			// 图片地址
			type: String,
			default: ''
		},
		previewList: {
			// 预览图片地址列表
			type: Array,
			default: () => []
		}
	},
	computed: {
		previewSrcList() {
			//默认图片预览
			return this.previewList.length > 0 ? this.previewList : [this.src]
		},
		htmlContent() {
			// html 内容
			if (this.colItem.render) return this.colItem.render(this.row)
			return ''
		}
	},
	emits: ['handleClick'],
	methods: {
		isFunction,
		isArray,
		handleClick(method) {
			this.$emit('handleClick')
		},
		/**
		 * prop 单值 或者 数组过滤(此处为针对时间组，不作为通用处理)
		 */
		propFilter(prop, row) {
			let res = prop.reduce((total, cur) => {
				if (row[cur]) {
					return (total += row[cur] + '~')
				} else {
					return ''
				}
			}, '')
			console.log(res)
			return res ? res.replace(/~$/, '') : ''
		}
	}
}
</script>

<style></style>
