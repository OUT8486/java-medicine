<template>
  <div class="home-container">
    <!-- 导航栏 -->
    <el-menu
      class="topbar"
      mode="horizontal"
      :ellipsis="false"
      text-color="#fff"
      active-text-color="#bfe9ec"
    >
      <div class="logo">
        <span class="logo-mark"><el-icon><FirstAidKit /></el-icon></span>
        <span class="logo-copy">
          <strong>康宁药房</strong>
          <small>PHARMACY ADMIN</small>
        </span>
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
      <div class="page-intro">
        <div>
          <span class="eyebrow">今日工作台</span>
          <h2>早上好，{{ userStore.userInfo.user_name || '管理员' }}</h2>
        </div>
        <p>把日常经营交给清晰、可靠的数字化管理。</p>
      </div>

      <el-card class="hero-card" shadow="hover">
        <el-row :gutter="20" align="middle">
          <el-col :xs="24" :sm="18">
            <div class="hero-kicker"><span></span> 药房运营中心</div>
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
          <el-col :xs="24" :sm="6" class="hero-image">
            <div class="hero-icon-wrapper">
              <el-icon :size="120" color="#fff"><OfficeBuilding /></el-icon>
            </div>
          </el-col>
        </el-row>
      </el-card>

      <!-- 快捷卡片 -->
      <el-row :gutter="20" class="mt-4">
        <el-col :xs="24" :sm="12" :md="8">
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

        <el-col :xs="24" :sm="12" :md="8">
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

        <el-col :xs="24" :sm="12" :md="8">
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
        <el-col :xs="24" :sm="12" :md="8">
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

        <el-col :xs="24" :sm="12" :md="8">
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

        <el-col :xs="24" :sm="12" :md="8">
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
        <el-col :xs="24" :sm="12" :md="8">
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
  FirstAidKit,
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
  } catch {
    return;
  }

  try {
    await logout();
  } catch {
    // 令牌可能已失效，忽略接口异常，仍然完成本地登出
  }

  userStore.clearUserInfo();
  ElMessage.success('登出成功！');
  router.push('/login');
};
</script>

<style scoped>
.home-container {
  min-height: 100vh;
  background: transparent;
}

.topbar {
  min-height: 72px;
  padding: 0 max(24px, calc((100vw - 1240px) / 2));
  background: rgba(15, 76, 99, 0.98) !important;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1) !important;
  box-shadow: 0 8px 24px rgba(15, 76, 99, 0.15);
}

.logo {
  display: flex;
  align-items: center;
  gap: 10px;
  color: #fff;
  padding: 0 18px 0 0;
  white-space: nowrap;
}

.logo-mark {
  width: 38px;
  height: 38px;
  display: grid;
  place-items: center;
  border-radius: 12px;
  color: var(--brand-deep);
  background: #d9f3f2;
  font-size: 21px;
}

.logo-copy {
  display: flex;
  flex-direction: column;
  line-height: 1.05;
  letter-spacing: 0.2px;
}

.logo-copy strong {
  font-size: 17px;
}

.logo-copy small {
  margin-top: 4px;
  color: rgba(255, 255, 255, 0.58);
  font-size: 8px;
  letter-spacing: 1.5px;
}

.menu-items {
  flex: 1;
  display: flex;
  justify-content: flex-start;
  height: 72px;
  overflow-x: auto;
}

.menu-items::-webkit-scrollbar {
  display: none;
}

.topbar :deep(.el-menu-item) {
  height: 72px;
  padding: 0 16px;
  border-bottom: 3px solid transparent;
  color: rgba(255, 255, 255, 0.68) !important;
}

.topbar :deep(.el-menu-item:hover),
.topbar :deep(.el-menu-item.is-active) {
  color: #fff !important;
  background: rgba(255, 255, 255, 0.08) !important;
  border-bottom-color: #82d6d5;
}

