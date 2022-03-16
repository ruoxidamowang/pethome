<template>
  <!--高级查询  行内表单-->
  <el-form :inline="true" :model="formInline" class="demo-form-inline">
    <el-form-item>
      <el-input v-model="formInline.name" placeholder="角色名称" clearable></el-input>
    </el-form-item>
    <el-form-item>
      <el-button round type="primary" @click="query">查询</el-button>
    </el-form-item>
    <el-form-item>
      <el-button round type="primary" @click="add">添加</el-button>
    </el-form-item>
  </el-form>

  <!--显示数据-->
  <el-table ref="multipleTableRef" table-layout="fixed" :data="tableData" style="width: 100%">
    <el-table-column prop="id" sortable label="角色编号"/>
    <el-table-column prop="name" label="角色名称"/>
    <el-table-column prop="sn" label="角色介绍"/>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button round size="small" type="success" @click="changePermission(scope.$index, scope.row)">修改权限</el-button>
        <el-button round size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        <el-popconfirm title="确定要删除吗?"
                       confirm-button-text="确定"
                       cancel-button-text="取消"
                       :icon="InfoFilled"
                       icon-color="red"
                       @confirm="handleDelete(scope.$index, scope.row)">
          <template #reference>
            <el-button round size="small" type="danger">删除</el-button>
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
      <el-form-item label="角色名称" :label-width="formLabelWidth">
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="角色介绍" :label-width="formLabelWidth">
        <el-input v-model="form.sn" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">关闭</el-button>
        <el-button type="primary" @click="update(form.row)">保存</el-button>
      </span>
    </template>
  </el-dialog>

  <!--权限修改模态框-->
  <el-dialog v-model="dialogVisible" title="修改权限">
    <div>
      <TBTree
          ref="TBTreeRef"
          :data="projectMenu"
          :activeId="activeProjectMenu.id"
          :defaultExpandedKeys="['1', '2']"
          :props="{
				label: 'name'
			}"
          showCheckbox
          @nodeClick="handleNode"
          @nodeExpand="nodeExpand"
          @checkChange="checkChange"
      >
      </TBTree>
      <el-button type="primary" @click="getCheckedKeys">获取选中节点</el-button>
      <el-button type="primary" @click="setCheckedKeys">设置节点选中</el-button>
      <el-button type="primary" @click="resetChecked">清空选中</el-button>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
        <el-button type="primary" @click="getCheckedKeys">保存</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script>
import {onMounted, reactive, ref} from "vue";
import {addOrEdit, loadAll, remove} from "@/api/role";
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
      currentPage4: '',
      pageSize4: '',
      name: '',
    })
    const state = ref(true)
    const title = ref('')
    const value = ref('')
    const options = ref('')

    const pid = ref('')
    const parent = ref('')

    const dialogFormVisible = ref(false)
    const dialogVisible = ref(false)
    const formLabelWidth = '140px'

    const form = reactive({
      id: '',
      name: '',
      sn: '',
    })

    const multipleTableRef = ref()

    //添加角色
    const add = () => {
      //显示模态框
      dialogFormVisible.value = true

      //改变表单标题
      title.value = '添加角色'

      //清空表单数据
      form.id = ''
      form.name = ''
      form.sn = ''
    }

    //高级查询
    const query = () => loadAll({
      'start': currentPage4.value,
      'pageSize': pageSize4.value,
      'name': formInline.name,
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
      title.value = '编辑角色'

      //将数据回写到模态框表单中
      form.id = row.id
      form.name = row.name
      form.sn = row.sn
    }

    //添加和修改提交
    const update = () => {
      //关闭模态框
      dialogFormVisible.value = false

      //执行添加或修改
      addOrEdit({
        'id': form.id,
        'name': form.name,
        'sn': form.sn,
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
      remove(row.id).then(() => {
        ElMessage.success("删除成功")
        load(currentPage4.value, pageSize4.value)
      }).catch(e => {
        ElMessage.success("删除失败" + e)
      })
    }

    const changePermission = (index, row) => {
      dialogVisible.value = true

    }

    const activeProjectMenu = reactive({
      icon: 'el-icon-office-building',
      menuName: '第一工程处-1-1-2-3-2',
      id: '1-1-2-3-2'
    })

    const projectMenu = ref([
      {
        id: '1',
        name: '第一工程处-1',
        children: [
          {
            name: '第一工程处1-1',
            id: '1-1',
          },
          {
            id: '1-2',
            name: '第一工程处1-2'
          }
        ]
      },
      {
        id: '2',
        name: '第二工程处-2',
        children: [
          {
            id: '2-1',
            name: '第二工程处2-1'
          },
          {
            id: '2-2',
            name: '第二工程处2-2'
          }
        ]
      },
    ])

    const TBTreeRef = ref(null)

    //节点被点击回调
    function handleNode(node) {
      // console.log('点击节点回调--', node)
      activeProjectMenu.path = node.path
      activeProjectMenu.name = node.name
      activeProjectMenu.isActive = node.isActive
      activeProjectMenu.id = node.id
    }

    // 节点被展开回调
    function nodeExpand(expanded, node) {
      // console.log('展开节点回调--', { expanded, node })
    }

    // 节点被选中状态变化回调
    function checkChange(node, checked, indeterminate) {
      // console.log(node, checked, indeterminate)
    }

    // 获取选中节点
    function getCheckedKeys() {
      dialogVisible.value = false
      const nodeKeys = TBTreeRef.value.getCheckedKeys()
      console.log('nodeKeys---', nodeKeys)
    }

    //设置节点选中
    function setCheckedKeys() {
      TBTreeRef.value.setCheckedKeys(['1-1', '2-1', '1-2'])
    }

    // 清空节点
    function resetChecked() {
      TBTreeRef.value.setCheckedKeys([])
    }

    return {
      changePermission,
      title,
      multipleTableRef,
      pid,
      parent,
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
      state,
      update,
      value,
      options,
      tableData,
      formInline,
      query,
      dialogVisible,
      TBTreeRef,
      activeProjectMenu,
      projectMenu,
      handleNode,
      nodeExpand,
      checkChange,
      getCheckedKeys,
      setCheckedKeys,
      resetChecked,
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
