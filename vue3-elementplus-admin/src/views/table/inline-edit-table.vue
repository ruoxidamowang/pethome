<template>
	<div>
		<InlineEditTable
			:tableData="table_data"
			:tableLabel="table_columns"
			@handleTableButton="handleTableButton"
		></InlineEditTable>
	</div>
</template>

<script>
export default {
	methods: {
		handleTableButton({ methods, row, callback }) {
			console.log(methods, row)
			setTimeout(() => {
				callback && callback()
				this.table_data = [
					{
						date: '2016-05-03',
						name: '王小虎',
						province: '上海',
						city: '普陀区',
						address: '上海市普陀区金沙江路 1518 弄',
						zip: 200333,
						sex: 18,
						options: [
							{
								label: '编辑',
								methods: 'edit',
								props: {
									type: 'text'
								},
								style: {
									color: '#1ebf0c'
								}
							},
							{
								label: '保存',
								hidden: true,
								methods: 'save',
								props: {
									type: 'text'
								},
								style: {
									color: '#1ebf0c'
								}
							},

							{
								label: '删除',
								methods: 'delete',
								props: {
									type: 'text'
								},
								style: {
									margin: '0 20px',
									color: '#f25656'
								}
							}
						]
					},
					{
						date: '2016-05-02',
						sex: 18,
						name: '王小虎',
						province: '上海',
						city: '普陀区',
						address: '上海市普陀区金沙江路 1518 弄',
						zip: 200333,
						options: [
							{
								label: '编辑',
								methods: 'edit',
								props: {
									type: 'text'
								},
								style: {
									color: '#1ebf0c'
								}
							},
							{
								label: '保存',
								hidden: true,
								methods: 'save',
								props: {
									type: 'text'
								},
								style: {
									color: '#1ebf0c'
								}
							},

							{
								label: '删除',
								methods: 'delete',
								props: {
									type: 'text'
								},
								style: {
									margin: '0 20px',
									color: '#f25656'
								}
							}
						]
					}
				]
			}, 1000)
		},
		//删除
		handleDelete(index, row) {
			console.log(index, row)

			this.table_data.splice(index, 1)

			this.$message({
				message: '删除成功！',
				type: 'success'
			})
		},
		//保存
		handleSave(index, row) {
			console.log(index, row)
			row.edit = false

			delete this.table_data[index].add

			this.$message({
				message: '保存成功！',
				type: 'success'
			})
		}
	},
	mounted() {
		this.table_data = [
			{
				date: '2016-05-03',
				name: '王小虎',
				province: '上海',
				city: '普陀区',
				address: '上海市普陀区金沙江路 1518 弄',
				zip: 200333,
				sex: 18,
				options: [
					{
						label: '编辑',
						methods: 'edit',
						props: {
							type: 'text'
						},
						style: {
							color: '#1ebf0c'
						}
					},
					{
						label: '保存',
						hidden: true,
						methods: 'save',
						props: {
							type: 'text'
						},
						style: {
							color: '#1ebf0c'
						}
					}
				]
			},
			{
				date: '2016-05-02',
				sex: 18,
				name: '王小虎',
				province: '上海',
				city: '普陀区',
				address: '上海市普陀区金沙江路 1518 弄',
				zip: 200333,
				options: [
					{
						label: '编辑',
						methods: 'edit',
						props: {
							type: 'text'
						},
						style: {
							color: '#1ebf0c'
						}
					},
					{
						label: '保存',
						hidden: true,
						methods: 'save',
						props: {
							type: 'text'
						},
						style: {
							color: '#1ebf0c'
						}
					},

					{
						label: '删除',
						methods: 'delete',
						props: {
							type: 'text'
						},
						style: {
							margin: '0 20px',
							color: '#f25656'
						}
					}
				]
			},
			{
				date: '2016-05-04',
				name: '王小虎',
				sex: 18,
				province: '上海',
				city: '普陀区',
				address: '上海市普陀区金沙江路 1518 弄',
				zip: 200333,
				options: [
					{
						label: '编辑',
						methods: 'edit',
						props: {
							type: 'text'
						},
						style: {
							color: '#1ebf0c'
						}
					},
					{
						label: '保存',
						hidden: true,
						methods: 'save',
						props: {
							type: 'text'
						},
						style: {
							color: '#1ebf0c'
						}
					},

					{
						label: '删除',
						methods: 'delete',
						props: {
							type: 'text'
						},
						style: {
							margin: '0 20px',
							color: '#f25656'
						}
					}
				]
			}
		]
		this.table_columns = [
			{
				prop: 'date',
				label: '日期',
				width: '150'
			},
			{
				prop: 'name',
				label: '姓名',
				width: '150'
			},
			{
				prop: 'sex',
				label: '年龄',
				width: '150'
			},
			{
				prop: 'province',
				label: '省份',
				width: ''
			},
			{
				prop: 'city',
				label: '城市',
				width: '150'
			},
			{
				prop: 'address',
				label: '地址',
				width: '150'
			}
		]
	},
	data() {
		return {
			loading: false,
			//表头信息
			table_columns: [],
			//表格数据
			table_data: []
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
