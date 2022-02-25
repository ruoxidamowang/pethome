import { requireSvgModules } from '@/assets/icons/index'
import SvgIcon from '@/components/SvgIcon'
import TBTree from '@/components/TBTree'
import TBTable from '@/components/TBTable'
import FilterSearch from '@/components/FilterSearch'
import InlineEditTable from '@/components/InlineEditTable'
import Pagination from '@/components/Pagination'
import Dialog from './Dialog'

requireSvgModules() // 引入svg图标

export function setupGlobCom(app) {
	// register globally
	app.component(SvgIcon.name, SvgIcon)
	app.component(TBTable.name, TBTable)
	app.component(FilterSearch.name, FilterSearch)
	app.component(InlineEditTable.name, InlineEditTable)
	app.component(Pagination.name, Pagination)
	app.component(TBTree.name, TBTree)
	app.component('Dialog', Dialog)
}
