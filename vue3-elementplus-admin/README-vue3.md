# Vue2 To Vue 3

## 生命周期

- beforeCreate -> use setup()
- created -> use setup()
- beforeMount -> onBeforeMount
- mounted -> onMounted
- beforeUpdate -> onBeforeUpdate
- updated -> onUpdated
- beforeDestroy -> onBeforeUnmount
- destroyed -> onUnmounted
- errorCaptured -> onErrorCaptured

新增了调试用的钩子函数

- onRenderTracked
- onRenderTriggered

## mapGetters
