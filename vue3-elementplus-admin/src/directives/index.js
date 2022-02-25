const modulesFiles = require.context('./modules', true, /\.js$/)
const directives = modulesFiles.keys().map(key => modulesFiles(key).default)

export function setupDirectives(app) {
	directives.forEach(directive => {
		typeof directive === 'function'
			? (app.directive('debounce', directive('debounce')), app.directive('throttle', directive('throttle')))
			: app.directive(directive.name, directive)
	})
}
