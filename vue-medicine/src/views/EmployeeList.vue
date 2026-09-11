<template>
  <div class="employee-list-container">
    <!-- 导航栏 -->
    <el-page-header @back="$router.push('/')" title="返回首页">
      <template #content>
        <span class="page-title"><el-icon><User /></el-icon> 员工管理</span>
      </template>
      <template #extra>
        <el-button type="primary" @click="handleAdd">
          <el-icon><Plus /></el-icon> 新增员工
        </el-button>
        <el-button @click="loadData">
          <el-icon><Refresh /></el-icon> 刷新
        </el-button>
      </template>
    </el-page-header>

    <!-- 统计卡片 -->
    <el-row :gutter="20" class="mt-4">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon success">
              <el-icon :size="32"><User /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-label">员工总数</div>
              <div class="stat-value">{{ total }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 搜索区域 -->
    <el-card class="search-card mt-4">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="员工姓名">
          <el-input v-model="searchForm.name" placeholder="请输入员工姓名" clearable />
        </el-form-item>
        <el-form-item label="岗位">
          <el-input v-model="searchForm.post" placeholder="请输入岗位" clearable />
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
        <el-table-column prop="employee_id" label="员工编号" width="120" />
        <el-table-column prop="name" label="姓名" min-width="120" />
        <el-table-column prop="post" label="岗位" min-width="150" />
        <el-table-column label="操作" width="280" fixed="right">
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

      <!-- 分页 -->
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
import { employeeApi } from '../api';
import { User, Plus, Refresh, Search, RefreshLeft, View, Edit, Delete } from '@element-plus/icons-vue';

const router = useRouter();
const loading = ref(false);
const tableData = ref([]);
const total = ref(0);
const currentPage = ref(1);
const pageSize = ref(20);

const searchForm = reactive({
  name: '',
  post: '',
});

// 加载员工数据
const loadData = async () => {
  loading.value = true;
  try {
    const res = await employeeApi.getPage(currentPage.value, pageSize.value);
    tableData.value = res.data.list || [];
    total.value = res.data.total || 0;
  } catch (error) {
    console.error('加载失败:', error);
  } finally {
    loading.value = false;
  }
};

// 搜索
const handleSearch = () => {
  currentPage.value = 1;
  loadData();
};

// 重置
const handleReset = () => {
  searchForm.name = '';
  searchForm.post = '';
  currentPage.value = 1;
  loadData();
};

// 新增
const handleAdd = () => {
  router.push('/employees/form');
};

// 查看
const handleView = (row) => {
  router.push(`/employees/form?id=${row.employee_id}`);
};

// 编辑
const handleEdit = (row) => {
  router.push(`/employees/form?id=${row.employee_id}`);
};

// 删除
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除这个员工吗？', '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    });

    await employeeApi.delete(row.employee_id);
    ElMessage.success('删除成功');
    loadData();
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error);
    }
  }
};

// 分页大小变化
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
.employee-list-container {
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
  background: linear-gradient(135deg, #67c23a 0%, #529b2e 100%);
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
