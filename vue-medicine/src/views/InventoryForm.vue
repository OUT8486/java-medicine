<template>
  <div class="inventory-form-container">
    <el-page-header @back="$router.push('/inventory')" title="返回列表">
      <template #content>
        <span class="page-title">{{ isEdit ? '编辑库存' : '新增库存' }}</span>
      </template>
    </el-page-header>

    <el-card class="mt-4">
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="120px">
        <el-form-item label="库存 ID" prop="inventory_id">
          <el-input v-model="formData.inventory_id" placeholder="请输入库存记录编号（留空自动生成）" clearable />
        </el-form-item>
        <el-form-item label="药品 ID" prop="drug_id">
          <el-input v-model="formData.drug_id" placeholder="请输入药品 ID" clearable />
        </el-form-item>
        <el-form-item label="仓库 ID" prop="warehouse_id">
          <el-input v-model="formData.warehouse_id" placeholder="请输入仓库 ID" clearable />
        </el-form-item>
        <el-form-item label="批号" prop="batch_no">
          <el-input v-model="formData.batch_no" placeholder="请输入药品批次号" clearable />
        </el-form-item>
        <el-form-item label="库存数量" prop="quantity">
          <el-input-number v-model="formData.quantity" :min="1" :step="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="有效期" prop="validity_date">
          <el-date-picker
            v-model="formData.validity_date"
            type="date"
            placeholder="选择有效期"
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
import { inventoryApi } from '../api';

const router = useRouter();
const route = useRoute();
const formRef = ref(null);
const submitting = ref(false);

const isEdit = computed(() => !!route.query.id);

const formData = reactive({
  inventory_id: '',
  drug_id: '',
  warehouse_id: '',
  batch_no: '',
  quantity: 1,
  validity_date: '',
});

const formRules = {
  drug_id: [
    { required: true, message: '请输入药品 ID', trigger: 'blur' },
  ],
  warehouse_id: [
    { required: true, message: '请输入仓库 ID', trigger: 'blur' },
  ],
  batch_no: [
    { required: true, message: '请输入批号', trigger: 'blur' },
  ],
  quantity: [
    { required: true, message: '请输入库存数量', trigger: 'change' },
    { type: 'number', min: 1, message: '数量必须大于 0', trigger: 'change' },
  ],
  validity_date: [
    { required: true, message: '请选择有效期', trigger: 'change' },
  ],
};

const loadData = async () => {
  if (!isEdit.value) return;
  
  try {
    const res = await inventoryApi.getById(route.query.id);
    const data = res.data;
    formData.inventory_id = data.inventory_id;
    formData.drug_id = data.drug_id;
    formData.warehouse_id = data.warehouse_id;
    formData.batch_no = data.batch_no;
    formData.quantity = data.quantity;
    formData.validity_date = data.validity_date;
  } catch (error) {
    console.error('加载失败:', error);
    ElMessage.error('加载库存信息失败');
  }
};

const handleSubmit = async () => {
  if (!formRef.value) return;
  
  await formRef.value.validate(async (valid) => {
    if (!valid) return;
    
    submitting.value = true;
    try {
      if (isEdit.value) {
        await inventoryApi.update(formData.inventory_id, formData);
        ElMessage.success('修改成功');
      } else {
        await inventoryApi.add(formData);
        ElMessage.success('创建成功');
      }
      router.push('/inventory');
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
.inventory-form-container {
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
