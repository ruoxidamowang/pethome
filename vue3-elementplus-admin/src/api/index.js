const devMockURL = '/dev-api' // mock测试接口地址
const releaseMockURL = '/prod-api' // mock打包接口地址

const baseURL = process.env.NODE_ENV === 'development' ? devMockURL : releaseMockURL

module.exports = { devMockURL, releaseMockURL, baseURL }
