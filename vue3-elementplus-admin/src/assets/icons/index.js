
const req = require.context('./svg', false, /\.svg$/)

const requireAll = (requireContext) => requireContext.keys()

const svgIcons = requireAll(req).map((i) => i.match(/\.\/(.*)\.svg/)[1])

const svgModules = (requireContext) => requireContext.keys().map(requireContext)

export const requireSvgModules = ()=>svgModules(req)

export default svgIcons
