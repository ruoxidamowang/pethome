<template>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="productNo" label="编号" width="180" />
    <el-table-column prop="createUserCode" label="姓名" width="180" />
    <el-table-column prop="addr" label="地址" />
  </el-table>
</template>
<script>
import {onMounted, ref} from "vue";
import {loadAll} from "@/api/test";

export default {
	setup() {
    const tableData = ref()
    onMounted(()=>{
      loadAll().then(res=>{
        console.log(res.data)
        tableData.value = res.data
      })
    })
    return {
      tableData
    }
  }
}
</script>

<style></style>
