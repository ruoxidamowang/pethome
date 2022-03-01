<template>
  <img src="http://34.80.47.178/group1/M00/00/00/CowAB2IdyFqAJpQ5AAm5oPb8HVQ02..jpg" width="600" height="360">
  <el-form
      ref="ruleFormRef"
      :model="ruleForm"
      :rules="rules"
      label-width="120px"
      class="demo-ruleForm"
      :size="formSize"
  >
    <h1 style="text-align: center">注册店长信息</h1>
    <el-form-item label="店长姓名" prop="username">
      <el-input v-model="ruleForm.username" show-word-limit clearable></el-input>
    </el-form-item>
    <el-form-item label="店长邮箱" prop="email">
      <el-input v-model="ruleForm.email" clearable></el-input>
    </el-form-item>
    <el-form-item label="联系电话" prop="phone">
      <el-input v-model="ruleForm.phone" clearable></el-input>
    </el-form-item>
    <el-form-item label="密&emsp;&emsp;码" prop="password">
      <el-input v-model="ruleForm.password" type="password" show-password clearable></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="repassword">
      <el-input v-model="ruleForm.repassword" type="password" show-password clearable></el-input>
    </el-form-item>
    <h1 style="text-align: center">注册商铺信息</h1>
    <el-form-item label="店铺名称" prop="name">
      <el-input v-model="ruleForm.name" clearable></el-input>
    </el-form-item>
    <el-form-item label="店铺电话" prop="tel">
      <el-input v-model="ruleForm.tel" clearable></el-input>
    </el-form-item>
    <el-form-item label="店铺logo" prop="logo">
      <el-input v-model="ruleForm.logo"></el-input>
    </el-form-item>
    <el-form-item label="店铺地址" prop="address">
      <el-input v-model="ruleForm.address"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="success" @click="submitForm(ruleFormRef)">注册</el-button>
      <el-button type="danger" @click="resetForm(ruleFormRef)">重置</el-button>
      <el-button type="warning" @click="back">返回</el-button>
    </el-form-item>
  </el-form>
</template>
<script>

import {reactive, ref} from "vue";
import router from "@/router";
import {addOrEdit} from "@/api/shop";
import {ElMessage} from "element-plus";

export default {
  setup() {
    const formSize = ref('')
    const ruleFormRef = ref()
    const ruleForm = reactive({
      name: '',
      tel: '',
      logo: '',
      address: '',
      admin: reactive({
        username: '',
        email: '',
        phone: '',
        password: '',
      }),
    })

    const rules = reactive({
      name: [
        {
          required: true,
          message: '请输入店铺名称',
          trigger: 'blur',
        },
        {
          min: 3,
          max: 16,
          message: '长度应该在3-16之间',
          trigger: 'blur',
        },
      ],
      username: [
        {
          required: true,
          message: '请输入用户名',
          trigger: 'blur',
        },
        {
          min: 3,
          max: 16,
          message: '长度应该在3-16之间',
          trigger: 'blur',
        },
      ],
      tel: [
        {
          required: true,
          message: '请输入店铺联系电话',
          trigger: 'blur',
        },
        {
          min: 6,
          max: 11,
          message: '长度应该在6-11之间',
          trigger: 'blur',
        },
      ],
      phone: [
        {
          required: true,
          message: '请输入联系电话',
          trigger: 'blur',
        },
        {
          min: 6,
          max: 11,
          message: '长度应该在6-11之间',
          trigger: 'blur',
        },
      ],
      email: [
        {
          required: true,
          message: '请输入联系邮箱',
          trigger: 'blur',
        },
        {
          min: 6,
          max: 30,
          message: '长度应该在6-30之间',
          trigger: 'blur',
        },
      ],
      password: [
        {
          required: true,
          message: '请输入密码',
          trigger: 'blur',
        },
        {
          min: 4,
          max: 16,
          message: '长度应该在4-16之间',
          trigger: 'blur',
        },
      ],
      repassword: [
        {
          required: true,
          message: '请输入确认密码',
          trigger: 'blur',
        },
      ],
      logo: [
        {
          required: true,
          message: '请输入店铺logo',
          trigger: 'blur',
        },
        {
          min: 3,
          max: 16,
          message: '长度应该在3-16之间',
          trigger: 'blur',
        },
      ],
      address: [
        {
          required: true,
          message: '请输入店铺地址',
          trigger: 'blur',
        },
        {
          min: 3,
          max: 16,
          message: '长度应该在3-16之间',
          trigger: 'blur',
        },
      ],
    })

    const submitForm = (formEl) => {
      if (!formEl) return
      formEl.validate((valid) => {
        if (valid) {
          addOrEdit({
            name: ruleForm.name,
            tel: ruleForm.tel,
            logo: ruleForm.logo,
            address: ruleForm.address,
            admin: reactive({
              username: ruleForm.username,
              email: ruleForm.email,
              phone: ruleForm.phone,
              password: ruleForm.password,
            })
          }).then(res => {
              if (res.success) {
                ElMessage.success("注册成功！")
                router.replace({path: '/login'})
              } else {
                ElMessage.error("注册失败！")
              }
            }).catch(e => {
              ElMessage.error("注册失败！" + e)
            })
          }
        else
          {
            return false
          }
        }
      )
      }

      const resetForm = (formEl) => {
        if (!formEl) return
        formEl.resetFields()
      }

      const back = () => {
        router.replace({path: '/login'})
      }

      return {
        formSize,
        ruleForm,
        ruleFormRef,
        rules,
        submitForm,
        resetForm,
        back,
      }
    }
  }
</script>

<style scoped>

</style>
