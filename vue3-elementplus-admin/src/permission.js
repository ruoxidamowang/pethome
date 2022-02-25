import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'
import { ElMessage } from 'element-plus'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
// import router from './router'
import store from './store'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const LOGIN_PATH = '/login'
const whitePathList = ['/login'] // no redirect whitelist

export function createPermissionGuard(router) {
	router.beforeResolve(async (to, from, next) => {
		const hasToken = getToken()
		const isDynamicAddedRoute = store.getters.isDynamicAddedRoute
		// start progress bar
		NProgress.start()

		// set page title
		document.title = getPageTitle(to.meta.title)
		// Whitelist can be directly entered
		if (whitePathList.includes(to.path)) {
			next()
			return
		}

		if (!hasToken) {
			// redirect login page
			const redirectData = {
				path: LOGIN_PATH,
				replace: true
			}
			if (to.path) {
				redirectData.query = {
					...redirectData.query,
					redirect: to.path
				}
			}
			next(redirectData)
			return
		}

		if (to.path === '/login') {
			next({ path: '/' })
			NProgress.done()
		}

		if (isDynamicAddedRoute) {
			next()
			return
		}

		try {
			const { menuList } = await store.dispatch('user/getInfo')
			const accessRoutes = await store.dispatch('permission/generateRoutes', { menuList })
			// console.log('菜单---', menuList)
			console.log('动态路由---', accessRoutes)
			accessRoutes.forEach(route => {
				router.addRoute(route) // 动态添加可访问路由表
			})
			store.dispatch('permission/setDynamicAddedRoute', true)
			const nextData = to.redirectedFrom || { ...to, replace: true }
			next(nextData)
		} catch (error) {
			console.log('permission--', error)
			// remove token and go to login page to re-login
			await store.dispatch('user/resetToken')
			store.dispatch('permission/setDynamicAddedRoute', false)
			ElMessage.error(error || 'Has Error')
			next(`/login`)
			NProgress.done()
		}
	})

	router.afterEach(() => {
		// finish progress bar
		NProgress.done()
	})
}
