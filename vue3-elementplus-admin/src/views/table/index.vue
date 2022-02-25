<template>
	<div class="information-container">
		<!-- 筛选 -->
		<FilterSearch @handelClick="handelClick" :filtersList="filtersList" :btnProps="btnProps" />
		<!-- 列表 -->
		<div class="table-content">
			<TBTable
				ref="TBTable"
				showCheckBox
				showOrder
				:tableData="tableData"
				:tableHeader="tableHeader"
				:pageInfo="pageInfo"
				:options="tableOptions()"
				:requestFn="fetchList"
				:filterParams="filterParams"
				@download="handleDownload"
				@handleTableButton="handelBtn"
				@handleSizeChange="handleSizeChange"
				@handleSelect="handleSelect"
				@handleSelectionChange="handleSelectionChange"
			/>
		</div>
		<Dialog
			width="30%"
			v-model="dialogState.visible"
			:loading="dialogState.loading"
			:showCancelButton="true"
			:title="dialogState.title"
			:beforeClose="beforeClose"
			@dynamicSubmit="submit"
			@closeCallBack="closeCallBack"
			showFooterBtn
			dynamicLoading
		>
			<div>
				123
			</div>
		</Dialog>
	</div>
</template>

<script>
import { ref, unref, reactive, onMounted, getCurrentInstance, toRefs, onBeforeUnmount } from 'vue'
import { fetchList } from '@/api/article'
import { mergeRefProperty } from '@/utils'
import { useDeleteConfirm } from '@/hooks'
const getFiltersParams = array => {
	let obj = {}
	array.forEach(elem => (elem.prop ? (obj[elem.prop] = elem.value ? elem.value : '') : null))
	return obj
}
export default {
	setup() {
		const { proxy } = getCurrentInstance()
		const tableReactive = reactive({
			// 筛选项
			filtersList: [
				{
					label: '输入框',
					inputType: 'input',
					prop: 'name'
				},
				{
					label: '下拉选择框',
					prop: 'selectName',
					inputType: 'select',
					value: '选项1',
					options: [
						{
							value: '选项1',
							label: '黄金糕'
						},
						{
							value: '选项2',
							label: '双皮奶'
						}
					]
				},
				{
					label: '时间段选择',
					inputType: 'datePicker',
					type: 'daterange',
					rangeSeparator: '至',
					endPlaceholder: '结束时间',
					startPlaceholder: '开始时间',
					placeholder: '请选择',
					valueFormat: 'yyyy-MM-dd',
					prop: 'datePickerName',
					value: [new Date('2021/1/1'), new Date()]
				}
			],
			// 筛选按钮
			btnProps: {
				hiddenQueryBtn: false,
				queryBtnLabel: '搜索',
				queryBtnClass: [],
				queryBtnSize: 'medium',
				queryBtnType: '',
				list: [
					{
						btnType: 'btnType1',
						label: '按钮1',
						id: '1',
						type: 'success'
					},
					{
						btnType: 'btnType2',
						label: '自定义样式按钮',
						id: '2',
						style: {
							'background-color': '#5584ff',
							color: '#fff'
						}
					}
				]
			},
			// 表头
			tableHeader: [
				{
					label: '作者',
					prop: 'author',
					width: ''
				},
				{
					label: '封面',
					prop: 'image_uri',
					colType: 'image',
					width: ''
				},
				{
					label: '内容',
					prop: 'content',
					method: 'download',
					colType: 'html',
					render() {
						return `<span style='color:#3B83EE;text-decoration:underline;'>下载</span>`
					}
				},
				{
					label: '状态',
					prop: 'status',
					width: ''
				},
				{
					label: '评论员',
					prop: 'reviewer',
					width: ''
				},
				{
					label: '出版时间',
					prop: 'display_time',
					width: '200'
				}
				// {
				// 	label: '是否首页展示',
				// 	render: row => (row.show ? '是' : '否')
				// }
			],
			// 表格操作按钮
			tableOptions: (hidden = false) => ({
				label: '操作',
				width: '200',
				children: [
					{
						hidden,
						label: '编辑',
						method: 'edit',
						props: {
							type: 'text'
						},
						style: {
							color: '#1ebf0c'
						}
					},
					{
						hidden,
						label: '删除',
						method: 'delete',
						props: {
							type: 'text'
						},
						style: {
							margin: '0 20px',
							color: '#f25656'
						}
					}
				]
			})
		})
		const TBTable = ref(null)
		const filterParams = ref(getFiltersParams(tableReactive.filtersList)) // 筛选参数
		const tableData = ref([])
		// 分页参数
		const pageInfo = reactive({
			totalCount: 0,
			pageIndex: 1,
			pageSize: 10
		})
		// 弹窗
		const dialogState = reactive({
			title: '提示',
			visible: false
		})

		// 筛选搜索按钮
		const handelClick = ({ btnType, query }) => {
			console.log(btnType, query)
			// 搜索
			if (btnType === 'query') {
				// 合并筛选参数
				mergeRefProperty(unref(filterParams), query)
				TBTable.value.getTableData().then(res => {
					const { items, total } = res.data
					pageInfo.totalCount = total
					tableData.value = items
				})
			}
		}

		// 分页
		const handleSizeChange = pages => {
			const { page, limit } = pages
			pageInfo.pageIndex = page
			pageInfo.pageSize = limit
			console.log(pageInfo)
			TBTable.value.getTableData().then(res => {
				const { items } = res.data
				tableData.value = items
			})
		}

		//下载
		const handleDownload = async ({ method, row, index }) => {
			console.log(method, row, index)
		}

		// 列表操作按钮
		const handelBtn = async ({ method, row, index }) => {
			console.log(method, row, index)
			if (method === 'delete') {
				useDeleteConfirm()
			} else if (method === 'edit') {
				dialogState.visible = true
			}
		}

		// 单选
		const handleSelect = ({ selection, row }) => {
			console.log('单选：', selection, row)
		}

		//选中变化回调
		const handleSelectionChange = selection => {
			console.log('选中变化：', selection)
		}

		// 通过Enter键，选中表格某一行
		let setRowIndex = 0
		const enterKey = event => {
			const isEnterKey = event.keyCode === 13 || event.key === 'Enter'
			if (isEnterKey) {
				console.log('通过Enter键，选中表格某一行')
				if (setRowIndex > pageInfo.pageSize - 1) setRowIndex = 0
				TBTable.value.setCurrentRow(tableData.value[setRowIndex++])
			}
		}

		const submit = () => {
			dialogState.loading = true
			setTimeout(() => {
				dialogState.loading = false
			}, 2000)
		}

		// 关闭前的回调
		const beforeClose = done => {
			proxy
				.$confirm('确认关闭？')
				.then(_ => {
					done()
				})
				.catch(_ => {})
		}

		// 关闭回调
		const closeCallBack = () => {
			console.log('’closeCallBack')
		}

		onMounted(() => {
			document.addEventListener('keyup', enterKey)
			TBTable.value.getTableData().then(res => {
				const { items, total } = res.data
				pageInfo.totalCount = total
				tableData.value = items
				TBTable.value.setCurrentRow(tableData.value[setRowIndex++])
			})
		})

		onBeforeUnmount(() => {
			document.removeEventListener('keyup', enterKey)
		})

		return {
			filterParams,
			TBTable,
			tableData,
			fetchList,
			handelClick,
			handleSizeChange,
			pageInfo,
			dialogState,
			submit,
			beforeClose,
			closeCallBack,
			handelBtn,
			handleDownload,
			handleSelect,
			handleSelectionChange,
			...toRefs(tableReactive)
		}
	}
}
</script>

<style lang="scss" scope>
.information-container {
	box-sizing: border-box;
	padding: 15px 10px 0;
	font-size: 0;
	.table-content {
		box-sizing: border-box;
		padding: 10px 0;
	}
}
</style>
