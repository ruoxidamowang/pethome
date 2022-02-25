<template>
	<div>
		<TreeTitle
			:style="{ 'padding-left': 15 * idx + 'px' }"
			:subNode="subNode"
			:isNodeExpand="isNodeExpand"
			:checked="checked"
			:indeterminate="indeterminate"
			@nodeClick="nodeClick"
			@getExpandedNode="getExpandedNode"
			@handleNodeExpanded="handleNodeExpanded"
			@changeChecked="changeChecked"
			@checkedChildren="checkedChildren"
			@changeIndeterminate="changeIndeterminate"
			@checkChange="checkChange"
		>
		</TreeTitle>
		<el-collapse-transition>
			<div v-show="showChildren">
				<ul class="tb-tree">
					<li class="tb-tree-item" v-for="(node, i) in subNode[aliasChildren]" :key="node.id">
						<TreeItem
							:ref="el => setItemRef(el, i)"
							:subNode="node"
							:level="idx"
							:checked="checkedList[i]"
							:index="i"
							:indeterminate="indeterminateList[i]"
							@checkChange="checkChange"
							@nodeClick="nodeClick"
							@nodeExpand="nodeExpand"
							@changeChecked="changeChecked"
							@changeIndeterminate="changeIndeterminate"
							@checkedChildren="checkedChildren(checked)"
						></TreeItem>
					</li>
				</ul>
			</div>
		</el-collapse-transition>
	</div>
</template>

<script>
import { computed, ref, inject } from 'vue'
import TreeTitle from './treeTitle'

