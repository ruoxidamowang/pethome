<template>
  <div>
    <div :class="classObj" class="app-wrapper">
      <div v-if="device === 'mobile' && sidebar.opened" class="drawer-bg" @click="handleClickOutside" />
      <Sidebar class="sidebar-container" />
      <div :class="{ hasTagsView: needTagsView }" class="main-container">
        <div :class="{ 'fixed-header': fixedHeader }">
          <Navbar v-if="needNavbar" />
          <TagsView v-if="needTagsView" />
        </div>
        <AppMain />
      </div>
    </div>
  </div>
</template>

<script>
  import { defineComponent, computed } from 'vue'
  import { useStore } from 'vuex'
  import { AppMain, Navbar, Sidebar, TagsView } from './components'
  import ResizeMixin from './mixin/ResizeHandler'

  export default defineComponent({
    name: 'Layout',
    components: {
      AppMain,
      Navbar,
      Sidebar,
      TagsView,
    },
    mixins: [ResizeMixin],
    setup() {
      const store = useStore()
      const sidebar = computed(() => store.state.app.sidebar)
      const device = computed(() => store.state.app.device)
      const showSettings = computed(() => store.state.settings.showSettings)
      const needTagsView = computed(() => store.state.settings.tagsView)
      const needNavbar = computed(() => store.state.settings.navbar)
      const fixedHeader = computed(() => store.state.settings.fixedHeader)
      const opened = computed(() => store.state.app.sidebar.opened)
      const withoutAnimation = computed(() => store.state.app.sidebar.withoutAnimation)

      const classObj = computed(() => {
        return {
          hideSidebar: !opened.value,
          openSidebar: opened.value,
          withoutAnimation: withoutAnimation.value,
          mobile: device.value === 'mobile',
        }
      })

      function handleClickOutside() {
        store.dispatch('app/closeSideBar', { withoutAnimation: false })
      }

      return {
        sidebar,
        device,
        showSettings,
        needTagsView,
        needNavbar,
        fixedHeader,
        classObj,
        handleClickOutside,
      }
    },
  })
</script>

<style lang="scss" scoped>
  @import '~@/styles/mixin.scss';
  @import '~@/styles/variables.scss';

  .app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;

    &.mobile.openSidebar {
      position: fixed;
      top: 0;
    }
  }

  .drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
  }

  .fixed-header {
    position: fixed;
    top: 0;
    right: 0;
    z-index: 9;
    width: calc(100% - #{$sideBarWidth});
    transition: width 0.28s;
  }

  .hideSidebar .fixed-header {
    width: calc(100% - 54px);
  }

  .mobile .fixed-header {
    width: 100%;
  }

  .app-header {
    height: 95px;
    background: linear-gradient(to right, rgba(85, 132, 255, 1), rgba(1, 49, 177, 1));
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 20px;
  }
</style>
