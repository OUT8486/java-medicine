<template>
  <div class="home-container">
    <!-- 导航栏 -->
    <el-menu
      mode="horizontal"
      :ellipsis="false"
      background-color="#1f2937"
      text-color="#fff"
      active-text-color="#409EFF"
      style="margin-bottom: 20px"
    >
      <div class="logo">
        <el-icon><OfficeBuilding /></el-icon>
        <span>药店管理系统</span>
      </div>

      <div class="menu-items">
        <el-menu-item index="1" @click="$router.push('/')">
          <el-icon><House /></el-icon>
          <span>首页</span>
        </el-menu-item>
        <el-menu-item index="2" @click="$router.push('/drugs')">
          <el-icon><PieChart /></el-icon>
          <span>药品</span>
        </el-menu-item>
        <el-menu-item index="3" @click="$router.push('/customers')">
          <el-icon><User /></el-icon>
          <span>客户</span>
        </el-menu-item>
        <el-menu-item index="4" @click="$router.push('/inventory')">
          <el-icon><Box /></el-icon>
          <span>库存</span>
        </el-menu-item>
        <el-menu-item index="5" @click="$router.push('/purchase-orders')">
          <el-icon><ShoppingCart /></el-icon>
          <span>采购</span>
        </el-menu-item>
        <el-menu-item index="6" @click="$router.push('/sales-orders')">
          <el-icon><Ticket /></el-icon>
          <span>销售</span>
        </el-menu-item>
      </div>

      <div class="user-info">
        <el-dropdown>
          <div class="user-dropdown">
            <el-avatar :size="32" icon="User" />
            <span class="username">{{ userStore.userInfo.user_name || '用户' }}</span>
            <el-icon><ArrowDown /></el-icon>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item divided @click="handleLogout">
                <el-icon><SwitchButton /></el-icon>
                退出登录
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-menu>

    <!-- 主要内容 -->
    <div class="main-content">
      <el-card class="hero-card" shadow="hover">
        <el-row :gutter="20" align="middle">
          <el-col :span="18">
            <h1 class="welcome-title">欢迎使用 药店管理系统</h1>
            <p class="welcome-desc">
              集中管理供应商、药品、库存与订单。快速进入下面的模块开始操作。
            </p>
            <div class="quick-actions">
              <el-button type="primary" @click="$router.push('/drugs')">
                <el-icon><PieChart /></el-icon> 药品管理
              </el-button>
              <el-button type="success" @click="$router.push('/customers')">
                <el-icon><User /></el-icon> 客户管理
              </el-button>
              <el-button type="warning" @click="$router.push('/inventory')">
                <el-icon><Box /></el-icon> 库存管理
              </el-button>
              <el-button type="info" @click="$router.push('/purchase-orders')">
                <el-icon><ShoppingCart /></el-icon> 采购订单
              </el-button>
              <el-button type="danger" @click="$router.push('/sales-orders')">
                <el-icon><Ticket /></el-icon> 销售订单
              </el-button>
            </div>
          </el-col>
          <el-col :span="6" class="hero-image">
            <div class="hero-icon-wrapper">
              <el-icon :size="120" color="#fff"><OfficeBuilding /></el-icon>
            </div>
          </el-col>
        </el-row>
      </el-card>

      <!-- 快捷卡片 -->
      <el-row :gutter="20" class="mt-4">
        <el-col :span="8">
          <el-card shadow="hover" class="module-card" @click="$router.push('/suppliers')">
            <div class="card-content">
              <div class="icon-wrapper primary">
                <el-icon :size="40"><UserFilled /></el-icon>
              </div>
              <div class="card-info">
                <div class="card-title">供应商管理</div>
                <div class="card-desc">管理供应商信息与联系方式</div>
              </div>
            </div>
          </el-card>
        </el-col>

        <el-col :span="8">
          <el-card shadow="hover" class="module-card" @click="$router.push('/customers')">
            <div class="card-content">
              <div class="icon-wrapper info">
                <el-icon :size="40"><Avatar /></el-icon>
              </div>
              <div class="card-info">
                <div class="card-title">客户管理</div>
                <div class="card-desc">管理客户信息与联系方式</div>
              </div>
            </div>
          </el-card>
        </el-col>

        <el-col :span="8">
          <el-card shadow="hover" class="module-card" @click="$router.push('/drugs')">
            <div class="card-content">
              <div class="icon-wrapper success">
                <el-icon :size="40"><PieChart /></el-icon>
              </div>
              <div class="card-info">
                <div class="card-title">药品管理</div>
                <div class="card-desc">新增、编辑与浏览药品</div>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <el-row :gutter="20" class="mt-4">
        <el-col :span="8">
          <el-card shadow="hover" class="module-card" @click="$router.push('/inventory')">
            <div class="card-content">
              <div class="icon-wrapper warning">
                <el-icon :size="40"><Box /></el-icon>
              </div>
              <div class="card-info">
                <div class="card-title">库存管理</div>
                <div class="card-desc">查看库存、出入库记录</div>
              </div>
            </div>
          </el-card>
        </el-col>

        <el-col :span="8">
          <el-card shadow="hover" class="module-card" @click="$router.push('/purchase-orders')">
            <div class="card-content">
              <div class="icon-wrapper info">
                <el-icon :size="40"><ShoppingCart /></el-icon>
              </div>
              <div class="card-info">
                <div class="card-title">采购订单</div>
                <div class="card-desc">创建并管理采购订单</div>
              </div>
            </div>
          </el-card>
        </el-col>

        <el-col :span="8">
          <el-card shadow="hover" class="module-card" @click="$router.push('/sales-orders')">
            <div class="card-content">
              <div class="icon-wrapper danger">
                <el-icon :size="40"><Ticket /></el-icon>
              </div>
              <div class="card-info">
                <div class="card-title">销售订单</div>
                <div class="card-desc">创建并管理销售订单</div>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <el-row :gutter="20" class="mt-4">
        <el-col :span="8">
          <el-card shadow="hover" class="module-card" @click="$router.push('/warehouses')">
            <div class="card-content">
              <div class="icon-wrapper secondary">
                <el-icon :size="40"><OfficeBuilding /></el-icon>
              </div>
              <div class="card-info">
                <div class="card-title">仓库管理</div>
                <div class="card-desc">仓库与入库操作</div>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>

    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { useUserStore } from '../stores/user';
