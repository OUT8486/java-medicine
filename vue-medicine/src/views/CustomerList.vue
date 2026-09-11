<template>
  <div class="customer-list-container">
    <el-page-header @back="$router.push('/')" title="返回首页">
      <template #content>
        <span class="page-title"><el-icon><User /></el-icon> 客户管理</span>
      </template>
      <template #extra>
        <el-button v-admin type="primary" @click="handleAdd">
          <el-icon><Plus /></el-icon> 新增客户
        </el-button>
        <el-button @click="loadData">
          <el-icon><Refresh /></el-icon> 刷新
        </el-button>
      </template>
    </el-page-header>

    <el-row :gutter="20" class="mt-4">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon info">
              <el-icon :size="32"><User /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-label">客户总数</div>
              <div class="stat-value">{{ total }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 搜索区域 -->
    <el-card class="search-card mt-4">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="客户名称">
          <el-input v-model="searchForm.name" placeholder="请输入客户名称" clearable />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="searchForm.contact_phone" placeholder="请输入联系电话" clearable />
        </el-form-item>
        <el-form-item label="客户类型">
          <el-select v-model="searchForm.type" placeholder="请选择类型" clearable style="width: 120px">
            <el-option label="个人" value="个人" />
            <el-option label="企业" value="企业" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">
            <el-icon><Search /></el-icon> 搜索
          </el-button>
          <el-button @click="handleReset">
            <el-icon><RefreshLeft /></el-icon> 重置
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 表格区域 -->
    <el-card class="mt-4">
      <el-table :data="tableData" v-loading="loading" border stripe style="width: 100%">
        <el-table-column prop="customer_id" label="客户编号" width="120" />
        <el-table-column prop="name" label="客户名称" min-width="180" />
        <el-table-column prop="type" label="客户类型" width="120">
          <template #default="{ row }">
            <el-tag :type="row.type === '企业' ? 'primary' : 'info'">{{ row.type }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="contact_phone" label="联系电话" width="150" />
        <el-table-column v-admin label="操作" width="280" fixed="right">
          <template #default="{ row }">
            <el-button size="small" @click="handleView(row)">
              <el-icon><View /></el-icon> 查看
            </el-button>
            <el-button size="small" type="primary" @click="handleEdit(row)">
              <el-icon><Edit /></el-icon> 编辑
            </el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">
              <el-icon><Delete /></el-icon> 删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-container mt-4">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage, ElMessageBox } from 'element-plus';
import { customerApi } from '../api';
import { User, Plus, Refresh, Search, RefreshLeft, View, Edit, Delete } from '@element-plus/icons-vue';

const router = useRouter();
const loading = ref(false);
const tableData = ref([]);
const total = ref(0);
const currentPage = ref(1);
const pageSize = ref(20);

const searchForm = reactive({
  name: '',
  contact_phone: '',
  type: '',
});

const loadData = async () => {
  loading.value = true;
  try {
    const res = await customerApi.getPage(currentPage.value, pageSize.value);
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
  searchForm.name = '';
  searchForm.contact_phone = '';
  searchForm.type = '';
  currentPage.value = 1;
  loadData();
};

const handleAdd = () => {
  router.push('/customers/form');
};

const handleView = (row) => {
  router.push(`/customers/form?id=${row.customer_id}`);
};

const handleEdit = (row) => {
  router.push(`/customers/form?id=${row.customer_id}`);
};

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除这个客户吗？', '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    });
    await customerApi.delete(row.customer_id);
    ElMessage.success('删除成功');
    loadData();
  } catch (error) {
    if (error !== 'cancel') console.error('删除失败:', error);
  }
};

const handleSizeChange = () => {
  currentPage.value = 1;
  loadData();
};

const handleCurrentChange = () => {
  loadData();
};

onMounted(() => {
  loadData();
});
</script>

<style scoped>
.customer-list-container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 20px;
}

.page-title {
  font-size: 18px;
  font-weight: bold;
  display: flex;
  align-items: center;
  gap: 8px;
}

.stat-card {
  background: linear-gradient(135deg, #409eff 0%, #337ecc 100%);
  color: #fff;
}

.stat-content {
  display: flex;
  align-items: center;
  gap: 16px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.2);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.stat-info {
  flex: 1;
}

.stat-label {
  font-size: 14px;
  opacity: 0.9;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
}

.search-card {
  background-color: #f5f7fa;
}

.pagination-container {
  display: flex;
  justify-content: flex-end;
}

.mt-4 {
  margin-top: 20px;
}
</style>
