import { ref, computed, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { ElMessage } from 'element-plus';

/**
 * 表单页通用逻辑：编辑态判断、回填、提交（新增/修改）、取消。
 * @param {Object} options
 * @param {Object} options.api 资源 API（含 getById/add/update）
 * @param {string} options.idKey 主键字段名（formData 中的键），如 'drug_id'
 * @param {string} options.listPath 提交成功后跳转的列表路由
 * @param {Object} options.formData 表单响应式对象
 * @param {Function} options.applyData 回填函数，入参为接口返回的 data
 * @param {Function} [options.loadErrorMessage] 加载失败时的提示，入参为 error
 */
export function useCrudForm({
  api,
  idKey,
  listPath,
  formData,
  applyData,
  loadErrorMessage = () => '加载失败',
}) {
  const router = useRouter();
  const route = useRoute();
  const formRef = ref(null);
  const submitting = ref(false);
  const isEdit = computed(() => !!route.query.id);

  const loadData = async () => {
    if (!isEdit.value) return;
    try {
      const res = await api.getById(route.query.id);
      applyData(res.data);
    } catch (error) {
      console.error('加载失败:', error);
      ElMessage.error(loadErrorMessage(error));
    }
  };

  const handleSubmit = async () => {
    if (!formRef.value) return;
    await formRef.value.validate(async (valid) => {
      if (!valid) return;
      submitting.value = true;
      try {
        if (isEdit.value) {
          await api.update(formData[idKey], formData);
          ElMessage.success('修改成功');
        } else {
          await api.add(formData);
          ElMessage.success('创建成功');
        }
        router.push(listPath);
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

  onMounted(loadData);

  return { router, formRef, submitting, isEdit, loadData, handleSubmit, handleCancel };
}
