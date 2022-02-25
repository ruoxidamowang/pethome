/**
 * v-draggable 拖动指令
 * @desc
 * @example
 * ```vue
 * <div> v-draggable></div>
 * <div> v-draggable.firstChild></div> // firstChild为拖动元素，绑定元素移动
 * <div> v-draggable="'draggable'"></div>// 设置绑定元素类名为 'draggable' 的子元素为拖动元素
 * <div> v-draggable="['el-dialog','el-dialog__header']"></div>// element 弹框
 * ```
 */

const draggable = {
	name: 'draggable',
	beforeMount(el, { modifiers, value }) {
		let dragDom = el, // 绑定元素
			dialogHeaderEl // 拖动元素

		if (modifiers.firstChild) {
			// v-draggable.firstChild
			dialogHeaderEl = dragDom.firstChild
		} else if (!value) {
			//默认
			dialogHeaderEl = el
		} else if (value instanceof Array && value.length) {
			// v-draggable="['el-dialog','el-dialog__header']"
			dragDom = value[0] && el.querySelector('.' + value[0].trim())
			dialogHeaderEl = el.querySelector('.' + value[1].trim())
		} else {
			// v-draggable="'draggable'"
			dialogHeaderEl = el.querySelector('.' + value.trim())
		}

		dialogHeaderEl.style.cssText += ';cursor:move;'
		dragDom.style.cssText += ';'
		const noffX = 0 // 拖动元素居中 受translateX 影响 - 50% 参数 0.5
		const noffY = 0 // 拖动元素居中 受translateY 影响 - 50% 参数 0.5

		dialogHeaderEl.onmousedown = e => {
			// 鼠标按下，计算当前元素距离可视区的距离
			const disX = e.clientX - dragDom.offsetLeft
			const disY = e.clientY - dragDom.offsetTop

			const screenWidth = document.body.clientWidth // body当前宽度
			const screenHeight = document.documentElement.clientHeight // 可见区域高度(应为body高度，可某些环境下无法获取)

			const dragDomWidth = dragDom.offsetWidth // 拖动元素宽度
			const dragDomheight = dragDom.offsetHeight // 拖动元素高度

			const minDragDomLeft = 0 + noffX * dragDomWidth
			const minDragDomTop = 0 + noffY * dragDomheight

			const maxDragDomLeft = screenWidth - dragDomWidth + noffX * dragDomWidth // 当前可向右移动距离
			const maxDragDomTop = screenHeight - dragDomheight + noffY * dragDomheight // 当前可向下移动距离

			document.onmousemove = function(e) {
				// 通过事件委托，计算移动的距离
				let left = e.clientX - disX
				let top = e.clientY - disY
				// 边界处理
				left = left <= minDragDomLeft ? minDragDomLeft : left >= maxDragDomLeft ? maxDragDomLeft : left
				top = top <= minDragDomTop ? minDragDomTop : top >= maxDragDomTop ? maxDragDomTop : top

				// 移动当前元素
				dragDom.style.cssText += `;position: absolute;left:${left}px;top:${top}px;`
			}

			document.onmouseup = function() {
				document.onmousemove = null
				document.onmouseup = null
			}
			return false
		}
	},
	beforeUnmount(el) {
		el.onmousedown = null
	}
}

export default draggable
