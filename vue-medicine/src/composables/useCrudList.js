import { ref, reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage, ElMessageBox } from 'element-plus';

/**
 * 列表页通用逻辑：分页加载、搜索重置、新增/查看/编辑/删除、分页切换。
 * @param {Object} options
 * @param {Object} options.api 资源 API（含 getPage/delete）
 * @param {string} options.basePath 表单路由前缀，如 '/drugs'
 * @param {string} options.idKey 行主键字段名，如 'drug_id'
 * @param {string} [options.entityLabel] 实体名称，用于默认删除提示
 * @param {Object} [options.searchDefaults] 搜索表单初始值
 * @param {string} [options.confirmText] 删除确认文案
 * @param {number} [options.pageSize] 每页条数，默认 20
 */
export function useCrudList({
  api,
  basePath,
  idKey,
  entityLabel = '记录',
  searchDefaults = {},
  confirmText,
  pageSize: initialPageSize = 20,
} = {}) {
  const router = useRouter();
  const loading = ref(false);
  const tableData = ref([]);
  const total = ref(0);
  const currentPage = ref(1);
  const pageSize = ref(initialPageSize);
  const searchForm = reactive({ ...searchDefaults });

  const loadData = async () => {
    loading.value = true;
    try {
      const res = await api.getPage(currentPage.value, pageSize.value);
      tableData.value = res.data.list || [];
      total.value = res.data.total || 0;
    } catch (error) {
      console.error('加载失败:', error);
    } finally {
      loading.value = false;
    }
  };

  const handleSearch = () => {
    currentPage.value = 1;
    loadData();
  };

  const handleReset = () => {
    Object.assign(searchForm, searchDefaults);
    currentPage.value = 1;
    loadData();
  };

  const handleAdd = () => {
    router.push(`${basePath}/form`);
  };

  const handleView = (row) => {
    router.push(`${basePath}/form?id=${row[idKey]}`);
  };

  const handleEdit = handleView;

  const handleDelete = async (row) => {
    try {
      await ElMessageBox.confirm(confirmText || `确定要删除这个${entityLabel}吗？`, '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      });
      await api.delete(row[idKey]);
      ElMessage.success('删除成功');
      loadData();
    } catch (error) {
      if (error !== 'cancel') {
        console.error('删除失败:', error);
      }
    }
  };

  const handleSizeChange = () => {
    currentPage.value = 1;
    loadData();
  };

  const handleCurrentChange = () => {
    loadData();
  };

  onMounted(loadData);

  return {
    router,
    loading,
    tableData,
    total,
    currentPage,
    pageSize,
    searchForm,
    loadData,
    handleSearch,
    handleReset,
    handleAdd,
    handleView,
    handleEdit,
    handleDelete,
    handleSizeChange,
    handleCurrentChange,
  };
}