export default {
	name: 'TreeItem',
	components: { TreeTitle },
	props: {
		subNode: {
			type: Object,
			default: () => {}
		},
		level: {
			//当前嵌套深度
			type: Number,
			default: 0
		},
		checked: {
			type: Boolean,
			default: false
		},
		index: {
			//当前深度索引
			type: Number,
			default: 0
		},
		indeterminate: {
			type: Boolean,
			default: false
		}
	},
	emits: ['nodeClick', 'nodeExpand', 'changeChecked', 'checkedChildren', 'changeIndeterminate', 'checkChange'],
	setup(props, { emit }) {
		const aliasChildren = inject('aliasChildren')
		const aliasDisabled = inject('aliasDisabled')
		const isNodeExpand = ref(false)
		const indeterminateList = ref(props.subNode[aliasChildren] ? props.subNode[aliasChildren].map(() => false) : []) // 当前节点子节点选中样式列表
		const checkedList = ref(props.subNode[aliasChildren] ? props.subNode[aliasChildren].map(() => false) : []) // 当前节点子节点选中状态列表
		const hasChildern = computed(() => props.subNode[aliasChildren] && props.subNode[aliasChildren].length > 0) // 是否存在子节点
		const showChildren = computed(() => hasChildern.value && isNodeExpand.value) // 是否展开当前子节点
		const idx = ref(props.level)
		idx.value += 1

		// 当前节点ref
		const treeItemsRef = ref([])
		const setItemRef = (el, i) => {
			el && (treeItemsRef.value[i] = el)
		}

		// 展开当前节点
		const expandedNode = function(expanded) {
			isNodeExpand.value = expanded
		}

		// 展开回调函数
		const handleNodeExpanded = function({ expanded, node }) {
			expandedNode(expanded) // 展开当前节点
			nodeExpand(expanded, node) // 展开时的回调
		}

		// 获取当前应展开元素- 首次展开执行
		const getExpandedNode = function({ expanded, node }) {
			handleNodeExpanded({ expanded, node })
		}

		// 点击节点
		const nodeClick = function(node) {
			if (aliasDisabled in node && node[aliasDisabled]) return false
			emit('nodeClick', node)
		}
		// 展开时的回调
		const nodeExpand = function(expanded, node) {
			emit('nodeExpand', expanded, node)
		}

		// 变更节点状态
		const changeChecked = (checked, index) => {
			// 不存在 index 是变更当前节点状态
			if (index !== -1) {
				//  递归处理 子节点状态
				checkedList.value[index] = checked // 变更子节点状态
				const checkAll = !checkedList.value.includes(false) // 子节点的兄弟节点是否全部选中
				checked
					? checkAll && emit('changeChecked', checked, props.index) //全部为true 变更父节点状态
					: !checkAll && emit('changeChecked', checked, props.index) //一个为 false 变更父节点状态
			} else {
				//存在子节点，变更的所有子节点状态，
				hasChildern.value && checkedList.value.fill(checked)
				//变更当前节点状态，
				emit('changeChecked', checked, props.index)
			}
		}

		// 变更节点样式
		const changeIndeterminate = ({ checked, indeterminate, index }) => {
			// console.log(props.subNode.id, index, indeterminate, checkedList.value)
			// 不存在 index 是变更当前节点状态
			if (index !== -1) {
				// 递归处理-从子节点传递而来的状态
				const checkAll = !checkedList.value.includes(false) //子节点全部选中
				const noCheckAll = !checkedList.value.includes(true) //子节点全部未选中
				indeterminateList.value[index] = indeterminate
				const noIndeterminateAll = !indeterminateList.value.includes(true) // 所有子节点的子节点都不存在选中为true
				// 设置 当前节点的样式
				// 1. checked为true, 子节点部分选中，当前节点 indeterminate 为 true
				// 2. checked为false, 子节点部分未选中 ，设置 当前节点 indeterminate 为 true
				// 3. 子节点的子节点存在选中  时 ，设置 当前节点 indeterminate 为 true
				emit('changeIndeterminate', {
					checked,
					indeterminate: checked ? !checkAll : !noCheckAll || !noIndeterminateAll,
					index: props.index
				})
			} else {
				//设置当前节点样式
				emit('changeIndeterminate', { checked, indeterminate, index: props.index })
			}
		}

		//选中子元素-所有相关父节点回调触发
		const checkedChildren = checked => {
			if (checked) {
				//全选
				checkedList.value.fill(checked)
				indeterminateList.value.fill(!checked) //  indeterminateList false
			} else {
				//取消全选 - 取消更深层子节点选中状态
				!checkedList.value.includes(false) && checkedList.value.fill(checked)
			}
			// console.log(checked, props.subNode.id, checkedList.value)
		}

		//节点选中状态/样式发生变化时的回调
		const checkChange = ({ checked, node, indeterminate }) => {
			emit('checkChange', { checked, node, indeterminate })
		}

		// 设置keys节点选中
		const setCheckedKeys = (keys = []) => {
			const nodes = props.subNode[aliasChildren] || []
			//清空节点状态
			checkedList.value.fill(false)
			indeterminateList.value.fill(false)

			if (keys.length > 0 && showChildren) {
				const checkedKyes = keys.reduce((pre, key) => {
					const cur = nodes.find((node, i) => {
						if (node.id === key) {
							!checkedList.value[i] && (checkedList.value[i] = true)
							return true
						}
						return false
					})
					return cur ? [...pre, cur] : pre
				}, [])
				// console.log('setCheckedKeys---1', checkedKyes, props.subNode.id, checkedList.value.includes(false))
				// 当前子节点选中
				if (checkedKyes.length > 0) {
					const checkAll = !checkedList.value.includes(false) //子节点全部选中
					if (checkAll) {
						//子节点全选
						emit('changeChecked', true, props.index)
						emit('changeIndeterminate', { checked: true, indeterminate: false, index: props.index })
					} else {
						//子节点单选
						emit('changeIndeterminate', { checked: true, indeterminate: true, index: props.index })
					}
				}
			}
			//存在子元素
			nodes.length > 0 && treeItemsRef.value.forEach(async itemRef => await itemRef.setCheckedKeys(keys))
		}

		return {
			treeItemsRef,
			setItemRef,
			idx,
			isNodeExpand,
			checkedList,
			hasChildern,
			showChildren,
			indeterminateList,
			getExpandedNode,
			aliasChildren,
			nodeClick,
			nodeExpand,
			expandedNode,
			checkedChildren,
			handleNodeExpanded,
			changeChecked,
			changeIndeterminate,
			checkChange,
			setCheckedKeys
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
