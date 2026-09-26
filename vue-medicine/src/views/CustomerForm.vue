<template>
  <div class="customer-form-container">
    <el-page-header @back="$router.push('/customers')" title="返回列表">
      <template #content>
        <span class="page-title">{{ isEdit ? '编辑客户' : '新增客户' }}</span>
      </template>
    </el-page-header>

    <el-card class="mt-4">
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="120px">
        <el-form-item label="客户编号" prop="customer_id">
          <el-input v-model="formData.customer_id" placeholder="请输入客户编号（留空自动生成）" clearable />
        </el-form-item>
        <el-form-item label="客户名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入客户姓名/企业名称" clearable />
        </el-form-item>
        <el-form-item label="客户类型" prop="type">
          <el-select v-model="formData.type" placeholder="请选择客户类型" style="width: 100%">
            <el-option label="个人" value="个人" />
            <el-option label="企业" value="企业" />
          </el-select>
        </el-form-item>
        <el-form-item label="联系电话" prop="contact_phone">
          <el-input v-model="formData.contact_phone" placeholder="请输入 11 位手机号码" maxlength="11" clearable />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" :loading="submitting" @click="handleSubmit">
            {{ isEdit ? '保存修改' : '立即创建' }}
          </el-button>
          <el-button @click="handleCancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive } from 'vue';
import { useCrudForm } from '../composables/useCrudForm';
import { customerApi } from '../api';

const formData = reactive({
  customer_id: '',
  name: '',
  type: '',
  contact_phone: '',
});

const formRules = {
  name: [
    { required: true, message: '请输入客户名称', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' },
  ],
  type: [
    { required: true, message: '请选择客户类型', trigger: 'change' },
  ],
  contact_phone: [
    { pattern: '^\\d{11}$', message: '请输入正确的 11 位手机号码', trigger: 'blur' },
  ],
};

const { formRef, submitting, isEdit, handleSubmit, handleCancel } = useCrudForm({
  api: customerApi,
  idKey: 'customer_id',
  listPath: '/customers',
  formData,
  applyData: (data) => {
    formData.customer_id = data.customer_id;
    formData.name = data.name;
    formData.type = data.type;
    formData.contact_phone = data.contact_phone || '';
  },
  loadErrorMessage: (error) => '加载客户信息失败',
});
</script>

<style scoped>
.customer-form-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.page-title {
  font-size: 18px;
  font-weight: bold;
}

.mt-4 {
  margin-top: 20px;
}
</style>
