<template>
	<div class="tree-container">
		<template v-for="(node, i) in data" :key="node.id">
			<TreeItem
				:ref="el => setItemRef(el, i)"
				:subNode="node"
				:checked="checkedList[i]"
				:index="i"
				:indeterminate="indeterminateList[i]"
				@nodeClick="nodeClick"
				@nodeExpand="nodeExpand"
				@changeChecked="changeChecked"
				@changeIndeterminate="changeIndeterminate"
				@checkChange="checkChange"
			></TreeItem>
		</template>
	</div>
</template>

<script>
import { computed, onMounted, provide, ref } from 'vue'
import TreeItem from './treeItem'
const defaultAlias = {
	label: 'label',
	children: 'children',
	disabled: 'disabled'
}
export default {
	name: 'TBTree',
	components: { TreeItem },
	props: {
		data: {
			// 节点列表
			type: Array,
			default: () => []
		},
		activeId: {
			// 选中节点
			type: String,
			default: ''
		},
		defaultExpandedKeys: {
			//默认展开节点
			type: Array,
			default: () => []
		},
		props: {
			// 参数别名
			type: Object,
			default: () => {}
		},
		activeStyle: {
			//选中节点样式
			type: Object,
			default: () => ({
				color: '#3b83ee'
			})
		},
		showCheckbox: {
			// 是否使用复选框
			type: Boolean,
			default: false
		}
	},
	emits: ['nodeClick', 'nodeExpand', 'nodeCollapse', 'checkChange'],
	setup(props, { emit }) {
		const { label: aliasLabel, children: aliasChildren, disabled: aliasDisabled } = Object.assign(defaultAlias, props.props)
		const activeId = computed(() => props.activeId)
		const defaultExpandedKeys = computed(() => props.defaultExpandedKeys)
		provide('aliasLabel', aliasLabel) // label别名
		provide('aliasChildren', aliasChildren) // children 别名
		provide('aliasDisabled', aliasDisabled) // disabled 别名
		provide('activeId', activeId) // 激活节点id
		provide('defaultExpandedKeys', defaultExpandedKeys)
		provide('activeStyle', props.activeStyle)
		provide('showCheckbox', props.showCheckbox)

		const checkedList = ref(props.data.map(() => false)) // 当前子节点选中状态列表
		const indeterminateList = ref(props.data.map(() => false)) // 当前子节点选中状态样式列表

		//选中节点Keys
		const checkedKeys = []
		// 当前节点ref
		const treeItemsRef = ref([])
		const setItemRef = (el, i) => {
			el && (treeItemsRef.value[i] = el)
		}

		// 单选
		const changeChecked = (checked, index) => {
			checkedList.value[index] = checked
			// console.log('changeChecked---0:', index, checked, checkedList.value)
		}

		// 选择样式
		const changeIndeterminate = ({ checked, indeterminate, index }) => {
			indeterminateList.value[index] = indeterminate
		}

		// 设置keys节点选中
		const setCheckedKeys = (keys = []) => {
			const nodes = props.data
			checkedList.value.fill(false)
			indeterminateList.value.fill(false)
			if (keys.length > 0) {
				keys.reduce((pre, key) => {
					const cur = nodes.find((node, i) => {
						if (node.id === key) {
							!checkedList.value[i] && (checkedList.value[i] = true)
							return true
						}
						return false
					})
					return cur ? [...pre, cur] : pre
				}, [])
			}
			//存在子元素
			nodes.length > 0 && treeItemsRef.value.forEach(itemRef => itemRef.setCheckedKeys(keys))
		}

		// 更新选中节点keys
		const changeCheckedKeys = (checkedKeys, checked, node) => {
			const index = checkedKeys.indexOf(node.id)
			if (checked) {
				index === -1 && checkedKeys.push(node.id)
			} else {
				index !== -1 && checkedKeys.splice(index, 1)
			}
		}

		// emit 事件统一处理
		const emitsHandler = (emitName, ...args) => {
			emit(emitName, ...args)
		}

		// 节点被点击时的回调
		const nodeClick = function(node) {
			if (aliasDisabled in node && node[aliasDisabled]) return false
			emitsHandler('nodeClick', node)
		}

		// 节点被展开/折叠时触发的事件
		const nodeExpand = function(expanded, node) {
			expanded ? emitsHandler('nodeExpand', expanded, node) : emitsHandler('nodeCollapse', expanded, node)
		}

		// 节点check状态发生变化时的回调
		const checkChange = ({ checked, node, indeterminate }) => {
			changeCheckedKeys(checkedKeys, checked, node)
			//节点对象，节点选中状态，节点的子树中是否有被选中的节点
			emitsHandler('checkChange', node, checked, indeterminate)
		}

		// 获取选中节点的keys
		const getCheckedKeys = () => checkedKeys

		return {
			treeItemsRef,
			setItemRef,
			indeterminateList,
			nodeClick,
			nodeExpand,
			checkedList,
			checkChange,
			changeChecked,
			changeIndeterminate,
			getCheckedKeys,
			setCheckedKeys,
			emitsHandler
		}
	}
}
</script>

<style lang="scss" scoped>
.tb-tree {
	border: none;
	height: 100%;
	width: 100%;
}
</style>
