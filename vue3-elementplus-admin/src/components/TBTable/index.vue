<!--
  表格组件
-->
<template>
	<div>
		<el-table
			ref="tableRef"
			v-loading="tableLoading"
			v-bind="{ ...props }"
			:data="tableList"
			style="width: 100%"
			:row-class-name="tabRowClassName"
			:row-style="rowStyle"
			:cell-class-name="cellClassName"
			@select="handleSelect"
			@selection-change="handleSelectionChange"
		>
			<!-- 单选框 -->
			<CheckBoxColumn v-if="showCheckBox"></CheckBoxColumn>
			<!-- 序号 -->
			<OrderColumn v-if="showOrder"></OrderColumn>
			<!-- 表格 -->
			<el-table-column
				v-for="item in tableHeader.filter(item => item.label)"
				:width="item.width ? item.width : ''"
				:key="item[keyId]"
				:align="!!item.align ? item.align : 'center'"
				:label="item.label"
				:show-overflow-tooltip="overflowText"
				:fixed="item.fixed"
				:prop="item.prop"
			>
				<template #default="{ row }">
					<ContentColumn :row="row" :colItem="item" :src="row[item.prop]" @handleClick="handleClickon(item.method, row)"></ContentColumn>
				</template>
			</el-table-column>
			<!-- 多个操作按钮  -->
			<OptBttonsColumn v-if="!!options" :columnAttrs="columnAttrs" :btns="options.children" @handleTableButton="handleTableButton"></OptBttonsColumn>
		</el-table>
		<!-- 分页 -->
		<Pagination
			:total="paginationInfo.totalCount"
			:page="paginationInfo.pageIndex"
			:limit="paginationInfo.pageSize"
			:pageSizes="paginationInfo.pageArr"
			@pagination="handleSizeChange"
		/>
	</div>
</template>

<script>
import CheckBoxColumn from './CheckBoxColumn'
import OrderColumn from './OrderColumn'
import ContentColumn from './ContentColumn'
import OptBttonsColumn from './OptBttonsColumn'
import { toRef, computed, ref } from 'vue'
import { isString, isArray, isFunction } from '@/utils/validate'
import { getObjVal } from '@/utils'

