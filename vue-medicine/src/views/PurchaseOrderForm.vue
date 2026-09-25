<template>
  <div class="purchase-order-form-container">
    <el-page-header @back="$router.push('/purchase-orders')" title="返回列表">
      <template #content>
        <span class="page-title">{{ isEdit ? '编辑采购订单' : '新增采购订单' }}</span>
      </template>
    </el-page-header>

    <el-card class="mt-4">
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="120px">
        <el-form-item label="采购订单 ID" prop="po_id">
          <el-input v-model="formData.po_id" placeholder="请输入采购订单编号（留空自动生成）" clearable />
        </el-form-item>
        <el-form-item label="供应商 ID" prop="supplier_id">
          <el-input v-model="formData.supplier_id" placeholder="请输入供应商 ID" clearable />
        </el-form-item>
        <el-form-item label="经办人 ID" prop="employee_id">
          <el-input v-model="formData.employee_id" placeholder="请输入经办人 ID" clearable />
        </el-form-item>
        <el-form-item label="采购日期" prop="po_date">
          <el-date-picker
            v-model="formData.po_date"
            type="date"
            placeholder="选择采购日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="审核状态" prop="audit_status">
          <el-select v-model="formData.audit_status" placeholder="请选择审核状态" style="width: 100%">
            <el-option label="未审核" :value="0" />
            <el-option label="已审核" :value="1" />
          </el-select>
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
import { ref, reactive, computed, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { ElMessage } from 'element-plus';
import { purchaseOrderApi } from '../api';

const router = useRouter();
const route = useRoute();
const formRef = ref(null);
const submitting = ref(false);

const isEdit = computed(() => !!route.query.id);

const formData = reactive({
  po_id: '',
  supplier_id: '',
  employee_id: '',
  po_date: '',
  audit_status: 0,
});

const formRules = {
  supplier_id: [
    { required: true, message: '请输入供应商 ID', trigger: 'blur' },
  ],
  employee_id: [
    { required: true, message: '请输入经办人 ID', trigger: 'blur' },
  ],
  po_date: [
    { required: true, message: '请选择采购日期', trigger: 'change' },
  ],
};

const loadData = async () => {
  if (!isEdit.value) return;
  
  
  try {
    const res = await purchaseOrderApi.getById(route.query.id);
    const data = res.data;
    formData.po_id = data.po_id;
    formData.supplier_id = data.supplier_id;
    formData.employee_id = data.employee_id;
    formData.po_date = data.po_date;
    formData.audit_status = data.audit_status ?? 0;
  } catch (error) {
    console.error('加载失败:', error);
    ElMessage.error('加载采购订单信息失败：' + (error.message || '未知错误'));
  }
};

const handleSubmit = async () => {
  if (!formRef.value) return;
  
  await formRef.value.validate(async (valid) => {
    if (!valid) return;
    
    submitting.value = true;
    try {
      if (isEdit.value) {
        await purchaseOrderApi.update(formData.po_id, formData);
        ElMessage.success('修改成功');
      } else {
        await purchaseOrderApi.add(formData);
        ElMessage.success('创建成功');
      }
      router.push('/purchase-orders');
    } catch (error) {
      console.error('提交失败:', error);
      ElMessage.error(isEdit.value ? '修改失败' : '创建失败');
    } finally {
      submitting.value = false;
    }
  });
};

const handleCancel = () => {
  router.back();
};

onMounted(() => {
  loadData();
});
</script>

<style scoped>
.purchase-order-form-container {
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
