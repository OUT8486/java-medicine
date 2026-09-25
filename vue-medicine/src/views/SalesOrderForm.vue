<template>
  <div class="sales-order-form-container">
    <el-page-header @back="$router.push('/sales-orders')" title="返回列表">
      <template #content>
        <span class="page-title">{{ isEdit ? '编辑销售订单' : '新增销售订单' }}</span>
      </template>
    </el-page-header>

    <el-card class="mt-4">
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="120px">
        <el-form-item label="销售订单 ID" prop="so_id">
          <el-input v-model="formData.so_id" placeholder="请输入销售订单编号（留空自动生成）" clearable />
        </el-form-item>
        <el-form-item label="客户 ID" prop="customer_id">
          <el-input v-model="formData.customer_id" placeholder="请输入客户 ID" clearable />
        </el-form-item>
        <el-form-item label="经办人 ID" prop="employee_id">
          <el-input v-model="formData.employee_id" placeholder="请输入经办人 ID" clearable />
        </el-form-item>
        <el-form-item label="销售日期" prop="so_date">
          <el-date-picker
            v-model="formData.so_date"
            type="date"
            placeholder="选择销售日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            style="width: 100%"
          />
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
import { salesOrderApi } from '../api';

const router = useRouter();
const route = useRoute();
const formRef = ref(null);
const submitting = ref(false);

const isEdit = computed(() => !!route.query.id);

const formData = reactive({
  so_id: '',
  customer_id: '',
  employee_id: '',
  so_date: '',
});

const formRules = {
  customer_id: [
    { required: true, message: '请输入客户 ID', trigger: 'blur' },
  ],
  employee_id: [
    { required: true, message: '请输入经办人 ID', trigger: 'blur' },
  ],
  so_date: [
    { required: true, message: '请选择销售日期', trigger: 'change' },
  ],
};

const loadData = async () => {
  if (!isEdit.value) return;
  
  
  try {
    const res = await salesOrderApi.getById(route.query.id);
    const data = res.data;
    formData.so_id = data.so_id;
    formData.customer_id = data.customer_id;
    formData.employee_id = data.employee_id;
    formData.so_date = data.so_date;
  } catch (error) {
    console.error('加载失败:', error);
    ElMessage.error('加载销售订单信息失败：' + (error.message || '未知错误'));
  }
};

const handleSubmit = async () => {
  if (!formRef.value) return;
  
  await formRef.value.validate(async (valid) => {
    if (!valid) return;
    
    submitting.value = true;
    try {
      if (isEdit.value) {
        await salesOrderApi.update(formData.so_id, formData);
        ElMessage.success('修改成功');
      } else {
        await salesOrderApi.add(formData);
        ElMessage.success('创建成功');
      }
      router.push('/sales-orders');
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
.sales-order-form-container {
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