import { logout } from '../api';
import { ElMessage, ElMessageBox } from 'element-plus';
import {
  OfficeBuilding,
  House,
  PieChart,
  User,
  Box,
  ShoppingCart,
  Ticket,
  ArrowDown,
  SwitchButton,
  UserFilled,
  Avatar,
} from '@element-plus/icons-vue';

const router = useRouter();
const userStore = useUserStore();

const handleLogout = async () => {
  try {
    await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    });

    await logout();
    userStore.clearUserInfo();
    ElMessage.success('登出成功！');
    router.push('/login');
  } catch {
    // 用户取消
  }
};
</script>

<style scoped>
.home-container {
  min-height: 100vh;
  background-color: #f5f7fa;
}

.logo {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 20px;
  font-weight: bold;
  color: #fff;
  padding: 0 20px;
}

.menu-items {
  flex: 1;
  display: flex;
  justify-content: center;
}

.user-info {
  padding: 0 20px;
}

.user-dropdown {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  color: #fff;
}

.username {
  margin-right: 4px;
}

.main-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.hero-card {
  background: linear-gradient(135deg, #4f46e5 0%, #06b6d4 100%);
  color: #fff;
}

.welcome-title {
  font-size: 28px;
  margin-bottom: 16px;
}

.welcome-desc {
  font-size: 16px;
  opacity: 0.9;
  margin-bottom: 24px;
}

.quick-actions {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
}

.hero-image {
  text-align: center;
}

.hero-icon-wrapper {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.2);
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto;
}

.module-card {
  cursor: pointer;
  transition: all 0.3s ease;
}

.module-card:hover {
  transform: translateY(-5px);
}

.card-content {
  display: flex;
  align-items: center;
  gap: 16px;
}

.icon-wrapper {
  width: 60px;
  height: 60px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.icon-wrapper.primary {
  background: linear-gradient(135deg, #409eff, #337ecc);
}

.icon-wrapper.info {
  background: linear-gradient(135deg, #67c23a, #529b2e);
}

.icon-wrapper.success {
  background: linear-gradient(135deg, #67c23a, #529b2e);
}

.icon-wrapper.warning {
  background: linear-gradient(135deg, #e6a23c, #b88230);
}

.icon-wrapper.danger {
  background: linear-gradient(135deg, #f56c6c, #c45656);
}

.icon-wrapper.secondary {
  background: linear-gradient(135deg, #909399, #73767a);
}

.card-info {
  flex: 1;
}

.card-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 4px;
}

.card-desc {
  font-size: 13px;
  color: #909399;
}

.mt-4 {
  margin-top: 20px;
}
</style>
