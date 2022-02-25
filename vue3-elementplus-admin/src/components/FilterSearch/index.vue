<!--
        列表、表格筛选组件
-->
<template>
	<div class="filter-search-content">
		<el-form :inline="true" class="demo-form-inline">
			<!-- 筛选选项列表 -->
			<el-form-item v-for="item in filtersOptions.filters" :key="item.prop" :label-width="item.width" :label="labelTitle(item.label)">
				<!-- 普通input框输入 -->
				<template v-if="item.inputType === 'input'">
					<el-input
						v-model="propsParams.params[item.prop]"
						v-bind="item.attrs"
						:clearable="isClearable(item.clearable)"
						:placeholder="item.placeholder || '请输入' + item.label"
						size="medium"
					></el-input>
				</template>
				<!-- 选择框 -->
				<template v-if="item.inputType === 'select'">
					<el-select
						v-model="propsParams.params[item.prop]"
						:clearable="isClearable(item.clearable)"
						:placeholder="item.placeholder || '请选择' + item.label"
						filterable
						size="medium"
					>
						<el-option v-for="item in item.options" :key="item.value" :label="item.label" :value="item.value"></el-option>
					</el-select>
				</template>
				<!-- 级联选择  value,label 必传-->
				<template v-if="item.inputType === 'cascader'">
					<el-cascader
						v-model="propsParams.params[item.prop]"
						:placeholder="item.placeholder || '请选择' + item.label"
						:options="item.options"
						:props="{ checkStrictly: true, value: item.props.value, label: item.props.label }"
						clearable
					></el-cascader>
				</template>
				<!-- 日期选择 -->
				<template v-if="item.inputType === 'datePicker'">
					<el-date-picker
						v-model="propsParams.params[item.prop]"
						:clearable="isClearable(item.clearable)"
						:type="item.type || 'date'"
						:range-separator="item['rangeSeparator']"
						:start-placeholder="item['startPlaceholder']"
						:end-placeholder="item['endPlaceholder']"
						:placeholder="item.placeholder || '请选择' + item.label"
						:format="item.format"
						size="medium"
					/>
					<!-- :class="['input-width', ...item.class]"-->
				</template>
			</el-form-item>
			<slot></slot>
			<!-- 搜索按钮 -->
			<el-form-item>
				<el-button
					@click="handelClick({ type: 'query', _id: btn_id })"
					class="query-btn"
					v-if="!btnProps.hiddenQueryBtn"
					:size="btnProps.queryBtnSize || 'medium'"
					:class="btnProps.queryBtnClass"
					:type="btnProps.queryBtnType || 'primary'"
					:style="btnProps.queryBtnStyle"
				>
					{{ btnProps.queryBtnLabel || '搜索' }}
				</el-button>
			</el-form-item>
			<!-- 其它按钮列表 -->
			<template v-if="otherBtns.length > 0">
				<el-form-item v-for="item in otherBtns" :key="item.id">
					<el-button
						v-if="!item.hidden"
						@click="handelClick(item)"
						:class="item.class"
						:size="item.size"
						:type="item.type"
						:icon="item.icon"
						v-bind="item.attrs"
						:style="item.style"
					>
						{{ item.label }}
					</el-button>
				</el-form-item>
			</template>
		</el-form>
	</div>
</template>

<script>
import { reactive, watch } from 'vue'
// 验证提示 是否存在prop属性，并且设置prop 和 value
const handelVerificationProp = data => {
	const result = {}
	if (!data || !Array.isArray(data) || data.length < 1) {
		console.warn('filtersOptions参数错误')
		return
	}
	data.map(({ prop, value, defaultValue }, index) => {
		if (!prop) console.warn(`${data[index].label}属性值没有prop参数`)
		if (defaultValue) {
			result[prop] = defaultValue
		} else {
			result[prop] = value || ''
		}
	})
	console.log(result)
	return JSON.parse(JSON.stringify(result))
}
export default {
	setup(props, { emit }) {
		const btn_id = 'dhsdhjsauwq___saaa_-sdc'
		const filtersOptions = reactive({ filters: props.filtersList })
		const resetValue = reactive({ reses: props.resetValue })
		const watchDeep = { deep: true }
		const propsParams = reactive({ params: {} })
		propsParams.params = handelVerificationProp(filtersOptions.filters)
		// 监听传入数据变化
		watch(
			() => filtersOptions.filters,
			() => {
				propsParams.params = handelVerificationProp(filtersOptions.filters)
			},
			watchDeep
		)
		// 重置需要的数据
		watch(
			() => resetValue.reses,
			newVal => {
				for (const key in newVal) {
					propsParams.params[key] = newVal[key] || ''
				}
			},
			watchDeep
		)

		const labelTitle = label => {
			if (props.colonSuffix) {
				const str = label.toString()
				const endStr = str.slice(str.length - 1)

				if (endStr === ':' || endStr === '：') {
					return label
				}
				return str + '：'
			} else {
				return label
			}
		}

		// 按钮事件
		const handelClick = item => {
			if (item && item._id === btn_id) {
				// 搜索
				emit('handelClick', { btnType: 'query', query: propsParams.params })
				return
			} else {
				// 存在 emit 属性
				if (item.emit) return emit(item.emit, { ...item, query: propsParams.params })
				// 其他按钮
				emit('handelClick', { ...item, query: propsParams.params })
			}
		}

		const isClearable = clearable => {
			if (clearable === true || clearable === undefined) return true
			return false
		}

		// 设置Placeholder
		function setPlaceholder(placeholder, label, t) {
			if (placeholder) {
				return placeholder
			}
			let p = ''
			if (t === 'i') p = '请输入'
			if (t === 's') p = '请选择'
			if (label) {
				if (typeof label === 'string') {
					const idx1 = label.lastIndexOf('：')
					const idx2 = label.lastIndexOf(':')
					if (idx1 > -1 && idx2 > -1) {
						p += label
						return p
					}
					let sIndex = Math.max(idx1, idx2)
					p += label.slice(0, sIndex)
					return p
				}
			}
		}

		const otherBtns = props.btnProps.list ? JSON.parse(JSON.stringify(props.btnProps.list)) : []
		console.log('props.btnProps.list', props.btnProps.list)

		return {
			btn_id,
			filtersOptions,
			labelTitle,
			propsParams,
			handelClick,
			isClearable,
			setPlaceholder,
			otherBtns
		}
	},
	props: {
		// label是否添加冒号后缀
		colonSuffix: {
			type: Boolean,
			default: true
		},
		//筛选项配置
		filtersList: {
			type: Array,
			default: () => []
		},
		// 按钮配置
		btnProps: {
			type: Object,
			default: () => ({
				hiddenQueryBtn: false,
				queryBtnLabel: '搜索',
				queryBtnClass: [],
				queryBtnSize: 'medium',
				queryBtnType: '',
				list: []
			})
		},
		resetValue: {
			// 重置输入选中的内容
			type: Object,
			default: () => null
		}
	},
	name: 'FilterSearch'
}
</script>

<style lang="scss" scoped>
.filter-search-content {
	::v-deep .el-form-item {
		margin-bottom: 0;
		margin-right: 15px;
	}
	::v-deep .el-form-item__label {
		padding: 0;
		color: #666;
		font-weight: normal;
	}
	::v-deep .el-form-item__content {
		.el-input {
			width: 200px;
		}
	}
	::v-deep input::-webkit-outer-spin-button,
	::v-deep input::-webkit-inner-spin-button {
		-webkit-appearance: none !important;
	}

	::v-deep input[type='number'] {
		-moz-appearance: textfield;
	}
}
</style>
