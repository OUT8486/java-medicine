# Vue 3 药店管理系统前端

## 项目介绍

这是一个基于 Vue 3 + Element Plus + TypeScript 的药店管理系统前端项目，与 Spring Boot 后端 API 配合使用。

## 技术栈

- **框架**: Vue 3.6
- **UI 组件库**: Element Plus
- **构建工具**: Vite
- **路由**: Vue Router
- **状态管理**: Pinia
- **HTTP 客户端**: Axios
- **图标**: @element-plus/icons-vue

## 项目结构

```
vue-medicine/
├── src/
│   ├── api/              # API 接口定义
│   │   └── index.js
│   ├── router/           # 路由配置
│   │   └── index.js
│   ├── stores/           # Pinia 状态管理
│   │   └── user.js
│   ├── utils/            # 工具函数
│   │   └── request.js
│   ├── views/            # 页面组件
│   │   ├── Login.vue     # 登录页
│   │   ├── Register.vue  # 注册页
│   │   ├── Home.vue      # 首页
│   │   ├── DrugList.vue  # 药品列表
│   │   ├── CustomerList.vue  # 客户列表
│   │   ├── SupplierList.vue  # 供应商列表
│   │   ├── EmployeeList.vue  # 员工列表
│   │   ├── InventoryList.vue # 库存列表
│   │   ├── WarehouseList.vue # 仓库列表
│   │   ├── PurchaseOrderList.vue # 采购订单列表
│   │   └── SalesOrderList.vue  # 销售订单列表
│   ├── App.vue
│   └── main.js
├── public/
├── package.json
└── vite.config.js
```

## 已完成的功能

✅ 1. 项目基础配置
   - Vue 3 + Vite 项目搭建
   - Element Plus UI 组件库集成
   - Vue Router 路由配置
   - Pinia 状态管理
   - Axios 请求封装 (带拦截器)

✅ 2. 核心页面
   - 登录页面 (Login.vue)
   - 注册页面 (Register.vue)
   - 首页 (Home.vue) - 包含导航栏、快捷卡片、系统信息
   - 药品管理列表页 (DrugList.vue)
   - 客户管理列表页 (CustomerList.vue)

✅ 3. API 接口封装
   - 登录/登出 API
   - 药品管理 API
   - 客户管理 API
   - 供应商管理 API
   - 员工管理 API
   - 库存管理 API
   - 仓库管理 API
   - 采购订单 API
   - 销售订单 API

## 待完成的功能

⏳ 1. 表单页面 (创建/编辑)
   - 药品表单页面 (/drugs/form)
   - 客户表单页面 (/customers/form)
   - 供应商表单页面 (/suppliers/form)
   - 员工表单页面 (/employees/form)
   - 等等...

⏳ 2. 其他列表页面完善
   - 供应商列表 (SupplierList.vue) - 需修改字段
   - 员工列表 (EmployeeList.vue) - 需修改字段
   - 库存列表 (InventoryList.vue) - 需修改字段
   - 仓库列表 (WarehouseList.vue) - 需修改字段
   - 采购订单列表 (PurchaseOrderList.vue) - 需修改字段
   - 销售订单列表 (SalesOrderList.vue) - 需修改字段

## 如何运行

### 安装依赖
```bash
npm install
```

### 启动开发服务器
```bash
npm run dev
```

访问 http://localhost:5173

### 构建生产版本
```bash
npm run build
```

### 预览生产构建
```bash
npm run preview
```

## 后端 API 配置

后端 API 基础地址：http://localhost:8080/api

如需修改后端地址，请编辑 `src/utils/request.js` 中的 `baseURL` 配置。

## 页面说明

### 1. 登录页 (/login)
- 用户名和密码验证
- Token 存储到 localStorage
- 自动跳转到首页

### 2. 首页 (/)
- 顶部导航栏
- 用户信息下拉菜单
- 快捷操作按钮
- 功能模块卡片
- 退出登录功能

### 3. 列表页通用功能
- 数据表格展示
- 搜索/筛选功能
- 分页功能
- 新增/编辑/删除操作
- 刷新数据

## 下一步开发建议

1. **完善其他列表页面**
   - 参考 DrugList.vue 和 CustomerList.vue 的结构
   - 根据各模块实际字段调整表格列
   - 修改搜索条件

2. **创建表单页面**
   - 为每个模块创建 Form.vue 文件
   - 实现新增和编辑功能
   - 添加表单验证规则

3. **完善 API 调用**
   - 确保后端 RESTful API 正常工作
   - 处理各种错误情况
   - 添加 loading 状态

4. **优化用户体验**
   - 添加更多反馈提示
   - 优化响应速度
   - 改进界面样式

## 注意事项

1. 确保后端服务已启动 (http://localhost:8080)
2. 检查 CORS 跨域配置是否正确
3. 登录 token 存储在 localStorage 中
4. 所有 API 响应格式应统一为 `{ code, message, data }`
