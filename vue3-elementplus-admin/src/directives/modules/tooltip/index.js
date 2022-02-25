import { h, render, resolveComponent, withCtx } from 'vue'
import { ElTooltip } from 'element-plus'

const vTooltip = {
	name: 'tooltip',
	beforeMount(el, binding) {
		const { arg: content, value: attrs } = binding
		const { placement = 'bottom', effect = 'dark' } = attrs
		const componentOptions = {
			components: {
				ElTooltip
			},
			props: ['content', 'placement', 'effect'],
			render() {
				const { content, placement, effect } = this
				return h(
					resolveComponent('ElTooltip'),
					{ content, placement, effect },
					{
						default: () => {
							return [
								h('i', {
									class: 'el-icon-question',
									style: 'margin:0 10px;font-size:24px;'
								})
							]
						}
					}
					// 插槽内容
					// {
					// 	content: withCtx(() => [
					// 		h('i', {
					// 			class: 'el-icon-question',
					// 			style: { margin: '0 10px', 'font-size': '24px' }
					// 		})
					// 	])
					// }
				)
			}
		}

		//使用vue文件模板
		// import tooltipComponent from './tooltip.vue'
		// const vm = h(tooltipComponent, {
		// 	placement,
		// 	effect
		// })

		const vm = h(componentOptions, {
			content,
			placement,
			effect
		})

		render(vm, el)
	}
}

export default vTooltip
