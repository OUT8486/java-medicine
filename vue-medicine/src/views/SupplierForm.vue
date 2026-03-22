<template>
  <div class="supplier-form-container">
    <el-page-header @back="$router.push('/suppliers')" title="返回列表">
      <template #content>
        <span class="page-title">{{ isEdit ? '编辑供应商' : '新增供应商' }}</span>
      </template>
    </el-page-header>

    <el-card class="mt-4">
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="120px">
        <el-form-item label="供应商编号" prop="supplier_id">
          <el-input v-model="formData.supplier_id" placeholder="请输入供应商编号（留空自动生成）" clearable />
        </el-form-item>
        <el-form-item label="供应商名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入供应商名称" clearable />
        </el-form-item>
        <el-form-item label="联系电话" prop="contact_phone">
          <el-input v-model="formData.contact_phone" placeholder="请输入 11 位手机号码" maxlength="11" clearable />
        </el-form-item>
        <el-form-item label="合作状态" prop="status">
          <el-select v-model="formData.status" placeholder="请选择合作状态" style="width: 100%">
            <el-option label="启用" :value="1" />
            <el-option label="停用" :value="0" />
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
import { supplierApi } from '../api';

const router = useRouter();
const route = useRoute();
const formRef = ref(null);
const submitting = ref(false);

const isEdit = computed(() => !!route.query.id);

const formData = reactive({
  supplier_id: '',
  name: '',
  contact_phone: '',
  status: 1,
});

const formRules = {
  name: [
    { required: true, message: '请输入供应商名称', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' },
  ],
  contact_phone: [
    { pattern: '^\\d{11}$', message: '请输入正确的 11 位手机号码', trigger: 'blur' },
  ],
};

const loadData = async () => {
  if (!isEdit.value) return;
  
  try {
    const res = await supplierApi.getById(route.query.id);
    const data = res.data;
    formData.supplier_id = data.supplier_id;
    formData.name = data.name;
    formData.contact_phone = data.contact_phone || '';
    formData.status = data.status ?? 1;
  } catch (error) {
    console.error('加载失败:', error);
    ElMessage.error('加载供应商信息失败');
  }
};

const handleSubmit = async () => {
  if (!formRef.value) return;
  
  await formRef.value.validate(async (valid) => {
    if (!valid) return;
    
    submitting.value = true;
    try {
      if (isEdit.value) {
        await supplierApi.update(formData.supplier_id, formData);
        ElMessage.success('修改成功');
      } else {
        await supplierApi.add(formData);
        ElMessage.success('创建成功');
      }
      router.push('/suppliers');
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
.supplier-form-container {
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
