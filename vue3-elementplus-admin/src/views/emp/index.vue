<template>
  <!--高级查询  行内表单-->
  <el-form :inline="true" :model="formInline" class="demo-form-inline">
    <el-form-item>
      <el-button type="danger" @click="removeSelection">批量删除</el-button>
    </el-form-item>
    <el-form-item>
      <el-input v-model="formInline.username" placeholder="员工姓名" clearable></el-input>
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
    <el-form-item>
      <el-button type="primary" @click="add">添加</el-button>
    </el-form-item>
  </el-form>

  <!--显示数据-->
  <el-table ref="multipleTableRef" table-layout="fixed" :data="tableData" @selection-change="handleSelectionChange"
            style="width: 100%">
    <el-table-column type="selection"/>
    <el-table-column prop="id" sortable label="编号"/>
    <el-table-column prop="username" label="姓名"/>
    <el-table-column prop="email" label="邮箱"/>
    <el-table-column prop="phone" label="电话"/>
    <el-table-column prop="age" label="年龄"/>
    <el-table-column prop="state" label="状态">
      <template #default="scope">
        <label v-if="scope.row.state" style="color: green">启用</label>
        <label v-else style="color: red">禁用</label>
      </template>
    </el-table-column>
    <el-table-column prop="department.name" label="所属部门"/>
    <el-table-column prop="shop.name" label="所属店铺"/>
    <el-table-column prop="role.name" label="角色"/>
    <el-table-column label="操作" width="200px">
      <template #default="scope">
        <el-button size="small" @click="handleEdit(scope.$index, scope.row)">修改角色</el-button>
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
  <el-dialog v-model="dialogFormVisible" :title="title">
    <el-form :model="form">
      <el-input type="hidden" v-model="form.id"></el-input>
      <el-form-item label="员工名称" :label-width="formLabelWidth">
        <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="员工经理" :label-width="formLabelWidth">
        <el-select v-model="value" class="m-2" placeholder="员工角色">
          <el-option
              v-for="item in options"
              :key="item.id"
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
        <el-button type="primary" @click="update(form.row)">保存</el-button>
      </span>
    </template>
  </el-dialog>

</template>
<script>
import {onMounted, reactive, ref} from "vue";
import {changeRole, loadAll, loadAllRole, remove, removeAll} from "@/api/emp";
import {ElMessage, ElMessageBox} from "element-plus";

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
      currentPage4: '',
      pageSize4: '',
      username: '',
      state: '',
    })
    const state = ref(true)
    const title = ref('')
    const value = ref('')
    const options = ref('')

    const pid = ref('')
    const parent = ref('')

    const dialogFormVisible = ref(false)
    const formLabelWidth = '140px'

    const form = reactive({
      id: '',
      username: '',
      role: reactive({
        id: '',
        name: '',
      }),
    })

    const multipleTableRef = ref()
    const multipleSelection = ref()
    const ids = ref([])

    //批量删除
    const removeSelection = () => {
      ids.value = []
      multipleSelection.value.forEach(dept => {
        ids.value.push(dept.id)
      })

      ElMessageBox.confirm(
          '确认删除所选员工吗？',
          '批量删除',
          {
            confirmButtonText: '确定',
            cancelButtonText: '关闭',
            type: 'warning',
          }
      ).then(() => {
        removeAll(ids.value).then(res => {
          if (res.success) {
            ElMessage.success("删除成功")
            load(currentPage4.value, pageSize4.value)
          } else {
            ElMessage.error("删除失败")
            load(currentPage4.value, pageSize4.value)
            multipleSelection.value = ''
            ids.value = []
          }
        }).catch(e => {
          ElMessage.error("删除失败" + e)
          load(currentPage4.value, pageSize4.value)
          multipleSelection.value = ''
          ids.value = []
        })
      }).catch(() => {
        ElMessage.info("取消删除成功")
        load(currentPage4.value, pageSize4.value)
        multipleSelection.value = ''
        ids.value = []
      })
    }

    //获取所有选中的对象
    const handleSelectionChange = (val) => {
      multipleSelection.value = val
    }

    //高级查询
    const query = () => loadAll({
      'start': currentPage4.value,
      'pageSize': pageSize4.value,
      'username': formInline.username,
      'state': formInline.state
    }).then(res => {
      total.value = res.data.total
      tableData.value = res.data.list
    }).catch(e => {
      console.log(e)
    })

    //查询所有并分页
    const load = (start, pageSize) => loadAll({
      'start': start,
      'pageSize': pageSize,
      'name': formInline.name,
      'manager': {'username': formInline.manager},
      'state': formInline.state
    }).then(res => {
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
      form.id= ''
      form.username= ''
      form.role.id = ''
      form.role.name = ''
      value.value = ''
      //显示编辑模态框
      dialogFormVisible.value = true

      //设置模态框标题
      title.value = '更改员工角色'

      //将数据回写到模态框表单中
      form.id = row.id
      form.username = row.username

      //查询所有角色显示在下拉框中
      loadAllRole().then(res => {
        options.value = res.data
        value.value = row.role.id
      })
    }

    //添加和修改提交
    const update = () => {
      //关闭模态框
      dialogFormVisible.value = false

      //执行添加或修改
      changeRole({
        'id': form.id,
        'username': form.username,
        'role': reactive({'id': value.value}),
      }).then(res => {
        if (res.success) {
          ElMessage.success(res.msg)
          load(currentPage4.value, pageSize4.value)
        } else {
          ElMessage.error(res.msg)
        }
      }).catch(e => {
        ElMessage.error("修改失败" + e)
      })
    }

    //删除事件
    const handleDelete = (index, row) => {
      remove(row.id).then(() => {
        ElMessage.success("删除成功")
        load(currentPage4.value, pageSize4.value)
      }).catch(e => {
        ElMessage.success("删除失败" + e)
      })
    }

    return {
      removeSelection,
      title,
      multipleTableRef,
      handleSelectionChange,
      pid,
      parent,
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
