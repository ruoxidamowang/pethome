const Layout = () => import('@/layout/index.vue')
const Redirect = () => import('@/views/redirect/index.vue')
const NotFound404 = () => import('@/views/inner/404.vue')
const NotFound401 = () => import('@/views/inner/401.vue')
const Login = () => import('@/views/login/index')
/**

hidden: true // (默认 false)	# 当设置 true 的时候该路由不会在侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
name: 'router-name' 					# 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题

alwaysShow: true							# 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
															# 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
															# 若你想不管路由下面的 children 声明的个数都显示你的根路由
															# 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由

meta: {
  title: 'title' 							# 设置该路由在侧边栏和面包屑中展示的名字
  icon: 'svg-name' 						# 设置该路由的图标，支持 svg-class，也支持 el-icon-x element-ui 的 icon
  noCache: true 							# 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
	noTagsView: true            # 如果设置为true，则不会出现在tag中(默认 false)
  breadcrumb: false  					# 如果设置为false，则不会在breadcrumb面包屑中显示(默认 true)
  affix: true 								# 如果设置为true，则会一直固定在tag项中(默认 false)
  activeMenu: ''							# 显示高亮的路由路径
}
**/

// 定义静态路由
export const staticRoutes = [
  {
    path: '/login',
    hidden: true,
    component: Login,
  },
  {
    path: '/redirect',
    name: 'Redirect',
    component: Redirect,
    hidden: true,
    meta: { noTagsView: true },
  },
  {
    path: '/404',
    hidden: true,
    component: NotFound404,
  },
  {
    path: '/401',
    hidden: true,
    component: NotFound401,
  },
  {
    path: '/:path(.*)*',
    redirect: '/404',
  },
]

// 定义动态路由
export const dynamicRoutes = [
  // {
  // 	path: '/dashboard',
  // 	component: () => import('@/views/dashboard/index'),
  // 	name: 'Dashboard'
  // },
  {
    path: '/dashboard',
    component: Layout,
    redirect: '/dashboard/index',
    name: 'Dashboard',
    children: [
      {
        name: 'Home',
        path: 'index',
        component: () => import('@/views/dashboard/index'),
        meta: { title: '首页', icon: 'el-icon-house', affix: true },
      },
    ],
  },
  {
    path: '/admin',
    component: Layout,
    name: 'Admin',
    meta: {
      title: '组织机构',
      icon: 'el-icon-menu',
    },
    redirect: '/Admin/dept',
    children: [
      {
        path: 'dept',
        component: () => import('@/views/dept/index'),
        name: 'Dept',
        meta: { title: '部门管理', noCache: true ,icon: 'el-icon-guide'},
      },
      {
        path: 'emp',
        component: () => import('@/views/icons/index'),
        name: 'Emp',
        meta: { title: '员工管理', noCache: true ,icon: 'el-icon-s-custom'},
      },
      {
        path: 'role',
        component: () => import('@/views/button/index'),
        name: 'Role',
        meta: { title: '角色管理', noCache: true,icon: 'el-icon-user' },
      },
      {
        path: 'permission',
        component: () => import('@/views/Tree/index'),
        name: 'Permission',
        meta: { title: '权限管理', noCache: true ,icon: 'el-icon-lock'},
      },
      {
        path: 'shop',
        component: () => import('@/views/shop/index'),
        name: 'Shop',
        meta: { title: '店铺管理', noCache: true ,icon: 'el-icon-s-grid'},
      },
      {
        path: 'add',
        component: () => import('@/views/shop/add'),
        name: 'Add',
        meta: { title: '店铺申请', noCache: true ,icon: 'el-icon-s-grid'},
      },
      {
        path: 'menu',
        component: () => import('@/views/table/index'),
        name: 'Menu',
        meta: { title: '菜单管理', noCache: true ,icon: 'el-icon-s-grid'},
      },
      {
        path: 'icon',
        component: () => import('@/views/table/inline-edit-table'),
        name: 'Icon',
        meta: { title: '图标', noCache: true ,icon: 'el-icon-star-on'},
      },
      {
        path: 'test',
        component: () => import('@/views/test/index'),
        name: 'test',
        meta: { title: 'test', noCache: true ,icon: 'el-icon-warning'},
      },
    ],
  },
]

// 动态重定向
export const dynamicRedirect = {
  path: '/',
  hidden: true,
  redirect: '/dashboard',
}
