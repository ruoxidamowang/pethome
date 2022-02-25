import { createStore } from 'vuex'
import getters from './getters'

// 自动注入所有 ./modules 下的 vuex 子模块
const modulesFiles = require.context('./modules', false, /\.js$/)
const modules = {}
modulesFiles.keys().forEach(key => {
	modules[key.replace(/(\.\/|\.js)/g, '')] = modulesFiles(key).default
})

const store = createStore({
	modules,
	getters
})

export function setupStore(app) {
	app.use(store)
}

export default store
