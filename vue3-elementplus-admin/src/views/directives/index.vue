<template>
	<el-card>
		<el-card>
			<template #header>
				<div><b>1.复制： v-clipboard</b></div>
				<div>
					<b>提示： v-tooltip</b>
				</div>
			</template>
			<div>
				<el-input placeholder="请输入内容" v-model="uniqueString" class="input-with-select">
					<template #prepend>
						<el-button type="primary" v-clipboard="uniqueString">
							复制
						</el-button>
					</template>
				</el-input>
				<div v-tooltip:[tootipParams.content]="tootipParams">
					这段文字最后有提示问号
				</div>
			</div>
		</el-card>
		<el-card>
			<template #header>
				<div><b>2.点击元素之外：v-clickoutside</b></div>
				<div><b>全屏：v-screenfull</b></div>
			</template>
			<el-button v-clickoutside="clickoutside">点击元素之外</el-button>
			<el-button v-screenfull>全屏</el-button>
		</el-card>

		<el-card>
			<template #header>
				<div><b>3.输入限制：v-input</b></div>
				<div><b>文字省略：v-ellipsis</b></div>
			</template>
			<div><span>只能输入数字：</span><input v-input:customize="inputValue" data-rule="/[^\d]/" v-model="inputValue" /></div>
			<div v-ellipsis:100>
				需要省略的文字是阿萨的副本阿萨的副本阿萨的副本阿萨的副本
			</div>
		</el-card>

		<el-card style="height: 3=200px">
			<template #header>
				<div><b>4.图片懒加载：v-lazy-img</b></div>
				<div><b>徽标指令：v-badge</b></div>
			</template>
			<div>
				<img
					v-default-img="'@assets/images/logo.png'"
					width="60"
					height="60"
					src="https://img0.baidu.com/it/u=2960192404,3098240088&fm=26&fmt=auto&gp=0.jpg"
				/>
				<div v-badge.info="6" style="height: 50px; width: 50px; background: #999"></div>
			</div>
		</el-card>

		<el-card style="height: 3=200px">
			<template #header>
				<div>
					<b>5.loading：v-loading</b>
				</div>
				<div>
					<b>防抖节流：v-debounce/v-throttle</b>
				</div>
			</template>

			<div>
				<div style="padding: 50px">
					<button @click="flag = !flag">显示/隐藏loading</button>
					<div v-tbloading="flag" tbloading-text="加载中..." style="width: 100px; height: 100px; border: 1px solid #000">
						一些内容
					</div>
				</div>
				<div>
					防抖<input
						v-model="inputValue2"
						v-throttle="{
							func: debounce,
							event: 'input',
							wait: 500,
							immediate: true
						}"
					/>
				</div>
				<div>
					<el-button
						class="submit"
						v-debounce="{
							func: submitHandle,
							wait: 300,
							params: [10, 20],
							immediate: true
						}"
					>
						节流
					</el-button>
				</div>
			</div>
		</el-card>

		<el-card style="height: 3=200px">
			<template #header>
				<b>6.点击范围扩大：v-expandClick</b>
			</template>
			<div>
				<div
					style="
            width: 100px;
            height: 30px;
            background: #e6a23c;
            font-size: 12px;
          "
					v-expandClick="[20, 30, 40, 50]"
					@click="glabClickoutside"
				>
					点击范围扩大
				</div>
			</div>
		</el-card>

		<el-card style="height: 3=200px">
			<template #header>
				<b>5.拖动：v-draggable</b>
			</template>
			<div v-draggable style="width: 100px; height: 100px; background: skyblue">
				<div>自定义拖动指令</div>
				<div>v-draggable</div>
			</div>
		</el-card>

		<el-button type="text" @click="dialogVisible = true">点击打开 Dialog</el-button>

		<el-dialog title="提示" v-model="dialogVisible" width="30%" :before-close="handleClose">
			<span>这是一段信息</span>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="dialogVisible = false">取 消</el-button>
					<el-button type="primary" @click="dialogVisible = false">确 定</el-button>
				</span>
			</template>
		</el-dialog>
	</el-card>
</template>
<script>
import { createUniqueString } from '@/utils'

import { ref, getCurrentInstance } from 'vue'

export default {
	setup() {
		const { proxy } = getCurrentInstance()
		const dialogVisible = ref(false)
		const uniqueString = ref(createUniqueString())
		const inputValue = ref('')
		const inputValue2 = ref('')
		const flag = ref(false)
		const clipboardSuccessHandler = val => {
			console.log('success', val)
		}
		const tootipParams = {
			placement: 'top',
			effect: 'light',
			content: '提示信息'
		}
		// Error event handler
		const clipboardErrorHandler = val => {
			console.log('error', val)
		}

		const handleClose = done => {
			proxy
				.$confirm('确认关闭？')
				.then(_ => {
					done()
				})
				.catch(_ => {})
		}

		const clickoutside = e => {
			// console.log('非自身点击触发')
		}

		const glabClickoutside = () => {
			console.log('扩大点击范围')
		}

		const debounce = () => {
			console.log('debounce 防抖', inputValue2.value)
		}

		const submitHandle = (a, b) => {
			console.log('submitHandle 节流', a, b)
		}

		return {
			inputValue,
			inputValue2,
			flag,
			dialogVisible,
			uniqueString,
			clipboardSuccessHandler,
			clipboardErrorHandler,
			handleClose,
			clickoutside,
			glabClickoutside,
			tootipParams,
			debounce,
			submitHandle
		}
	}
}
</script>

<style></style>
