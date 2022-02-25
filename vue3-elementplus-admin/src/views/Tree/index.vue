<template>
	<div>
		<TBTree
			ref="TBTreeRef"
			:data="projectMenu"
			:activeId="activeProjectMenu.id"
			:defaultExpandedKeys="['1-1-2-3', '2-1']"
			:props="{
				label: 'menuName'
			}"
			showCheckbox
			@nodeClick="handleNode"
			@nodeExpand="nodeExpand"
			@checkChange="checkChange"
		>
		</TBTree>
		<el-button type="primary" @click="getCheckedKeys">获取选中节点</el-button>
		<el-button type="primary" @click="setCheckedKeys">设置节点选中</el-button>
		<el-button type="primary" @click="resetChecked">清空选中</el-button>
	</div>
</template>

<script>
import { ref, reactive } from 'vue'

export default {
	name: 'demo',
	setup() {
		const activeProjectMenu = reactive({
			icon: 'el-icon-office-building',
			menuName: '第一工程处-1-1-2-3-2',
			id: '1-1-2-3-2'
		})

		const projectMenu = ref([
			{
				icon: 'el-icon-office-building',
				menuName: '第一工程处-1',
				id: '1',
				children: [
					{
						icon: 'el-icon-office-building',
						menuName: '第一工程处1-1',

						id: '1-1',
						children: [
							{
								icon: 'el-icon-office-building',
								menuName: '第一工程处-1-1-1',

								id: '1-1-1'
							},
							{
								icon: 'el-icon-office-building',
								menuName: '第一工程处-1-1-2',

								id: '1-1-2',
								children: [
									{
										icon: 'el-icon-office-building',
										menuName: '第一工程处-1-1-2-1',

										id: '1-1-2-1'
									},
									{
										icon: 'el-icon-office-building',
										menuName: '第一工程处-1-1-2-2',
										disabled: true,
										id: '1-1-2-2'
									},
									{
										icon: 'el-icon-office-building',
										menuName: '第一工程处-1-1-2-3',
										id: '1-1-2-3',
										children: [
											{
												icon: 'el-icon-office-building',
												menuName: '第一工程处-1-1-2-3-1',
												id: '1-1-2-3-1',
												children: [
													{
														icon: 'el-icon-office-building',
														menuName: '第一工程处-1-1-2-3-1-1',
														id: '1-1-2-3-1-1'
													},
													{
														icon: 'el-icon-office-building',
														menuName: '第一工程处-1-1-2-3-1-2',
														id: '1-1-2-3-1-2'
													}
												]
											},
											{
												icon: 'el-icon-office-building',
												menuName: '第一工程处-1-1-2-3-2',
												disabled: true,
												id: '1-1-2-3-2'
											}
										]
									}
								]
							},
							{
								icon: 'el-icon-office-building',
								menuName: '第一工程处-1-1-3',

								id: '1-1-3',
								children: [
									{
										icon: 'el-icon-office-building',
										menuName: '第一工程处-1-1-3-1',

										id: '1-1-3-1'
									},
									{
										icon: 'el-icon-office-building',
										menuName: '第一工程处-1-1-3-2',

										disabled: true,
										id: '1-1-3-2'
									}
								]
							}
						]
					},
					{
						icon: 'el-icon-office-building',
						menuName: '第一工程处1-2',

						id: '1-2'
					}
				]
			},
			{
				icon: 'el-icon-office-building',
				menuName: '第二工程处-2',

				id: '2',
				children: [
					{
						icon: 'el-icon-office-building',
						menuName: '第二工程处2-1',
						id: '2-1'
					},
					{
						icon: 'el-icon-office-building',
						menuName: '第二工程处2-2',

						id: '2-2'
					}
				]
			},
			{
				icon: 'el-icon-office-building',
				menuName: '第二工程处-3',

				id: '3'
			}
		])

		const TBTreeRef = ref(null)

		//节点被点击回调
		function handleNode(node) {
			// console.log('点击节点回调--', node)
			activeProjectMenu.icon = node.icon
			activeProjectMenu.menuName = node.menuName
			activeProjectMenu.isActive = node.isActive
			activeProjectMenu.id = node.id
		}

		// 节点被展开回调
		function nodeExpand(expanded, node) {
			// console.log('展开节点回调--', { expanded, node })
		}

		// 节点被选中状态变化回调
		function checkChange(node, checked, indeterminate) {
			// console.log(node, checked, indeterminate)
		}

		// 获取选中节点
		function getCheckedKeys() {
			const nodeKeys = TBTreeRef.value.getCheckedKeys()
			console.log('nodeKeys---', nodeKeys)
		}

		//设置节点选中
		function setCheckedKeys() {
			TBTreeRef.value.setCheckedKeys(['1-1-2-1', '1-1-2-3-1', '1-1-2-3-2', '3', '1-1-3-1'])
		}

		// 清空节点
		function resetChecked() {
			TBTreeRef.value.setCheckedKeys([])
		}

		return {
			TBTreeRef,
			activeProjectMenu,
			projectMenu,
			handleNode,
			nodeExpand,
			checkChange,
			getCheckedKeys,
			setCheckedKeys,
			resetChecked
		}
	}
}
</script>
