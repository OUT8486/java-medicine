<template>
  <div class="drug-list-container">
    <!-- 导航栏 -->
    <el-page-header @back="$router.push('/')" title="返回首页">
      <template #content>
        <span class="page-title"><el-icon><PieChart /></el-icon> 药品管理</span>
      </template>
      <template #extra>
        <el-button type="primary" @click="handleAdd">
          <el-icon><Plus /></el-icon> 新增药品
        </el-button>
        <el-button @click="loadDrugs">
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
              <el-icon :size="32"><PieChart /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-label">药品总数</div>
              <div class="stat-value">{{ total }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 搜索区域 -->
    <el-card class="search-card mt-4">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="药品通用名">
          <el-input v-model="searchForm.generic_name" placeholder="请输入药品通用名" clearable />
        </el-form-item>
        <el-form-item label="批准文号">
          <el-input v-model="searchForm.approval_no" placeholder="请输入批准文号" clearable />
        </el-form-item>
        <el-form-item label="剂型">
          <el-select v-model="searchForm.dosage_form" placeholder="请选择剂型" clearable style="width: 150px">
            <el-option label="片剂" value="片剂" />
            <el-option label="胶囊" value="胶囊" />
            <el-option label="注射液" value="注射液" />
            <el-option label="颗粒剂" value="颗粒剂" />
            <el-option label="口服液" value="口服液" />
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
        <el-table-column prop="drug_id" label="药品编号" width="120" />
        <el-table-column prop="generic_name" label="药品通用名" min-width="150" />
        <el-table-column prop="approval_no" label="批准文号" min-width="150" />
        <el-table-column prop="dosage_form" label="剂型" width="100" />
        <el-table-column prop="specification" label="规格" width="120" />
        <el-table-column prop="unit" label="单位" width="80" />
        <el-table-column prop="purchase_price" label="采购价" width="100">
          <template #default="{ row }">
            ¥{{ row.purchase_price ? row.purchase_price.toFixed(2) : '-' }}
          </template>
        </el-table-column>
        <el-table-column prop="retail_price" label="零售价" width="100">
          <template #default="{ row }">
            ¥{{ row.retail_price ? row.retail_price.toFixed(2) : '-' }}
          </template>
        </el-table-column>
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
import { drugApi } from '../api';
import {
  PieChart,
  Plus,
  Refresh,
  Search,
  RefreshLeft,
  View,
  Edit,
  Delete,
} from '@element-plus/icons-vue';

const router = useRouter();
const loading = ref(false);
const tableData = ref([]);
const total = ref(0);
const currentPage = ref(1);
const pageSize = ref(20);

const searchForm = reactive({
  generic_name: '',
  approval_no: '',
  dosage_form: '',
});

// 加载药品数据
const loadDrugs = async () => {
  loading.value = true;
  try {
    const res = await drugApi.getPage(currentPage.value, pageSize.value);
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
  loadDrugs();
};

// 重置
const handleReset = () => {
  searchForm.generic_name = '';
  searchForm.approval_no = '';
  searchForm.dosage_form = '';
  currentPage.value = 1;
  loadDrugs();
};

// 新增
const handleAdd = () => {
  router.push('/drugs/form');
};

// 查看
const handleView = (row) => {
  router.push(`/drugs/form?id=${row.drug_id}`);
};

// 编辑
const handleEdit = (row) => {
  router.push(`/drugs/form?id=${row.drug_id}`);
};

// 删除
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除这个药品吗？此操作不可恢复！', '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    });

    await drugApi.delete(row.drug_id);
    ElMessage.success('删除成功');
    loadDrugs();
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error);
    }
  }
};

// 处理每页条数变化
const handleSizeChange = () => {
  currentPage.value = 1; // 重置到第一页
  loadDrugs();
};

// 处理页码变化
const handleCurrentChange = () => {
  loadDrugs();
};

onMounted(() => {
  loadDrugs();
});
</script>

<style scoped>
.drug-list-container {
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
