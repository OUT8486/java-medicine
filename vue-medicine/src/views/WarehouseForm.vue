<template>
  <div class="warehouse-form-container">
    <el-page-header @back="$router.push('/warehouses')" title="返回列表">
      <template #content>
        <span class="page-title">{{ isEdit ? '编辑仓库' : '新增仓库' }}</span>
      </template>
    </el-page-header>

    <el-card class="mt-4">
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="120px">
        <el-form-item label="仓库编号" prop="warehouse_id">
          <el-input v-model="formData.warehouse_id" placeholder="请输入仓库编号（留空自动生成）" clearable />
        </el-form-item>
        <el-form-item label="仓库名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入仓库名称" clearable />
        </el-form-item>
        <el-form-item label="仓库位置" prop="location">
          <el-input v-model="formData.location" placeholder="请输入仓库地址/位置" type="textarea" :rows="3" clearable />
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
import { warehouseApi } from '../api';

const formData = reactive({
  warehouse_id: '',
  name: '',
  location: '',
});

const formRules = {
  name: [
    { required: true, message: '请输入仓库名称', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' },
  ],
};

const { formRef, submitting, isEdit, handleSubmit, handleCancel } = useCrudForm({
  api: warehouseApi,
  idKey: 'warehouse_id',
  listPath: '/warehouses',
  formData,
  applyData: (data) => {
    formData.warehouse_id = data.warehouse_id;
    formData.name = data.name;
    formData.location = data.location || '';
  },
  loadErrorMessage: (error) => '加载仓库信息失败',
});
</script>

<style scoped>
.warehouse-form-container {
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
