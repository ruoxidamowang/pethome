<template>
  <!--高级查询  行内表单-->
  <el-form :inline="true" :model="formInline" class="demo-form-inline">
    <el-form-item>
      <el-button type="danger" @click="removeSelection">批量删除</el-button>
    </el-form-item>
    <el-form-item>
      <el-input v-model="formInline.name" placeholder="宠物名称" clearable></el-input>
    </el-form-item>
    <el-form-item>
      <el-select v-model="formInline.state" clearable>
        <el-option label="请选择状态" value=""></el-option>
        <el-option label="待领养" value="0"></el-option>
        <el-option label="已领养" value="1"></el-option>
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
    <el-table-column prop="id" label="宠物编号" sortable/>
    <el-table-column prop="name" label="宠物名称"/>
    <el-table-column prop="age" label="宠物年龄" sortable/>
    <el-table-column prop="gender" label="宠物性别">
      <template #default="scope">
        <label v-if="scope.row.gender===1" style="color: blue">公</label>
        <label v-else style="color: pink">母</label>
      </template>
    </el-table-column>
    <el-table-column prop="coatColor" label="宠物颜色"/>
<!--    <el-table-column prop="resources" label="宠物照片"/>-->
    <el-table-column prop="petType.name" label="宠物类型"/>
    <el-table-column prop="price" label="宠物价格" sortable/>
    <el-table-column prop="address" label="宠物地址"/>
    <el-table-column prop="state" label="宠物状态" sortable>
      <template #default="scope">
        <label v-if="scope.row.state===0" style="color: green">待领养</label>
        <label v-else style="color: red">已领养</label>
      </template>
    </el-table-column>
    <el-table-column prop="title" label="标题"/>
    <el-table-column prop="user.username" label="发布者姓名"/>
    <el-table-column prop="shop.name" label="店铺名字"/>
    <el-table-column fixed="right" label="操作" width="210px">
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
  <el-dialog v-model="dialogFormVisible" width="1000px" :title="title">
    <el-form :model="form">
      <el-input type="hidden" v-model="form.id"></el-input>
      <el-form-item label="宠物名称" :label-width="formLabelWidth">
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="宠物资源" :label-width="formLabelWidth">
        <el-input v-model="form.resources" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="宠物售价" :label-width="formLabelWidth">
        <el-input v-model="form.saleprice" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="宠物销量" :label-width="formLabelWidth">
        <el-input v-model="form.salecount" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="下架时间" :label-width="formLabelWidth">
        <el-input v-model="form.offsaletime" autocomplete="off" disabled></el-input>
      </el-form-item>
      <el-form-item label="上架时间" :label-width="formLabelWidth">
        <el-input v-model="form.onsaletime" autocomplete="off" disabled></el-input>
      </el-form-item>
      <el-form-item label="宠物状态" :label-width="formLabelWidth">
        <el-select v-model="form.state" class="m-2" placeholder="宠物状态">
          <el-option
              v-for="item in states"
              :key="item.id"
              :label="item.value"
              :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="成本价" :label-width="formLabelWidth">
        <el-input v-model="form.costprice" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="简介" :label-width="formLabelWidth">
        <v-md-editor :disabled-menus="[]"
                     @upload-image="handleUploadImage"
                     v-model="form.productDetail.intro"
                     autocomplete="off"></v-md-editor>
      </el-form-item>
      <el-form-item label="预约须知" :label-width="formLabelWidth">
        <v-md-editor :disabled-menus="[]"
                     @upload-image="handleUploadImage"
                     v-model="form.productDetail.orderNotice"
                     autocomplete="off"></v-md-editor>
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
import {addOrEdit, loadAll, removeAll} from "@/api/petsearch";

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
      resources: '',
      saleprice: '',
      offsaletime: '',
      onsaletime: '',
      state: '',
      costprice: '',
      createtime: '',
      salecount: '',
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
          '确认删除所选宠物吗？',
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

    //添加宠物
    const add = () => {
      //显示模态框
      dialogFormVisible.value = true

      //改变表单标题
      title.value = '添加宠物'

      //清空表单数据
      form.id = ''
      form.name = ''
      form.resources = ''
      form.saleprice = ''
      form.offsaletime = ''
      form.onsaletime = ''
      form.state = ''
      form.costprice = ''
      form.salecount = ''
      form.productDetail.intro = ''
      form.productDetail.orderNotice = ''
    }

    //高级查询
    const query = () => loadAll({
      'start': currentPage4.value,
      'pageSize': pageSize4.value,
      'name': formInline.name,
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
      title.value = '编辑宠物'

      //将数据回写到模态框表单中
      form.id = row.id
      form.name = row.name
      form.resources = row.resources
      form.saleprice = row.saleprice
      form.offsaletime = row.offsaletime
      form.onsaletime = row.onsaletime
      form.state = row.state
      form.costprice = row.costprice
      form.salecount = row.salecount
      form.productDetail.intro = row.productDetail.intro
      form.productDetail.orderNotice = row.productDetail.orderNotice
    }

    //添加和修改提交
    const update = () => {
      //关闭模态框
      dialogFormVisible.value = false

      //执行添加或修改
      addOrEdit({
        'id': form.id,
        'name': form.name,
        'resources': form.resources,
        'state': form.state,
        'saleprice': form.saleprice,
        'offsaletime': form.offsaletime,
        'onsaletime': form.onsaletime,
        'costprice': form.costprice,
        'salecount': form.salecount,
        'productDetail': {
          "id": form.id,
          "intro": form.productDetail.intro,
          "orderNotice": form.productDetail.orderNotice
        }
      }).then(res => {
        if (res.success) {
          console.log(res)
          ElMessage.success(res.msg)
          load(currentPage4.value, pageSize4.value)
        } else {
          ElMessage.error(res.msg)
        }
      }).catch(e => {
        ElMessage.error("修改失败-" + e)
      })
    }

    //删除事件
    const handleDelete = (index, row) => {
      remove(row.id/*,row.logo*/).then(res => {
        ElMessage.success(res.msg)
        load(currentPage4.value, pageSize4.value)
      }).catch(e => {
        ElMessage.error("删除失败-" + e.msg)
      })
    }

    //审核事件
    const handlePass = (index, row) => {
      //修改状态为通过
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
      {id: 0, value: '下架'},
      {id: 1, value: '上架'},
    ])

    const handleUploadImage = (event, insertImage, files)=>{
      // 拿到 files 之后上传到文件服务器，然后向编辑框中插入对应的内容
      console.log(files);

      // 此处只做示例
      insertImage({
        url:
            'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1269952892,3525182336&fm=26&gp=0.jpg',
        desc: '七龙珠',
        // width: 'auto',
        // height: 'auto',
      });
    }

    return {
      handleUploadImage,
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
