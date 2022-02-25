<template>
	<el-tooltip :content="content" :placement="placement" :effect="effect">
		<i class="el-icon-question" style="margin:0 10px;font-size:24px;"></i>
	</el-tooltip>
</template>

<script>
import { ElTooltip } from 'element-plus'
export default {
	props: ['content', 'placement', 'effect'],
	components: { ElTooltip }
}
</script>

<style></style>
