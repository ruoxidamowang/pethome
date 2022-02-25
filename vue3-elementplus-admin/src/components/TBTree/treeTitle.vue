<template>
	<div class="tb-subNode__title" @click="nodeClick(subNode)" :style="isActive ? activeStyle : ''">
		<i
			v-if="hasChildern"
			class="el-icon-caret-right el-tree-node__expand-icon"
			:class="isNodeExpand ? 'expanded' : ''"
			@click.stop="handleNodeExpanded(isNodeExpand, subNode)"
		></i>
		<i v-else class="el-icon-caret-right el-tree-node__expand-icon" style="visibility: hidden"></i>
		<template v-if="showCheckbox">
			<el-checkbox
				v-if="hasChildern"
				class="tb-subNode__checkbox"
				:model-value="checked"
				:indeterminate="indeterminate"
				:data-indeterminate="indeterminate"
				@change="changeChecked"
			></el-checkbox>
			<el-checkbox v-else :model-value="checked" @change="changeChecked" class="tb-subNode__checkbox"></el-checkbox>
		</template>
		<i class="mr10" :class="subNode.icon" style="font-size: 16px"></i>
		<span class="">{{ subNode[aliasLabel] }}</span>
		<span v-show="isActive" class="fr" :style="activeStyle">当前组织</span>
	</div>
</template>
<script>
import { computed, inject, watchEffect, watch } from 'vue'
export default {
	name: 'TreeTitle',
	props: {
		subNode: {
			type: Object,
			default: () => {}
		},
		isNodeExpand: {
			type: Boolean,
			default: false
		},
		checked: {
			type: Boolean,
			default: false
		},
		indeterminate: {
			type: Boolean,
			default: false
		}
	},
	emits: [
		'nodeClick',
		'getExpandedNode',
		'handleNodeExpanded',
		'update:modelValue',
		'changeChecked',
		'checkedChildren',
		'changeIndeterminate',
		'checkChange'
	],
	setup(props, { emit }) {
		const aliasLabel = inject('aliasLabel')
		const aliasChildren = inject('aliasChildren')
		const activeId = inject('activeId')
		const defaultExpandedKeys = inject('defaultExpandedKeys') //默认展开keys
		const activeStyle = inject('activeStyle') // 激活样式
		const showCheckbox = inject('showCheckbox') // 是否显示Checkbox
		const hasChildern = computed(() => props.subNode[aliasChildren] && props.subNode[aliasChildren].length > 0) // 是否存在子节点
		const isActive = computed(() => activeId.value === props.subNode.id) //是否是激活节点

		// 展开/折叠
		const handleNodeExpanded = function(expanded, node) {
			emit('handleNodeExpanded', { expanded: !expanded, node })
		}
		// 点击当前节点
		const nodeClick = function(node) {
			emit('nodeClick', node)
		}

		// 判断 defaultExpandedKeys 是否存在 当前 props.submenu 中
		const findExpandedNode = function(node, expandedKeys, aliasChildren) {
			if (!expandedKeys.length) return false
			if (expandedKeys.includes(node.id)) return true // 展开当前节点
			//存在子节点
			if (node[aliasChildren] && Array.isArray(node[aliasChildren])) {
				return node[aliasChildren].some(n => {
					if (expandedKeys.includes(n.id)) return true // 展开当前节点
					if (n[aliasChildren]) {
						return findExpandedNode(n, expandedKeys, aliasChildren)
					}
					return false
				})
			}
			return false
		}

		// 当前节点是否展开
		const shouldExpanded = findExpandedNode(props.subNode, defaultExpandedKeys.value, aliasChildren)
		// 传递出需展开元素
		shouldExpanded === true && emit('getExpandedNode', { expanded: shouldExpanded, node: props.subNode })

		// 选中状态变更
		const changeChecked = checked => {
			// console.log('changeChecked---', props.subNode.id)
			emit('changeChecked', checked, -1)
			// 无论选中或取消，样式都未false
			emit('changeIndeterminate', { checked, indeterminate: false, index: -1 })
		}

		// watchEffect(() => {
		// 	// 带有子节点的节点触发
		// 	if (hasChildern.value) {
		// 		emit('checkedChildren', props.checked, props.indeterminate)
		// 	}
		// })

		//节点选中状态发生变化时的回调
		watch([() => props.indeterminate, () => props.checked], () => {
			emit('checkChange', { checked: props.checked, node: props.subNode, indeterminate: props.indeterminate })
			// 带有子节点的节点触发
			hasChildern.value && emit('checkedChildren', props.checked, props.indeterminate)
			// console.log('checkChange---', { checked: props.checked, node: props.subNode, indeterminate: props.indeterminate })
		})

		return {
			activeStyle,
			showCheckbox,
			activeId,
			defaultExpandedKeys,
			isActive,
			aliasLabel,
			hasChildern,
			nodeClick,
			handleNodeExpanded,
			changeChecked
		}
	}
}
</script>

<style lang="scss" scoped>
.tb-subNode__title {
	padding: 10px 5px;
	font-size: 14px;
	overflow: hidden;

	&:hover {
		background: rgb(220 220 220 / 40%);
		cursor: pointer;
	}
	.el-icon-caret-right {
		font-size: 18px;
	}
	.tb-subNode__checkbox {
		display: inline-flex;
		margin-right: 8px !important;
	}
}
</style>
