import { createRouter, createWebHashHistory } from 'vue-router'
import {dynamicRoutes, staticRoutes} from './routes'

// 返回上一级页面的浏览位置
const scrollBehavior = (to, from, savedPosition) => {
	// scroll to id `#app` + 200px, and scroll smoothly (when supported by the browser)
	if (savedPosition) {
		return savedPosition
	} else {
		return {
			el: '#app',
			top: 0,
			left: 0,
			behavior: 'smooth'
		}
	}
}

const myRoutes = [];
staticRoutes.forEach(a=>{
	myRoutes.push(a)
})

dynamicRoutes.forEach(a=>{
	myRoutes.push(a)
})

const router = createRouter({
	// routes: staticRoutes, // 添加静态路由
	routes: myRoutes, // 添加静态路由
	history: createWebHashHistory(), // hash 模式
	scrollBehavior
})

export function setupRouter(app) {
	app.use(router)
}

export function resetRouter() {
	const constantRouteNames = ['Root', 'RedirectRoot', 'Redirect', 'Login', '401', '404']
	router.getRoutes().forEach(route => {
		const { name } = route
		if (name && !constantRouteNames.includes(name)) {
			router.hasRoute(name) && router.removeRoute(name)
		}
	})
}

export default router
