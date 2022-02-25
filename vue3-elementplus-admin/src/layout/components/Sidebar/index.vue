<template>
  <div :class="{ 'has-logo': showLogo }">
    <logo v-if="showLogo" :collapse="isCollapse" />
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :background-color="variables.menuBg"
        :text-color="variables.menuText"
        :unique-opened="false"
        :active-text-color="variables.menuActiveText"
        :collapse-transition="false"
        mode="vertical"
      >
        <sidebar-item
          v-for="route in permissionRoutes"
          :key="route.path"
          :item="route"
          :base-path="route.path"
        />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
import { computed, unref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import Logo from './Logo'
import SidebarItem from './SidebarItem'
import variablesScss from '@/styles/variables.scss'

export default {
  components: { SidebarItem, Logo },
  setup() {
    const store = useStore()
    const { currentRoute } = useRouter()
    const permissionRoutes = computed(() => store.getters.permission_routes)
    const sidebar = computed(() => store.getters.sidebar)
    const isCollapse = computed(() => !sidebar.value.opened)
    const variables = computed(() => variablesScss)
    const showLogo = computed(() => store.state.settings.sidebarLogo)
    const activeMenu = computed(() => {
      const { meta ,path} = unref(currentRoute)
      return meta.activeMenu ? meta.activeMenu:path
    })

    return {
      permissionRoutes,
      activeMenu,
      sidebar,
      showLogo,
      variables,
      isCollapse,
    }
  },
}
</script>
