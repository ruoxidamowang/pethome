import screenfull from 'screenfull'

function handleClick() {
	if (!screenfull.isEnabled) {
		alert('浏览器不支持全屏')
		return
	}
	screenfull.toggle()
}

const vScreenfull = {
	name: 'screenfull',
	beforeMount(el, binding) {
		if (binding.modifiers.icon) {
			if (el.hasIcon) return
			// 创建全屏图标
			const iconElement = document.createElement('i')
			iconElement.setAttribute('class', 'el-icon-full-screen')
			iconElement.setAttribute('style', 'margin-left:5px')
			el.appendChild(iconElement)
			el.hasIcon = true
		}
		el.style.cursor = el.style.cursor || 'pointer'
		// 监听点击全屏事件
		el.addEventListener('click', () => handleClick())
	}
}

export default vScreenfull
