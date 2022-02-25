<template>
	<i v-if="icon && icon.includes('el-icon')" :class="[icon, 'sub-el-icon']" />
	<i v-else-if="icon" class="iconfont" :class="[icon, 'sub-el-icon']" />
	<svg-icon v-else :icon-class="icon" />
	<template v-if="title">
		<slot name="title">
			<span class="anticon-item">{{ title }}</span>
		</slot>
	</template>
</template>

<script>
export default {
	name: 'Item',
	props: {
		icon: {
			type: String,
			default: ''
		},
		title: {
			type: String,
			default: ''
		}
	}
}
</script>

<style lang="scss" scoped>
.sub-el-icon {
	color: currentColor;
	width: 1em;
	height: 1em;
}
.anticon-item {
	opacity: 1;
	transition: opacity 0.3s cubic-bezier(0.645, 0.045, 0.355, 1),
		width 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
}
</style>
