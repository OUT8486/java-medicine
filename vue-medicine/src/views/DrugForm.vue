<template>
  <div class="drug-form-container">
    <el-page-header @back="$router.push('/drugs')" title="返回列表">
      <template #content>
        <span class="page-title">{{ isEdit ? '编辑药品' : '新增药品' }}</span>
      </template>
    </el-page-header>

    <el-card class="mt-4">
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="120px">
        <el-form-item label="药品编号" prop="drug_id">
          <el-input v-model="formData.drug_id" placeholder="请输入药品编号（留空自动生成）" clearable />
        </el-form-item>
        <el-form-item label="药品通用名" prop="generic_name">
          <el-input v-model="formData.generic_name" placeholder="请输入药品通用名" clearable />
        </el-form-item>
        <el-form-item label="批准文号" prop="approval_no">
          <el-input v-model="formData.approval_no" placeholder="请输入药品批准文号" clearable />
        </el-form-item>
        <el-form-item label="剂型" prop="dosage_form">
          <el-select v-model="formData.dosage_form" placeholder="请选择剂型" style="width: 100%">
            <el-option label="片剂" value="片剂" />
            <el-option label="胶囊" value="胶囊" />
            <el-option label="注射液" value="注射液" />
            <el-option label="颗粒剂" value="颗粒剂" />
            <el-option label="口服液" value="口服液" />
          </el-select>
        </el-form-item>
        <el-form-item label="规格" prop="specification">
          <el-input v-model="formData.specification" placeholder="请输入药品规格（如 5mg/片）" clearable />
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-select v-model="formData.unit" placeholder="请选择单位" style="width: 100%">
            <el-option label="片" value="片" />
            <el-option label="盒" value="盒" />
            <el-option label="瓶" value="瓶" />
            <el-option label="支" value="支" />
          </el-select>
        </el-form-item>
        <el-form-item label="采购价" prop="purchase_price">
          <el-input-number v-model="formData.purchase_price" :min="0.01" :precision="2" :step="0.1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="零售价" prop="retail_price">
          <el-input-number v-model="formData.retail_price" :min="0.01" :precision="2" :step="0.1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="生产厂家 ID" prop="manufacturer_id">
          <el-input v-model="formData.manufacturer_id" placeholder="请输入生产厂家 ID" clearable />
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
import { drugApi } from '../api';

const router = useRouter();
const route = useRoute();
const formRef = ref(null);
const submitting = ref(false);

const isEdit = computed(() => !!route.query.id);

const formData = reactive({
  drug_id: '',
  generic_name: '',
  approval_no: '',
  dosage_form: '',
  specification: '',
  unit: '',
  purchase_price: 0.01,
  retail_price: 0.01,
  manufacturer_id: '',
});

const formRules = {
  generic_name: [
    { required: true, message: '请输入药品通用名', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' },
  ],
  approval_no: [
    { required: true, message: '请输入药品批准文号', trigger: 'blur' },
  ],
  dosage_form: [
    { required: true, message: '请选择剂型', trigger: 'change' },
  ],
  specification: [
    { required: true, message: '请输入规格', trigger: 'blur' },
  ],
  unit: [
    { required: true, message: '请选择单位', trigger: 'change' },
  ],
  purchase_price: [
    { required: true, message: '请输入采购价', trigger: 'change' },
    { type: 'number', min: 0.01, message: '价格必须大于 0', trigger: 'change' },
  ],
  retail_price: [
    { required: true, message: '请输入零售价', trigger: 'change' },
    { type: 'number', min: 0.01, message: '价格必须大于 0', trigger: 'change' },
  ],
};

const loadData = async () => {
  if (!isEdit.value) return;
  
  try {
    const res = await drugApi.getById(route.query.id);
    const data = res.data;
    formData.drug_id = data.drug_id;
    formData.generic_name = data.generic_name;
    formData.approval_no = data.approval_no;
    formData.dosage_form = data.dosage_form;
    formData.specification = data.specification;
    formData.unit = data.unit;
    formData.purchase_price = parseFloat(data.purchase_price) || 0.01;
    formData.retail_price = parseFloat(data.retail_price) || 0.01;
    formData.manufacturer_id = data.manufacturer_id || '';
  } catch (error) {
    console.error('加载失败:', error);
    ElMessage.error('加载药品信息失败');
  }
};

const handleSubmit = async () => {
  if (!formRef.value) return;
  
  await formRef.value.validate(async (valid) => {
    if (!valid) return;
    
    submitting.value = true;
    try {
      if (isEdit.value) {
        await drugApi.update(formData.drug_id, formData);
        ElMessage.success('修改成功');
      } else {
        await drugApi.add(formData);
        ElMessage.success('创建成功');
      }
      router.push('/drugs');
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
.drug-form-container {
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
