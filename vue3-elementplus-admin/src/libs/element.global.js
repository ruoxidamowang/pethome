// 全局加载element
import ElementPlus, { ElMessage } from 'element-plus' // 全局加载element-plus
import locale from 'element-plus/lib/locale/lang/zh-cn' // 中文

export function setupElementPlus(app) {
	app.use(ElementPlus, { locale })
	app.use(ElMessage)
	window.$success = function(msg) {
		ElMessage({
			type: 'success',
			message: msg,
			duration: 2000
		})
	}

	// 失败消息提示
	window.$error = function(msg) {
		ElMessage({
			type: 'error',
			message: msg,
			duration: 2500
		})
	}
}