export default {
	name: 'TBTable',
	components: { CheckBoxColumn, OrderColumn, ContentColumn, OptBttonsColumn },
	props: {
		tableData: {
			type: Array
		},
		tableHeader: {
			// 表头
			type: Array,
			default: () => {
				return []
			}
		},
		options: {
			// 表格操作列按钮
			type: Object
		},
		showCheckBox: {
			// 是否显示表格复选框
			type: Boolean,
			default: false
		},
		showOrder: {
			// 是否显示表格序号
			type: Boolean,
			default: false
		},
		pageInfo: {
			// 配置分页
			type: Object,
			default: () => {
				return {
					pageIndex: 1,
					totalCount: 0,
					pageSize: 10,
					pageArr: []
				}
			}
		},
		showPagination: {
			// 是否隐藏 分页显示
			type: Boolean,
			default: true
		},
		overflowText: {
			// 是否文字过长隐藏
			type: Boolean,
			default: false
		},
		loading: {
			// loading 配置
			type: Boolean,
			default: false
		},
		keepSelection: {
			//暂无
			//仅对 type=selection 的列有效，类型为 Boolean，为 true 则会在数据更新之后保留之前选中的数据（需指定 table row-key）
			type: Boolean,
			default: false
		},
		keyId: {
			// 动态绑定 key 值
			type: String,
			default: 'id'
		},
		props: {
			// 表格其他参数配置
			type: Object,
			default: function() {
				return {
					'show-header': true, // 显示表头
					'highlight-current-row': true, // 是否要高亮当前行
					'tooltip-effect': 'dark', //
					'max-height': 'auto', // Table 的最大高度。合法的值为数字或者单位为 px 的高度。
					'empty-text': '没有数据', // 空数据显示状态
					'element-loading-text': '加载中', // loading 加载
					'header-cell-style': {
						background: '#F2F4F7',
						color: '#333',
						fontSize: '13px'
					}, // 表头样式
					border: false, // 是否带有纵向边框
					fit: true, // 列的宽度是否自撑开
					stripe: false // 是否显示斑马纹
				}
			}
		},
		rowStyle: {
			// 行的样式回调
			type: Object,
			default: () => {
				return {
					height: '45px'
				}
			}
		},
		requestFn: {
			//请求
			type: Function,
			default: () => {
				return []
			}
		},
		filterParams: {
			//请求参数
			type: Object,
			default: () => {}
		}
	},
	emits: ['handleTableButton', 'handleGetList', 'handleSelect', 'handleSelectionChange', 'handleSizeChange'],
	setup(props, { emit }) {
		const tableRef = ref(null)
		const customEmits = ref([])

		//表格操作列公共属性
		const columnAttrs = computed(() =>
			props.options
				? {
						width: props.options.width,
						label: props.options.label,
						fixed: props.options.fixed
				  }
				: null
		)

		// Table操作按钮事件
		const handleTableButton = ({ method, row, index }) => {
			emit('handleTableButton', { method, row, index })
		}

		// Table单元格事件
		const handleClickon = (method, row, index) => {
			if (!method) return console.warn('不存在 method 属性')
			if (!isString(method)) throw '方法名错误'
			// 数据操作
			customEmits.value.push(method)
			emit(method, { method, row, index })
		}

		// 分页变化事件
		const handleSizeChange = page => {
			emit('handleSizeChange', page)
		}

		// 获取列 id
		const getRowKeys = row => {
			return row.id
		}

		// 复选框变换事件
		const handleSelectionChange = selection => {
			emit('handleSelectionChange', selection)
		}

		// 复选框单选事件
		const handleSelect = (selection, row) => {
			emit('handleSelect', { selection, row })
		}

		//设定某一行为选中行
		const setCurrentRow = row => {
			tableRef.value.setCurrentRow(row)
		}

		// 表格列类名
		const tabRowClassName = ({ row, rowIndex }) => {
			let index = rowIndex + 1
			if (index % 2 == 0) {
				return 'even-row'
			} else {
				return 'odd-row'
			}
		}

		//单元格的类名
		const cellClassName = ({ row, column, rowIndex, columnIndex }) => {
			if (row.confirmTag === 2 && columnIndex < props.tableHeader.length) {
				return 'height_light_cell'
			} else {
				return ''
			}
		}

		//table 请求处理
		const tableDataHandler = props => {
			const { pageInfo, filterParams, requestFn } = props
			//默认请求参数
			const requestParams = computed(() => ({
				...pageInfo,
				...filterParams
			}))
			//请求函数
			// 可修改分页参数
			const paginationInfo = toRef(props, 'pageInfo')
			// 可修改table数据
			const tableList = toRef(props, 'tableData')
			const tableLoading = ref(false)
			// 请求
			const getTableData = (params = requestParams.value) => {
				return new Promise((resolve, reject) => {
					console.log('params---', params)
					tableLoading.value = true
					requestFn(params)
						.then(res => {
							tableLoading.value = false
							// const { items, total } = res.data
							// tableData.value = items
							// paginationInfo.value.totalCount = total
							// console.log('list:', res)
							resolve(res)
						})
						.catch(error => {
							tableLoading.value = false
							console.log('error:', error)
							reject(error)
						})
				})
			}
			return {
				requestParams,
				paginationInfo,
				requestFn,
				getTableData,
				tableList,
				tableLoading
			}
		}

		const { getTableData, requestParams, paginationInfo, tableList, tableLoading } = tableDataHandler(props)

		return {
			isArray,
			isFunction,
			tableRef,
			customEmits,
			columnAttrs,
			tableLoading,
			handleTableButton,
			handleClickon,
			handleSelectionChange,
			handleSizeChange,
			getRowKeys,
			setCurrentRow,
			handleSelect,
			tabRowClassName,
			cellClassName,
			paginationInfo,
			tableList,
			requestParams,
			getTableData
		}
	}
}
</script>

<style lang="scss" scope>
::v-deep .el-table__header,
::v-deep .el-table__body {
	margin: 0;
}

::v-deep .el-table::before {
	height: 0;
}

::v-deep .el-button {
	padding: 0;
	border: none;
	// margin-right: 10px;
}

::v-deep .el-button + .el-button {
	margin-left: 0;
}

.btn-see {
	color: #39a6ff;
}

.btn-handel {
	color: #41a4bd;
}

.btn-edit {
	color: #fcb0fb;
}

.btn-delete,
.btn-del {
	color: #fd9090;
}

.btn-revoke {
	color: #ffa913;
}

::v-deep .btn-right div {
	margin-right: 5px;
}

.btn-right div:empty {
	margin-right: 0px;
}

::v-deep .even-row {
	background: #f1fdff;
}

::v-deep .odd-row {
	background: #fff;
}

.el-table--border::after,
.el-table--group::after {
	width: 0;
}

::v-deep .el-table td,
th.is-leaf {
	border: none;
}

::v-deep .el-table__fixed-right::before,
.el-table__fixed::before {
	background-color: transparent;
}

.progress-line {
	.el-progress-bar__outer,
	.el-progress-bar__inner {
		border-radius: 0 !important;
	}
}
</style>