.user-info {
  padding: 0 0 0 18px;
  border-left: 1px solid rgba(255, 255, 255, 0.14);
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
  max-width: 1240px;
  margin: 0 auto;
  padding: 32px 24px 48px;
}

.page-intro {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  gap: 20px;
  margin-bottom: 22px;
}

.eyebrow,
.hero-kicker {
  color: var(--brand);
  font-size: 12px;
  font-weight: 800;
  letter-spacing: 1.6px;
  text-transform: uppercase;
}

.page-intro h2 {
  margin-top: 6px;
  color: var(--text-main);
  font-size: 26px;
  font-weight: 750;
}

.page-intro p {
  color: var(--text-secondary);
  font-size: 14px;
}

.hero-card {
  position: relative;
  background:
    radial-gradient(circle at 85% 15%, rgba(131, 221, 216, 0.28), transparent 13rem),
    linear-gradient(120deg, #0f4c63 0%, #176b87 58%, #1e8293 100%);
  color: #fff;
  border: 0;
  box-shadow: 0 18px 42px rgba(15, 76, 99, 0.2);
}

.hero-card :deep(.el-card__body) {
  padding: 36px 38px;
}

.hero-kicker {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 13px;
  color: #9fe3e0;
  letter-spacing: 1px;
}

.hero-kicker span {
  width: 7px;
  height: 7px;
  border-radius: 50%;
  background: #f2c078;
  box-shadow: 0 0 0 5px rgba(242, 192, 120, 0.14);
}

.welcome-title {
  color: #fff;
  font-size: clamp(25px, 3vw, 34px);
  font-weight: 750;
  letter-spacing: -0.7px;
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
  min-width: 150px;
}

.hero-icon-wrapper {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.12);
  border: 1px solid rgba(255, 255, 255, 0.22);
  box-shadow: inset 0 0 30px rgba(255, 255, 255, 0.06);
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto;
}

.module-card {
  cursor: pointer;
  height: 100%;
  transition: transform 0.25s ease, box-shadow 0.25s ease;
}

.module-card:hover {
  transform: translateY(-5px);
  box-shadow: var(--shadow-hover);
}

.module-card :deep(.el-card__body) {
  min-height: 112px;
  display: flex;
  align-items: center;
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
  background: linear-gradient(135deg, #3b9fbc, #176b87);
}

.icon-wrapper.info {
  background: linear-gradient(135deg, #5aa9bd, #327b91);
}

.icon-wrapper.success {
  background: linear-gradient(135deg, #3bb6a4, #208b83);
}

.icon-wrapper.warning {
  background: linear-gradient(135deg, #f1b56e, #d58843);
}

.icon-wrapper.danger {
  background: linear-gradient(135deg, #e78682, #bd5b63);
}

.icon-wrapper.secondary {
  background: linear-gradient(135deg, #8496a6, #5b7182);
}

.card-info {
  flex: 1;
}

.card-title {
  font-size: 16px;
  font-weight: 750;
  color: var(--text-main);
  margin-bottom: 4px;
}

.card-desc {
  font-size: 13px;
  color: var(--text-secondary);
}

@media (max-width: 900px) {
  .topbar {
    padding: 0 16px;
  }

  .logo {
    padding-right: 12px;
  }

  .logo-copy small,
  .username,
  .user-dropdown > .el-icon {
    display: none;
  }

  .topbar :deep(.el-menu-item) {
    padding: 0 11px;
  }
}

@media (max-width: 640px) {
  .topbar {
    min-height: 62px;
  }

  .menu-items {
    height: 62px;
  }

  .topbar :deep(.el-menu-item) {
    height: 62px;
    font-size: 13px;
  }

  .main-content {
    padding: 24px 16px 36px;
  }

  .page-intro {
    display: block;
  }

  .page-intro p {
    margin-top: 8px;
  }

  .hero-card :deep(.el-card__body) {
    padding: 26px 22px;
  }

  .hero-image {
    display: none;
  }
}
</style>
