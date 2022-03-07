<template>
  <!--高级查询  行内表单-->
  <el-form :inline="true" :model="formInline" class="demo-form-inline">
    <el-form-item>
      <el-button type="danger" @click="removeSelection">批量删除</el-button>
    </el-form-item>
    <el-form-item>
      <el-input v-model="formInline.name" placeholder="产品名称" clearable></el-input>
    </el-form-item>
    <el-form-item>
      <el-input v-model="formInline.username" placeholder="产品经理" clearable></el-input>
    </el-form-item>
    <el-form-item>
      <el-select v-model="formInline.state">
        <el-option label="请选择状态" value=""></el-option>
        <el-option label="禁用" value="0"></el-option>
        <el-option label="启用" value="1"></el-option>
        <el-option label="待审核" value="2"></el-option>
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
    <el-table-column prop="id" label="产品编号" sortable/>
    <el-table-column prop="name" label="产品名称"/>
    <el-table-column prop="costprice" label="产品成本" sortable/>
    <el-table-column prop="saleprice" label="产品售价" sortable/>
    <el-table-column prop="salecount" label="产品销量" sortable/>
    <el-table-column prop="state" label="产品状态" sortable>
      <template #default="scope">
        <label v-if="scope.row.state===1" style="color: green">启用</label>
        <label v-else-if="scope.row.state===0" style="color: red">禁用</label>
        <label v-else style="color: orange">待审核</label>
      </template>
    </el-table-column>
    <el-table-column prop="onsaletime" label="上架时间" sortable/>
    <el-table-column prop="offsaletime" label="下架时间" sortable/>
    <el-table-column label="操作" width="210px">
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
  <el-dialog v-model="dialogFormVisible" :title="title">
    <el-form :model="form">
      <el-input type="hidden" v-model="form.id"></el-input>
      <el-form-item label="产品名称" :label-width="formLabelWidth">
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="产品电话" :label-width="formLabelWidth">
        <el-input v-model="form.tel" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="产品状态" :label-width="formLabelWidth">
        <el-select v-model="form.state" class="m-2" placeholder="产品状态">
          <el-option
              v-for="item in states"
              :key="item.id"
              :label="item.value"
              :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="产品地址" :label-width="formLabelWidth">
        <el-input v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="管理人员" :label-width="formLabelWidth">
        <el-input v-model="form.admin.username" autocomplete="off"></el-input>
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
import {ElMessage, ElMessageBox} from "element-plus";
import {addOrEdit, loadAll, delShop, removeAll} from "@/api/product";

export default {
  setup() {
    const currentPage4 = ref(1)
    const total = ref(0)
    const pageSize4 = ref(10)
    const small = ref(false)
    const background = ref(true)
    const disabled = ref(false)
    const tableData = ref('')
    const formInline = reactive({
      currentPage4: '',
      pageSize4: '',
      name: '',
      username: '',
      state: '',
    })
    const title = ref('')

    const srcList = ref([])

    const dialogFormVisible = ref(false)
    const formLabelWidth = '140px'

    const form = reactive({
      id: '',
      name: '',
      resources:'',
      saleprice:'',
      offsaletime:'',
      onsaletime:'',
      state:'',
      costprice:'',
      createtime:'',
      salecount:'',
      productDetail: reactive({
        id: '',
        intro: '',
        orderNotice: '',
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
          '确认删除所选产品吗？',
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

    //添加产品
    const add = () => {
      //显示模态框
      dialogFormVisible.value = true

      //改变表单标题
      title.value = '添加产品'

      //清空表单数据
      form.id = ''
      form.name = ''
      form.tel = ''
      form.state = ''
      form.address = ''
      form.logo = ''
      form.admin.id = ''
      form.admin.username = ''
    }

    //高级查询
    const query = () => loadAll({
      'start': currentPage4.value,
      'pageSize': pageSize4.value,
      'name': formInline.name,
      'admin': {'username': formInline.username},
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
      'manager': {'username': formInline.username},
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
      //显示编辑模态框
      dialogFormVisible.value = true

      //设置模态框标题
      title.value = '编辑产品'

      //将数据回写到模态框表单中
      form.id = row.id
      form.name = row.name
      form.tel = row.tel
      form.registerTime = row.registerTime
      form.state = row.state
      form.address = row.address
      form.logo = row.logo
      form.admin.id = row.admin.id
      form.admin.username = row.admin.username
    }

    //添加和修改提交
    const update = () => {
      //关闭模态框
      dialogFormVisible.value = false

      //执行添加或修改
      addOrEdit({
        'id': form.id,
        'name': form.name,
        'tel': form.tel,
        'state': form.state,
        'address': form.address,
        'logo': form.logo,
        'admin': {"id": form.admin.id}
      }).then(res => {
        if (res.success) {
          ElMessage.success("修改成功")
          load(currentPage4.value, pageSize4.value)
        } else {
          ElMessage.error("修改失败" + res.msg)
        }
      }).catch(e => {
        ElMessage.error("修改失败" + e)
      })
    }

    //删除事件
    const handleDelete = (index, row) => {
      delShop(row.id,row.logo).then(res => {
        ElMessage.success(res.msg)
        load(currentPage4.value, pageSize4.value)
      }).catch(e => {
        ElMessage.success("删除失败" + e)
      })
    }

    //审核事件
    const handlePass = (index, row) => {
      //修改状态为启用
      addOrEdit({
        'id': row.id,
        'name': row.name,
        'tel': row.tel,
        'state': 1,
        'address': row.address,
        'logo': row.logo,
        'admin': {"id": row.admin.id}
      }).then(res => {
        if (res.success) {
          ElMessage.success("通过成功")
          load(currentPage4.value, pageSize4.value)
        } else {
          ElMessage.error("通过失败" + res.msg)
        }
      }).catch(e => {
        ElMessage.error("通过失败" + e)
      })
    }

    const states = ref([
      {id: 0, value: '禁用'},
      {id: 1, value: '启用'},
      {id: 2, value: '待审核'},
    ])

    return {
      states,
      handlePass,
      removeSelection,
      title,
      multipleTableRef,
      handleSelectionChange,
      add,
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
      update,
      tableData,
      formInline,
      query,
      srcList,
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
