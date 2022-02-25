<template>
	<div>
		<el-table ref="mytable" :data="tableData" v-bind="{ ...props }" style="width: 100%" :row-style="rowStyle" v-loading="loading">
			<el-table-column
				align="center"
				v-for="(item, index, key) in tableLabel"
				:item="item"
				:key="key"
				:index="index"
				:label="item.label"
				:show-overflow-tooltip="overflowText"
			>
				<template v-slot="scope">
					<el-input
						v-if="scope.row.edit && !notEditableColumn.includes(item.prop)"
						size="small"
						v-model="scope.row[item.prop]"
						:placeholder="'请输入' + item.label"
					></el-input>
					<span v-else>{{ scope.row[item.prop] }}</span>
				</template>
			</el-table-column>
			<el-table-column label="操作" align="center">
				<template v-slot="{ row, $index }">
					<el-button
						v-show="!btn.hidden"
						size="mini"
						v-bind="{ ...btn.props }"
						v-for="(btn, index) in row.options"
						:key="index"
						:style="{ ...btn.style }"
						:class="['btn-' + btn.methods, 'btn-right']"
						:disabled="btn.disabled"
						@click="handleTableButton(btn, $index, row.options, row)"
					>
						{{ !!btn.label ? btn.label : '' }}
					</el-button>
				</template>
			</el-table-column>
		</el-table>
	</div>
</template>

<script>
export default {
	name: 'InlineEditTable',
	methods: {
		handleTableButton(btn, index, options, row) {
			let saveBtn = options.find(e => e.methods === 'save')
			let editBtn = options.find(e => e.methods === 'edit')
			if (btn.methods === 'edit') {
				row.edit = true
				btn.hidden = true
				saveBtn.hidden = false
			} else if (btn.methods === 'save') {
				this.loading = true
				const saveStatus = () => this.handleSave(btn, row, editBtn)
				this.$emit('handleTableButton', { methods: btn.methods, row, callback: saveStatus })
			} else {
				this.$emit('handleTableButton', { methods: btn.methods, row })
			}
		},
		handleSave(item, row, editBtn) {
			row.edit = false
			item.hidden = true
			editBtn.hidden = false
			this.loading = false
		},
		initEditAttribute() {
			if (this.tableData.length > 0) {
				//添加编辑事件
				this.tableData.forEach(item => {
					this.table_data.push({
						...item,
						edit: false
					})
				})
			}
		}
	},
	mounted() {
		console.log(this.notEditableColumn.includes('idNo'), 5555)
	},
	data() {
		return {
			loading: false,
			is_edit: false,
			editIndex: -1,
			table_data: []
		}
	},
	watch: {
		tableData: {
			//深度监听，可监听到对象、数组的变化
			handler() {
				this.initEditAttribute()
			},
			deep: true //true 深度监听
		}
	},
	props: {
		overflowText: {
			//数据太多-存在性能问题
			type: Boolean,
			default: false
		},
		notEditableColumn: {
			type: Array,
			default: () => {
				return []
			}
		},
		props: {
			// 表格参数配置
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
			type: Object,
			default: () => {
				return {
					height: '45px'
				}
			}
		},
		tableLabel: {
			type: Array,
			default: () => {
				return []
			}
		},
		tableData: {
			type: Array,
			default: () => []
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
</style>
