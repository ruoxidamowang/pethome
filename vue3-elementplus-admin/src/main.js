import { createApp } from 'vue'
import { setupElementPlus } from './libs/element.part' // element-plus
import { setupGlobCom } from '@/components' // 全局公用组件
import { setupStore } from './store' // 状态管理
import { setupDirectives } from './directives'
import router, { setupRouter } from './router' // 路由
import App from './App.vue'

import { createPermissionGuard } from './permission' // 权限控制

import 'normalize.css/normalize.css'
import '@/styles/index.scss'
import VueMarkdownEditor from "@kangc/v-md-editor"; // 全局样式 css
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/style/base-editor.css';
VueMarkdownEditor.use(vuepressTheme);
const app = createApp(App).use(VueMarkdownEditor);
setupRouter(app) // 注册路由

setupStore(app) // 注册状态管理

createPermissionGuard(router)

setupGlobCom(app) // 注册全局组件

setupElementPlus(app) // 注册ElementPlus组件

setupDirectives(app) // 注册全局自定义指令

// if (process.env.NODE_ENV === 'production') {
// 	const { mockXHR } = require('../mock')
// 	mockXHR()
// }

router.isReady().then(() => app.mount('#app'))

export default app
