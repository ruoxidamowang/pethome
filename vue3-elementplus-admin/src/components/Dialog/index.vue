<template>
	<el-dialog
		custom-class="dialog-modal"
		v-model="dialogVisible"
		:width="width"
		:title="title"
		:fullscreen="fullscreen"
		:lock-scroll="lockScroll"
		:close-on-click-modal="closeOnClickModal"
		:destroy-on-close="destroyOnClose"
		:append-to-body="appendToBody"
		:before-close="beforeCloseCallBack"
		@close="closeCallBack"
	>
		<template v-if="isShowTitle" #title>
			<div class="dialog-head">
				<slot name="title"></slot>
			</div>
		</template>
		<div class="container scrollbar-bar">
			<slot></slot>
		</div>
		<div class="dialog-loading" :style="{ 'pointer-events': loading ? 'auto' : 'none' }" v-loading="loading" element-loading-text="加载中"></div>
		<template #footer v-if="showFooterBtn">
			<div class="dialog-footer">
				<slot name="footer">
					<el-button v-if="showCancelButton" @click="handleClose('cancel')" size="small">{{ cancelButtonText }}</el-button>
					<el-button type="primary" @click="handleClose('ok')" size="small">{{ confirmButtonText }}</el-button>
				</slot>
			</div>
		</template>
	</el-dialog>
</template>

<script>
import { nextTick, reactive, ref, watch, watchEffect } from '@vue/runtime-core'
export default {
	emits: ['update:modelValue', 'before-close', 'closeCallBack', 'dynamicSubmit'],
	setup(props, { emit }) {
		const dialogLoding = reactive({
			width: 0,
			height: 0
		})
		const closeDone = ref(props.beforeClose)
		const status = ref(props.dynamicLoading ? 1 : 0)
		const dialogVisible = ref(props.modelValue)

		// 按钮事件
		const handleClose = type => {
			if (status.value === 0 || type === 'cancel') {
				// 确定/取消 -直接关闭
				dialogVisible.value = false
			} else if (status.value === 1) {
				// 异步回调函数
				emit('dynamicSubmit')
			}
		}

		/**
		 * 关闭dialog时回调函数
		 */
		const closeCallBack = () => {
			nextTick(() => {
				status.value = props.dynamicLoading ? 1 : 0
				// 关闭弹框后，还原父组件状态
				emit('update:modelValue', false)
				emit('closeCallBack')
			})
		}

		// 关闭前回调
		const beforeCloseCallBack = done => {
			!closeDone.value ? done() : closeDone.value(done)
		}

		// loading 变化，关闭弹窗
		watch(
			() => props.loading,
			() => {
				props.loading && (status.value = 2)
				!props.loading && status.value === 2 && (dialogVisible.value = false)
			}
		)
		// 初始显示
		watchEffect(() => {
			props.modelValue && (dialogVisible.value = props.modelValue)
		})

		return {
			dialogLoding,
			status,
			closeDone,
			dialogVisible,
			handleClose,
			closeCallBack,
			beforeCloseCallBack
		}
	},
	props: {
		modelValue: {
			type: Boolean,
			default: false
		},
		width: {
			type: String || Number,
			default: '50%'
		},
		title: {
			type: String,
			default: '提示'
		},
		top: {
			type: String,
			default: '10vh'
		},
		fullscreen: {
			type: Boolean,
			default: false
		},
		isShowTitle: {
			type: Boolean,
			default: false
		},
		lockScroll: {
			type: Boolean,
			default: true
		},
		closeOnClickModal: {
			type: Boolean,
			default: false
		},
		destroyOnClose: {
			type: Boolean,
			default: true
		},
		appendToBody: {
			type: Boolean,
			default: false
		},
		showFooterBtn: {
			type: Boolean,
			default: true
		},
		confirmButtonText: {
			type: String,
			default: '确 定'
		},
		cancelButtonText: {
			type: String,
			default: '取 消'
		},
		showCancelButton: {
			type: Boolean,
			default: true
		},
		dynamicLoading: {
			//需要在请求完成关闭弹窗需要设置此属性
			type: Boolean,
			default: false
		},
		loading: {
			type: Boolean,
			default: false
		},
		beforeClose: {
			type: Function
		}
	}
}
</script>

<style lang="scss" scope>
@import '~@/styles/variables.scss';

.dialog-modal {
	// min-width: 840px;
	.dialog-loading {
		width: 100%;
		height: 100%;
		position: absolute;
		top: 0;
		left: 0;
	}
	.el-dialog__header {
		display: flex;
		align-items: center;
		justify-content: space-between;
		height: 36px;
		box-sizing: border-box;
		padding: 0 10px;
		background-color: $j-color-primary;
		.el-dialog__title {
			font-size: 18px;
			color: #fff;
		}
		.el-dialog__headerbtn {
			position: inherit;
		}
		.el-dialog__close {
			width: 16px;
			height: 16px;
			border-radius: 50%;
			text-align: center;
			transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
			transform-origin: 100% 50%;
			background-color: #bbcdff;
			color: #3b83ee;
			cursor: pointer;
			&:before {
				transform: scale(0.8);
				display: inline-block;
			}
			&:hover {
				opacity: 1;
				background-color: #d3dfff;
				color: #486ece;
			}
		}
	}
	.el-dialog__footer {
		text-align: center;
		button {
			width: 100px;
			height: 36px;
			margin-right: 8px;
			border-radius: 18px;
		}
	}
	.container {
		width: 100%;
		//max-height: calc(100vh - 100px);
		max-height: 70vh;
		overflow: hidden auto;
		box-sizing: border-box;
		.row-item {
			margin-bottom: 20px;
		}
		.col-title {
			margin: 18px 0 5px;
		}
	}
	.dialog-head {
		height: 35px;
		line-height: 35px;
		color: #fff;
		background-color: rgba(24, 175, 200, 1);
		padding: 0 16px 0 20px;
	}
}
</style>
