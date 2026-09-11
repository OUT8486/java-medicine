<template>
  <div class="inventory-list-container">
    <!-- 导航栏 -->
    <el-page-header @back="$router.push('/')" title="返回首页">
      <template #content>
        <span class="page-title"><el-icon><Box /></el-icon> 库存管理</span>
      </template>
      <template #extra>
        <el-button type="primary" @click="handleAdd">
          <el-icon><Plus /></el-icon> 新增库存记录
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
            <div class="stat-icon warning">
              <el-icon :size="32"><Box /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-label">库存记录总数</div>
              <div class="stat-value">{{ total }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 搜索区域 -->
    <el-card class="search-card mt-4">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="药品 ID">
          <el-input v-model="searchForm.drug_id" placeholder="请输入药品 ID" clearable />
        </el-form-item>
        <el-form-item label="批号">
          <el-input v-model="searchForm.batch_no" placeholder="请输入批号" clearable />
        </el-form-item>
        <el-form-item label="仓库 ID">
          <el-input v-model="searchForm.warehouse_id" placeholder="请输入仓库 ID" clearable />
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
        <el-table-column prop="inventory_id" label="库存 ID" width="100" />
        <el-table-column prop="drug_id" label="药品 ID" min-width="120" />
        <el-table-column prop="batch_no" label="批号" min-width="150" />
        <el-table-column prop="quantity" label="库存数量" width="100" />
        <el-table-column prop="warehouse_id" label="仓库 ID" width="120" />
        <el-table-column prop="validity_date" label="有效期" width="120" />
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
import { inventoryApi } from '../api';
import { Box, Plus, Refresh, Search, RefreshLeft, View, Edit, Delete } from '@element-plus/icons-vue';

const router = useRouter();
const loading = ref(false);
const tableData = ref([]);
const total = ref(0);
const currentPage = ref(1);
const pageSize = ref(20);

const searchForm = reactive({
  drug_id: '',
  batch_no: '',
  warehouse_id: '',
});

// 加载库存数据
const loadData = async () => {
  loading.value = true;
  try {
    const res = await inventoryApi.getPage(currentPage.value, pageSize.value);
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
  searchForm.drug_id = '';
  searchForm.batch_no = '';
  searchForm.warehouse_id = '';
  currentPage.value = 1;
  loadData();
};

// 新增
const handleAdd = () => {
  router.push('/inventory/form');
};

// 查看
const handleView = (row) => {
  router.push(`/inventory/form?id=${row.inventory_id}`);
};

// 编辑
const handleEdit = (row) => {
  router.push(`/inventory/form?id=${row.inventory_id}`);
};

// 删除
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除这条库存记录吗？', '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    });

    // TODO: 实现删除 API
    // await inventoryApi.delete(row.inventory_id);
    ElMessage.success('删除功能待实现');
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
.inventory-list-container {
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
  background: linear-gradient(135deg, #e6a23c 0%, #b88230 100%);
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
