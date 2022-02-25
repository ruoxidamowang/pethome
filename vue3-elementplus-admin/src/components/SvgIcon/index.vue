<template>
	<div
		v-if="ifExternal"
		:style="styleExternalIcon"
		class="svg-external-icon svg-icon"
		v-bind="$attrs"
	/>
	<svg v-else :class="svgClass" aria-hidden="true" v-bind="$attrs">
		<use :xlink:href="iconName" />
	</svg>
</template>
<script>
import { isExternal } from '@/utils/validate'
import { computed } from 'vue'
export default {
	name: 'SvgIcon',
	props: {
		iconClass: {
			type: String,
			required: true
		},
		className: {
			type: String,
			default: ''
		}
	},
	setup(props) {
		const ifExternal = computed(() => isExternal(props.iconClass))
		const iconName = computed(() => `#icon-${props.iconClass}`)
		const svgClass = computed(() => (props.className ? 'svg-icon ' + props.className : 'svg-icon'))
		const styleExternalIcon = computed(() => ({
			mask: `url(${props.iconClass}) no-repeat 50% 50%`,
			'-webkit-mask': `url(${props.iconClass}) no-repeat 50% 50%`
		}))
		return {
			ifExternal,
			iconName,
			svgClass,
			styleExternalIcon
		}
	}
}
</script>

<style scoped>
.svg-icon {
	width: 1em;
	height: 1em;
	vertical-align: -0.15em;
	fill: currentColor;
	overflow: hidden;
}

.svg-external-icon {
	background-color: currentColor;
	mask-size: cover !important;
	display: inline-block;
}
</style>
