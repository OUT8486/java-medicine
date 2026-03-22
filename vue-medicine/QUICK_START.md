# 🚀 药店管理系统 Vue 3 前端 - 快速启动指南

## 📌 项目概述

我已经成功将原来的 Thymeleaf 模板页面改造为 **Vue 3 + Element Plus** 的前后端分离架构。

---

## ✅ 已完成的工作

### 1. 后端 RESTful API 改造 (Spring Boot)
- ✅ 统一响应格式：`Result<T>` (包含 code, message, data)
- ✅ CORS 跨域配置 (允许前端访问)
- ✅ 全局异常处理
- ✅ 所有 Controller 改为 `@RestController`
- ✅ API 路径统一为 `/api/*`
- ✅ 新增登录认证接口 `/api/auth/login`

### 2. 前端 Vue 3 项目创建
- ✅ Vue 3 + Vite 项目搭建
- ✅ Element Plus UI 组件库集成
- ✅ Vue Router 路由配置
- ✅ Pinia 状态管理
- ✅ Axios 请求封装 (带拦截器)

### 3. 已实现的页面
#### 认证模块
- ✅ 登录页面 (`/login`) - 带表单验证、token 存储
- ✅ 注册页面 (`/register`) - 密码确认验证

#### 主页面
- ✅ 首页 (`/`) - 导航栏、快捷卡片、模块入口、用户信息

#### 业务列表页
- ✅ 药品管理 (`/drugs`) - 表格展示、搜索、分页、CRUD 操作
- ✅ 客户管理 (`/customers`) - 同上
- ✅ 供应商管理 (`/suppliers`) - 同上
- ⚠️  其他模块基础框架已建好，需根据实际字段调整

---

## 🎯 如何启动

### 方式一：分别启动 (推荐)

#### 1. 启动后端 (在新终端窗口)
```bash
cd "c:\Users\Lenovo\Desktop\药店管理系统"
mvn spring-boot:run
```
后端地址：http://localhost:8080

#### 2. 启动前端 (在另一个新终端窗口)
```bash
cd "c:\Users\Lenovo\Desktop\药店管理系统\vue-medicine"
npm run dev
```
前端地址：http://localhost:5173

### 方式二：只启动前端查看界面效果
```bash
cd "c:\Users\Lenovo\Desktop\药店管理系统\vue-medicine"
npm run dev
```
访问 http://localhost:5173 可以看到登录页面

---

## 🔑 测试账号

由于注册功能还未完全对接后端，你可以:

1. **使用后端数据库中的现有账号**
   - 用户名：admin
   - 密码：123456

2. **或者先测试界面**
   - 登录会调用后端 API，需要确保后端已启动

---

## 📁 项目结构

```
vue-medicine/
├── src/
│   ├── api/              # API 接口定义
│   │   └── index.js      ← 所有 API 封装在这里
│   ├── router/           # 路由配置
│   │   └── index.js      ← 路由和守卫
│   ├── stores/           # Pinia 状态管理
│   │   └── user.js       ← 用户信息存储
│   ├── utils/            # 工具函数
│   │   └── request.js    ← Axios 封装
│   ├── views/            # 页面组件
│   │   ├── Login.vue     ← 登录页
│   │   ├── Register.vue  ← 注册页
│   │   ├── Home.vue      ← 首页
│   │   ├── DrugList.vue  ← 药品列表
│   │   ├── CustomerList.vue  ← 客户列表
│   │   ├── SupplierList.vue  ← 供应商列表
│   │   └── ...其他列表页
│   ├── App.vue
│   └── main.js
├── package.json
└── vite.config.js
```

---

## 🔧 核心功能说明

### 1. 登录流程
```
1. 用户输入用户名密码
2. 调用 /api/auth/login
3. 后端返回 { code: 200, data: { token, user_name, role } }
4. 前端保存 token 到 localStorage
5. 跳转到首页
```

### 2. API 调用示例
```javascript
import { drugApi } from '../api';

// 获取药品列表
const res = await drugApi.getList();
console.log(res.data); // 药品数组

// 删除药品
await drugApi.delete(id);
```

### 3. 路由守卫
```javascript
// 自动检查 token
router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && !localStorage.getItem('token')) {
    next('/login'); // 未登录跳转登录页
  } else {
    next();
  }
});
```

---

## 📋 待完成的工作

### 高优先级
1. **完善其他列表页面**
   - EmployeeList.vue (员工字段)
   - InventoryList.vue (库存字段)
   - WarehouseList.vue (仓库字段)
   - PurchaseOrderList.vue (采购订单)
   - SalesOrderList.vue (销售订单)

2. **创建表单页面**
   - 为每个模块创建 Form.vue
   - 实现新增/编辑对话框或页面

3. **后端 API 联调测试**
   - 测试所有 CRUD 接口
   - 处理错误情况

### 中优先级
4. **优化用户体验**
   - 添加 loading 状态
   - 优化提示信息
   - 改进样式细节

5. **功能增强**
   - 数据导出 Excel
   - 打印功能
   - 统计图表

---

## 🎨 界面特点

- **现代化设计**: Element Plus 组件库
- **渐变色背景**: 登录页紫色渐变
- **卡片式布局**: 首页和功能页
- **丰富图标**: Element Plus Icons
- **响应式**: 适配不同屏幕
- **交互友好**: hover 效果、确认提示

---

## ⚠️ 注意事项

1. **后端必须启动**: 前端需要调用后端 API
2. **CORS 配置**: 后端已配置允许跨域
3. **Token 存储**: 使用 localStorage
4. **API 路径**: 所有接口以 `/api` 开头
5. **响应格式**: `{ code: 200, message: "...", data: [...] }`

---

## 🐛 可能遇到的问题

### 问题 1: 登录失败 "网络错误"
**解决**: 确保后端已启动且地址正确

### 问题 2: 跨域错误
**解决**: 检查后端 CorsConfig.java 是否配置正确

### 问题 3: 页面空白
**解决**: 打开浏览器控制台查看错误信息

---

## 📞 当前状态

✅ **开发服务器已启动**: http://localhost:5173

你现在可以:
1. 访问 http://localhost:5173 查看登录页面
2. 启动后端后测试登录功能
3. 查看各个页面的界面效果

---

## 📖 相关文档

- `README_DEV.md` - 详细开发文档
- `COMPLETION_SUMMARY.md` - 完整总结文档

---

**祝你使用愉快！🎉**
