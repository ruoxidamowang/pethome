<template>
	<div v-if="!item.hidden">
		<template
			v-if="
				hasOneShowingChild(item.children, item) &&
					(!onlyOneChild.children || onlyOneChild.noShowingChildren) &&
					!item.alwaysShow
			"
		>
			<app-link v-if="onlyOneChild.meta" :to="resolvePath(onlyOneChild.path)">
				<el-menu-item
					:index="resolvePath(onlyOneChild.path)"
					:class="{ 'submenu-title-noDropdown': !isNest }"
				>
					<item
						v-if="onlyOneChild.meta"
						:icon="onlyOneChild.meta.icon || (item.meta && item.meta.icon)"
					/>
					<template #title>
						<span class="anticon-item">{{ onlyOneChild.meta.title }}</span>
					</template>
				</el-menu-item>
			</app-link>
		</template>
		<el-submenu v-else ref="subMenu" :index="resolvePath(item.path)" popper-append-to-body>
			<template #title>
				<item v-if="item.meta" :icon="item.meta && item.meta.icon" :title="item.meta.title" />
			</template>
			<sidebar-item
				v-for="child in item.children"
				:key="child.path"
				:is-nest="true"
				:item="child"
				:base-path="resolvePath(child.path)"
				class="nest-menu"
			/>
		</el-submenu>
	</div>
</template>
<script>
import { ref } from 'vue'
import path from 'path'
import { isExternal } from '@/utils/validate'
import Item from './Item.vue'
import AppLink from './Link.vue'

export default {
	name: 'SiderItem',
	components: { Item, AppLink },
	props: {
		// route object
		item: {
			type: Object,
			required: true
		},
		isNest: {
			type: Boolean,
			default: false
		},
		basePath: {
			type: String,
			default: ''
		}
	},
	setup(props) {
		const onlyOneChild = ref(null)

		function hasOneShowingChild(children = [], parent) {
			const showingChildren = children.filter(item => {
				if (item.hidden) {
					return false
				} else {
					// Temp set(will be used if only has one showing child)
					onlyOneChild.value = item
					return true
				}
			})

			// When there is only one child router, the child router is displayed by default
			if (showingChildren.length === 1) {
				return true
			}

			// Show parent if there are no child router to display
			if (showingChildren.length === 0) {
				onlyOneChild.value = { ...parent, path: '', noShowingChildren: true }
				return true
			}

			return false
		}

		function resolvePath(routePath) {
			if (isExternal(routePath)) {
				return routePath
			}
			return path.resolve(props.basePath, routePath)
		}

		return {
			onlyOneChild,
			hasOneShowingChild,
			resolvePath
		}
	}
}
</script>

<style></style>
