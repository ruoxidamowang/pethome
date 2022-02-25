import storeLocal from 'storejs'
import { login, logout } from '@/api/user'
import { resetRouter } from '@/router'
import { getToken, removeToken, setToken } from '@/utils/auth'

const state = {
	token: getToken(),
	routeMenu: [],
	menuList: [],
	userInfo: {}
}

const mutations = {
	SET_TOKEN: (state, token) => {
		state.token = token
	},
	SET_MENULIST: (state, menuList) => {
		storeLocal.set('menuList', menuList)
		state.menuList = menuList
	},
	SET_ROUTEMENU: (state, routeMenu) => {
		storeLocal.set('routeMenu', routeMenu)
		state.routeMenu = routeMenu
	},
	SET_USERINFO: (state, userInfo) => {
		storeLocal.set('userInfo', userInfo)
		state.userInfo = userInfo
	}
}

const actions = {
	// user login
	login({ commit }, userInfo) {
		console.log('登陆', userInfo)
		const { username, password } = userInfo
		return new Promise((resolve, reject) => {
			login({ username: username.trim(), password: password })
				.then(response => {
					const { data } = response
					if (!data) {
						reject('Login failed, please Login again.')
					}
					const { token, menuList } = data
					commit('SET_USERINFO', data)
					commit('SET_ROUTEMENU', menuList || [])
					commit('SET_TOKEN', token)
					setToken(token)
					resolve(data)
				})
				.catch(error => {
					reject(error)
				})
		})
	},
	// get user info
	getInfo({ commit, state }) {
		return new Promise((resolve, reject) => {
			try {
				let menuList = state.routeMenu.length > 0 ? state.routeMenu : storeLocal.get('routeMenu')
				commit('SET_MENULIST', menuList)
				resolve({ menuList })
			} catch (error) {
				reject('menuList not in userInfo!', error)
			}
		})
	},
	// user logout
	logout({ commit, state, dispatch }) {
		return new Promise((resolve, reject) => {
			logout(state.token)
				.then(() => {
					commit('SET_TOKEN', '')
					removeToken()
					resetRouter()
					dispatch('tagsView/delAllViews', null, { root: true })
					dispatch('permission/setDynamicAddedRoute', false, { root: true })
					localStorage.clear()
					resolve()
				})
				.catch(error => {
					reject(error)
				})
		})
	},

	// remove token
	resetToken({ commit }) {
		return new Promise(resolve => {
			commit('SET_TOKEN', '')
			removeToken()
			resolve()
		})
	}
}

export default {
	namespaced: true,
	state,
	mutations,
	actions
}
