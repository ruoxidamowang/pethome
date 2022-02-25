<template>
	<div class="dashboard-container">
		<!-- <component :is="currentRole" /> -->
		<adminDashboard></adminDashboard>
	</div>
</template>

<script>
import adminDashboard from './admin'
// import editorDashboard from './editor'
export default {
	name: 'Dashboard',
	components: { adminDashboard },
	data() {
		return {
			currentRole: 'adminDashboard'
		}
	}
}
</script>
