<template>
	<el-table :data="list" style="width: 100%;padding-top: 15px;">
		<el-table-column label="Order_No" min-width="200">
			<template v-slot="scope">
				{{ orderNoFilter(scope.row.order_no) }}
			</template>
		</el-table-column>
		<el-table-column label="Price" width="195" align="center">
			<template v-slot="scope"> ¥{{ toThousandFilter(scope.row.price) }} </template>
		</el-table-column>
		<el-table-column label="Status" width="100" align="center">
			<template v-slot="{ row }">
				<el-tag :type="statusFilter(row.status)">
					{{ row.status }}
				</el-tag>
			</template>
		</el-table-column>
	</el-table>
</template>

<script>
import { transactionList } from '@/api/remote-search'

export default {
	filters: {
		// statusFilter(status) {
		// 	const statusMap = {
		// 		success: 'success',
		// 		pending: 'danger'
		// 	}
		// 	return statusMap[status]
		// },
		// orderNoFilter(str) {
		// 	return str.substring(0, 30)
		// }
	},
	data() {
		return {
			list: null
		}
	},
	created() {
		this.fetchData()
	},
	methods: {
		statusFilter(status) {
			const statusMap = {
				success: 'success',
				pending: 'danger'
			}
			return statusMap[status]
		},
		orderNoFilter(str) {
			return str.substring(0, 30)
		},
		toThousandFilter(num) {
			return (+num || 0).toString().replace(/^-?\d+/g, m => m.replace(/(?=(?!\b)(\d{3})+$)/g, ','))
		},
		fetchData() {
			transactionList().then(response => {
				this.list = response.data.items.slice(0, 8)
			})
		}
	}
}
</script>
