const req = require.context('@/assets/icons/svg', false, /\.svg$/)

const requireAll = (requireContext) => requireContext.keys()

const svgIcons = requireAll(req).map((i) => i.match(/\.\/(.*)\.svg/)[1])

export default svgIcons