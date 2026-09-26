<template>
  <div class="purchase-order-list-container">
    <!-- 导航栏 -->
    <el-page-header @back="$router.push('/')" title="返回首页">
      <template #content>
        <span class="page-title"><el-icon><ShoppingCart /></el-icon> 采购订单管理</span>
      </template>
      <template #extra>
        <el-button v-admin type="primary" @click="handleAdd">
          <el-icon><Plus /></el-icon> 新增采购订单
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
            <div class="stat-icon primary">
              <el-icon :size="32"><ShoppingCart /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-label">采购订单总数</div>
              <div class="stat-value">{{ total }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 搜索区域 -->
    <el-card class="search-card mt-4">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="采购订单 ID">
          <el-input v-model="searchForm.po_id" placeholder="请输入订单 ID" clearable />
        </el-form-item>
        <el-form-item label="供应商 ID">
          <el-input v-model="searchForm.supplier_id" placeholder="请输入供应商 ID" clearable />
        </el-form-item>
        <el-form-item label="审核状态">
          <el-select v-model="searchForm.audit_status" placeholder="请选择状态" clearable style="width: 120px">
            <el-option label="已审核" :value="1" />
            <el-option label="未审核" :value="0" />
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
        <el-table-column prop="po_id" label="采购订单 ID" width="120" />
        <el-table-column prop="supplier_id" label="供应商 ID" min-width="120" />
        <el-table-column prop="employee_id" label="经办人 ID" width="120" />
        <el-table-column prop="po_date" label="采购日期" width="120" />
        <el-table-column prop="audit_status" label="审核状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.audit_status === 1 ? 'success' : 'warning'">{{ row.audit_status === 1 ? '已审核' : '未审核' }}</el-tag>
          </template>
        </el-table-column>
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
import { useCrudList } from '../composables/useCrudList';
import { purchaseOrderApi } from '../api';
import { ShoppingCart, Plus, Refresh, Search, RefreshLeft, View, Edit, Delete } from '@element-plus/icons-vue';

const {
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
} = useCrudList({
  api: purchaseOrderApi,
  basePath: '/purchase-orders',
  idKey: 'po_id',
  entityLabel: '采购订单',
  confirmText: '确定要删除这个采购订单吗？',
  searchDefaults: {
  po_id: '',
  supplier_id: '',
  audit_status: null,
},
});
</script>

<style scoped>
.purchase-order-list-container {
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
