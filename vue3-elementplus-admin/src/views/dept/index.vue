<template>
  <!--高级查询  行内表单-->
  <el-form :inline="true" :model="formInline" class="demo-form-inline">
    <el-form-item>
      <el-input v-model="formInline.name" placeholder="部门名称"></el-input>
    </el-form-item>
    <el-form-item>
      <el-input v-model="formInline.manager" placeholder="部门经理"></el-input>
    </el-form-item>
    <el-form-item>
      <el-select v-model="formInline.state">
        <el-option label="请选择状态" value=""></el-option>
        <el-option label="禁用" value="0"></el-option>
        <el-option label="启用" value="1"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="query">查询</el-button>
    </el-form-item>
  </el-form>

  <!--显示数据-->
  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="id" label="部门编号"/>
    <el-table-column prop="name" label="部门名称"/>
    <el-table-column prop="sn" label="部门介绍"/>
    <el-table-column prop="dirPath" label="部门路径"/>
    <el-table-column prop="state" label="部门状态">
      <template #default="scope">
        <el-button v-if="scope.row.state" style="background-color:green;color: white">启用</el-button>
          <el-button v-else style="background-color:red;color: white">禁用</el-button>
      </template>
    </el-table-column>
    <el-table-column prop="manager.username" label="部门经理"/>
    <el-table-column prop="parent.name" label="上级部门"/>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        <el-popconfirm title="确定要删除吗?"
                       confirm-button-text="确定"
                       cancel-button-text="取消"
                       :icon="InfoFilled"
                       icon-color="red"
                       @confirm="handleDelete(scope.$index, scope.row)">
          <template #reference>
            <el-button size="small" type="danger">删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>

  <!-- 分页 -->
  <div class="demo-pagination-block">
    <el-pagination
        v-model:currentPage="currentPage4"
        v-model:page-size="pageSize4"
        :page-sizes="[5, 10, 20, 50]"
        :small="small"
        :disabled="disabled"
        :background="background"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    >
    </el-pagination>
  </div>

  <!--添加和修改模态框-->
  <el-dialog v-model="dialogFormVisible" title="修改部门">
    <el-form :model="form">
      <el-input type="hidden" v-model="form.id"></el-input>
      <el-form-item label="部门名称" :label-width="formLabelWidth">
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="部门介绍" :label-width="formLabelWidth">
        <el-input v-model="form.sn" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="部门路径" :label-width="formLabelWidth">
        <el-input v-model="form.dirPath" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="部门状态" :label-width="formLabelWidth">
        <el-switch v-model="state" inline-prompt active-text="启用" inactive-text="禁用"></el-switch>
      </el-form-item>

      <el-form-item label="部门经理" :label-width="formLabelWidth">
        <el-select v-model="value" class="m-2" placeholder="部门经理">
          <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.username"
              :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="上级部门" :label-width="formLabelWidth">
        <el-select v-model="value" class="m-2" placeholder="上级部门">
          <el-option
              v-for="item in parent"
              :key="item.value"
              :label="item.name"
              :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>

    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">关闭</el-button>
        <el-button type="primary" @click="update(form.row)"
        >保存</el-button
        >
      </span>
    </template>
  </el-dialog>

</template>
<script>
import {onMounted, reactive, ref} from "vue";
import {addOrEdit, loadAll, loadAllEmp, remove} from "@/api/dept";
import {ElMessage} from "element-plus";

export default {
  setup() {
    const currentPage4 = ref(1)
    const total = ref(0)
    const pageSize4 = ref(10)
    const small = ref(false)
    const background = ref(true)
    const disabled = ref(false)
    const tableData = ref()
    const formInline = reactive({
      currentPage4:'',
      pageSize4:'',
      name: '',
      manager:'',
      state:'',
    })
    const state = ref(true)

    const value = ref('')
    const options = ref('')

    const dialogFormVisible = ref(false)
    const formLabelWidth = '140px'

    const form = reactive({
      id: '',
      name: '',
      sn: '',
      dirPath: '',
      state: state,
      manager: reactive({
        id: '',
        username: '',
      }),
      parent: reactive({
        id: '',
        name: '',
      }),
    })

    //高级查询
    const query = () => loadAll({'start': currentPage4.value, 'pageSize': pageSize4.value,'name':formInline.name,'manager_id':formInline.manager,'state':formInline.state}).then(res => {
      total.value = res.data.total
      tableData.value = res.data.list
    }).catch(e => {
      console.log(e)
    })

    //查询所有并分页
    const load = (start, pageSize) => loadAll({'start': start, 'pageSize': pageSize,'name':formInline.name,'manager_id':formInline.manager,'state':formInline.state}).then(res => {
      total.value = res.data.total
      tableData.value = res.data.list
    }).catch(e => {
      console.log(e)
    })

    //改变每页显示数据数量
    const handleSizeChange = (size) => {
      load(currentPage4.value, size)
    }

    //切换页
    const handleCurrentChange = (currentPage) => {
      load(currentPage, pageSize4.value)
    }

    //加载数据
    onMounted(() => {
      load(currentPage4.value, pageSize4.value)
    })

    //编辑回写
    const handleEdit = (index, row) => {
      dialogFormVisible.value = true

      form.id = row.id
      form.name = row.name
      form.sn = row.sn
      form.dirPath = row.dirPath
      form.state = row.state ? false : true

      form.manager = row.manager?row.manager.username:'无'
      form.parent = row.parent?row.parent.name:'无'

      //查询所有经理显示在下拉框中
      loadAllEmp().then(res => {
        options.value = res.data
        value.value = row.manager.id
      })
    }

    //编辑提交事件
    const update = () => {
      dialogFormVisible.value = false

      console.log(form)

      addOrEdit({
        'id': form.id,
        'name': form.name,
        'sn': form.sn,
        'dirPath': form.dirPath,
        'state': form.state ? 0 : 1,
        'manager': reactive({'id': value.value}),
        'parent': reactive({'id': form.parent.id}),
      }).then(res => {
        if (res.success) {
          ElMessage.success("修改成功")
        } else {
          ElMessage.success("修改失败")
        }
      }).catch(e => {
        ElMessage.success("修改失败" + e)
      })
    }

    //删除事件
    const handleDelete = (index, row) => {
      remove(row.id).then(res=>{
        ElMessage.success("删除成功")
      }).catch(e=>{
        ElMessage.success("删除失败" + e)
      })
    }

    return {
      currentPage4,
      pageSize4,
      small,
      background,
      disabled,
      total,
      handleEdit,
      handleDelete,
      handleSizeChange,
      handleCurrentChange,
      dialogFormVisible,
      formLabelWidth,
      form,
      state,
      update,
      value,
      options,
      tableData,
      formInline,
      query,
    }
  }
}
</script>

<style scoped>
.demo-pagination-block + .demo-pagination-block {
  margin-top: 10px;
}

.demo-pagination-block {
  margin-top: 16px;
  text-align: center;
}
</style>
