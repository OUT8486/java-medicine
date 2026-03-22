<template>
  <div class="employee-form-container">
    <el-page-header @back="$router.push('/employees')" title="返回列表">
      <template #content>
        <span class="page-title">{{ isEdit ? '编辑员工' : '新增员工' }}</span>
      </template>
    </el-page-header>

    <el-card class="mt-4">
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="120px">
        <el-form-item label="员工编号" prop="employee_id">
          <el-input v-model="formData.employee_id" placeholder="请输入员工编号（留空自动生成）" clearable />
        </el-form-item>
        <el-form-item label="员工姓名" prop="name">
          <el-input v-model="formData.name" placeholder="请输入员工姓名" clearable />
        </el-form-item>
        <el-form-item label="岗位" prop="post">
          <el-input v-model="formData.post" placeholder="请输入岗位（如采购/销售/库管等）" clearable />
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
import { employeeApi } from '../api';

const router = useRouter();
const route = useRoute();
const formRef = ref(null);
const submitting = ref(false);

const isEdit = computed(() => !!route.query.id);

const formData = reactive({
  employee_id: '',
  name: '',
  post: '',
});

const formRules = {
  name: [
    { required: true, message: '请输入员工姓名', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' },
  ],
  post: [
    { required: true, message: '请输入岗位', trigger: 'blur' },
  ],
};

const loadData = async () => {
  if (!isEdit.value) return;
  
  try {
    const res = await employeeApi.getById(route.query.id);
    const data = res.data;
    formData.employee_id = data.employee_id;
    formData.name = data.name;
    formData.post = data.post;
  } catch (error) {
    console.error('加载失败:', error);
    ElMessage.error('加载员工信息失败');
  }
};

const handleSubmit = async () => {
  if (!formRef.value) return;
  
  await formRef.value.validate(async (valid) => {
    if (!valid) return;
    
    submitting.value = true;
    try {
      if (isEdit.value) {
        await employeeApi.update(formData.employee_id, formData);
        ElMessage.success('修改成功');
      } else {
        await employeeApi.add(formData);
        ElMessage.success('创建成功');
      }
      router.push('/employees');
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
.employee-form-container {
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
