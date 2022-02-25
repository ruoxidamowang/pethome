import storeLocal from 'storejs'
import { dynamicRoutes, staticRoutes, dynamicRedirect } from '@/router/routes'

/**
 * Filter asynchronous routing tables by recursion
 * @param routes dynamicRoutes
 * @param menuList menuList
 */
export function filterAsyncRoutes(routes = [], menuList = []) {
	const res = []
	routes.forEach(route => {
		const tmp = { ...route }
		menuList.forEach(menu => {
			if (route.name === menu.name) {
				if (menu.children) {
					tmp.redirect = menu.path + '/' + menu.children[0].path
					tmp.children = filterAsyncRoutes(tmp.children, menu.children)
					res.push(tmp)
				} else {
					res.push(tmp)
				}
			}
		})
	})
	return res
}

const state = {
	// Whether the route has been dynamically added
	isDynamicAddedRoute: false,
	routes: [],
	addRoutes: []
}

const mutations = {
	SET_ROUTES: (state, routes) => {
		state.addRoutes = routes
		state.routes = staticRoutes.concat(routes)
	},
	SET_DYNAMIC_ADDED_ROUTE(state, added) {
		storeLocal.set('isDynamicAddedRoute', added)
		state.isDynamicAddedRoute = added
	}
}

const actions = {
	generateRoutes({ commit }, { menuList }) {
		return new Promise(resolve => {
			const accessRoutes = filterAsyncRoutes(dynamicRoutes, menuList)
			dynamicRedirect.redirect = accessRoutes[1].path
			accessRoutes.push(dynamicRedirect)
			commit('SET_ROUTES', accessRoutes)
			resolve(accessRoutes)
		})
	},
	setDynamicAddedRoute({ commit }, added) {
		commit('SET_DYNAMIC_ADDED_ROUTE', added)
	}
}

export default {
	namespaced: true,
	state,
	mutations,
	actions
}
