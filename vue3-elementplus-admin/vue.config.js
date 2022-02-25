'use strict'
const path = require('path')

const resolve = dir => path.join(__dirname, dir)

const { NODE_ENV, VUE_APP_BASE_API, VUE_APP_BASE_TITLE } = process.env

const mockServer = () => {
	if (NODE_ENV === 'development') return require('./mock/mock-server.js')
	else return ''
}

module.exports = {
	publicPath: '/',
	outputDir: 'dist',
	assetsDir: 'assets',
	runtimeCompiler: true, //运行时编译 - 去除自定义指定时候使用组件模板的警告
	lintOnSave: NODE_ENV === 'development', // eslint validation
	productionSourceMap: false,
	devServer: {
		open: true,
		port: 8081, // 8088 端口谷歌浏览器 使用 mockjs 可能存在问题
		overlay: {
			// warning type
			errors: true,
			warnings: false
		},
		proxy: {
			[VUE_APP_BASE_API]: {
				target: 'http://xxx.xxxxx.com/api',
				ws: true,
				secure: false, // 如果是https接口，需要配置这个参数
				changeOrigin: false, // 如果接口跨域，需要进行这个参数配置
				pathRewrite: {
					'^/api': ''
				}
			}
		},
		before: mockServer()
	},
	// css: {
	// 	loaderOptions: {
	// 		scss: {
	// 			additionalData: `@import "~@/styles/variables.scss";`
	// 		}
	// 	}
	// },
	configureWebpack: {
		// provide the app's title in webpack's name field, so that
		// it can be accessed in index.html to inject the correct title.
		name: VUE_APP_BASE_TITLE,
		resolve: {
			alias: {
				'@': resolve('src'),
				_c: resolve('src/components')
			}
		}
	},
	chainWebpack: config => {
		// 配置 svg-sprite-loader
		// 第一步：让其他svg loader不要对src/icons进行操作
		config.module
			.rule('svg')
			.exclude.add(resolve('src/assets/icons/svg')) // 注意：路径要具体到存放的svg的路径下，不然会报错
			.end()
		// 第二步：使用svg-sprite-loader 对 src/icons下的svg进行操作
		config.module
			.rule('icons')
			.test(/\.svg$/)
			.include.add(resolve('src/assets/icons/svg')) // 注意：路径要具体到存放的svg的路径下，不然会报错
			.end()
			.use('svg-sprite-loader')
			.loader('svg-sprite-loader')
			// 定义规则 使用时 <svg class="icon"> <use xlink:href="#icon-svg文件名"></use>  </svg>
			.options({
				symbolId: 'icon-[name]'
			})
			.end()
	}
}
