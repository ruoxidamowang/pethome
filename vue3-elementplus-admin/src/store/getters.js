const getters = {
	sidebar: state => state.app.sidebar,
	size: state => state.app.size,
	device: state => state.app.device,
	visitedViews: state => state.tagsView.visitedViews,
	cachedViews: state => state.tagsView.cachedViews,
	token: state => state.user.token,
	menuList: state => state.user.menuList,
	permission_routes: state => state.permission.routes,
	isDynamicAddedRoute: state => state.permission.isDynamicAddedRoute,
	errorLogs: state => state.errorLog.logs
}
export default getters
