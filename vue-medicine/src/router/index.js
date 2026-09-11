import { createRouter, createWebHistory } from 'vue-router';

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue'),
    meta: { title: '登录' },
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue'),
    meta: { title: '注册' },
  },
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue'),
    meta: { title: '首页', requiresAuth: true },
  },
  {
    path: '/purchase-orders',
    name: 'PurchaseOrders',
    component: () => import('../views/PurchaseOrderList.vue'),
    meta: { title: '采购订单', requiresAuth: true },
  },
  {
    path: '/purchase-orders/form',
    name: 'PurchaseOrderForm',
    component: () => import('../views/PurchaseOrderForm.vue'),
    meta: { title: '采购订单表单', requiresAuth: true },
  },
  {
    path: '/sales-orders',
    name: 'SalesOrders',
    component: () => import('../views/SalesOrderList.vue'),
    meta: { title: '销售订单', requiresAuth: true },
  },
  {
    path: '/sales-orders/form',
    name: 'SalesOrderForm',
    component: () => import('../views/SalesOrderForm.vue'),
    meta: { title: '销售订单表单', requiresAuth: true },
  },
  {
    path: '/drugs',
    name: 'Drugs',
    component: () => import('../views/DrugList.vue'),
    meta: { title: '药品管理', requiresAuth: true },
  },
  {
    path: '/drugs/form',
    name: 'DrugForm',
    component: () => import('../views/DrugForm.vue'),
    meta: { title: '药品表单', requiresAuth: true },
  },
  {
    path: '/customers',
    name: 'Customers',
    component: () => import('../views/CustomerList.vue'),
    meta: { title: '客户管理', requiresAuth: true },
  },
  {
    path: '/customers/form',
    name: 'CustomerForm',
    component: () => import('../views/CustomerForm.vue'),
    meta: { title: '客户表单', requiresAuth: true },
  },
  {
    path: '/suppliers',
    name: 'Suppliers',
    component: () => import('../views/SupplierList.vue'),
    meta: { title: '供应商管理', requiresAuth: true },
  },
  {
    path: '/suppliers/form',
    name: 'SupplierForm',
    component: () => import('../views/SupplierForm.vue'),
    meta: { title: '供应商表单', requiresAuth: true },
  },
  {
    path: '/employees',
    name: 'Employees',
    component: () => import('../views/EmployeeList.vue'),
    meta: { title: '员工管理', requiresAuth: true },
  },
  {
    path: '/employees/form',
    name: 'EmployeeForm',
    component: () => import('../views/EmployeeForm.vue'),
    meta: { title: '员工表单', requiresAuth: true },
  },
  {
    path: '/inventory',
    name: 'Inventory',
    component: () => import('../views/InventoryList.vue'),
    meta: { title: '库存管理', requiresAuth: true },
  },
  {
    path: '/inventory/form',
    name: 'InventoryForm',
    component: () => import('../views/InventoryForm.vue'),
    meta: { title: '库存表单', requiresAuth: true },
  },
  {
    path: '/warehouses',
    name: 'Warehouses',
    component: () => import('../views/WarehouseList.vue'),
    meta: { title: '仓库管理', requiresAuth: true },
  },
  {
    path: '/warehouses/form',
    name: 'WarehouseForm',
    component: () => import('../views/WarehouseForm.vue'),
    meta: { title: '仓库表单', requiresAuth: true },
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// 路由守卫
router.beforeEach((to, from, next) => {
  // 设置页面标题
  document.title = to.meta.title ? `${to.meta.title} - 药店管理系统` : '药店管理系统';
  
  // 检查是否需要登录
  if (to.meta.requiresAuth) {
    const token = localStorage.getItem('token');
    if (!token) {
      next('/login');
      return;
    }
  }
  
  // 如果已登录且访问登录页，重定向到首页
  if (to.path === '/login') {
    const token = localStorage.getItem('token');
    if (token) {
      next('/');
      return;
    }
  }
  
  next();
});

export default router;
