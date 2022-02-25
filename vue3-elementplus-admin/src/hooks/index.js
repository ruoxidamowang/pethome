import { ElMessage, ElMessageBox } from 'element-plus'

// 确认弹窗
export function useConfirm({
	title = 'Title',
	tps = '提示',
	confirmButtonText = '确定',
	cancelButtonText = '取消',
	showCancelButton = true,
	roundButton = true, //按钮圆角
	customClass = 'confirm-custom-class' // 样式类名
}) {
	return ElMessageBox.confirm(title, tps, {
		confirmButtonText,
		cancelButtonText,
		showCancelButton,
		roundButton,
		customClass
	})
}

// 删除确认弹窗
export function useDeleteConfirm() {
	return useConfirm({
		title: '确定删除吗?',
		tps: '删除'
	})
}
