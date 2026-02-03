# 首页客户管理API集成说明

## 概述
本次更新在药店管理系统的首页中集成了客户管理功能的API链接，使用户能够从首页直接访问客户管理模块。

## 修改内容

### 1. 导航栏集成
在首页导航栏中添加了客户管理链接：
```html
<li class="nav-item"><a class="nav-link" href="/customer">客户</a></li>
```

### 2. 快捷操作按钮
在首页的快捷操作区域添加了客户管理按钮：
```html
<a class="btn btn-light btn-sm" href="/customer"><i class="bi bi-people me-1"></i> 客户管理</a>
```

### 3. 快捷卡片模块
在首页的快捷卡片区域添加了客户管理模块卡片：
```html
<a class="module-link" href="/customer">
  <div class="card quick-card p-3">
    <div class="d-flex align-items-center">
      <div class="me-3 fs-3 text-info"><i class="bi bi-person-lines-fill"></i></div>
      <div>
        <div class="fw-bold">客户管理</div>
        <div class="text-muted small">管理客户信息与联系方式</div>
      </div>
    </div>
  </div>
</a>
```

## 可用的客户管理API端点

### 页面访问
- `/customer` - 客户管理主页面

### RESTful API接口
1. **获取所有客户**
   - GET `/customer/list`
   - 返回所有客户数据列表

2. **分页查询客户**
   - GET `/customer/page?page=1&size=10&search=&contactPhone=&type=`
   - 支持分页、搜索和筛选

3. **根据ID获取客户**
   - GET `/customer/{id}`
   - 根据客户ID获取详细信息

4. **根据姓名查询客户**
   - GET `/customer/name/{name}`
   - 根据客户姓名模糊查询

5. **根据电话查询客户**
   - GET `/customer/phone/{phone}`
   - 根据联系电话精确查询

6. **新增客户**
   - POST `/customer`
   - 创建新客户记录

7. **更新客户**
   - PUT `/customer/{id}`
   - 更新指定客户的详细信息

8. **删除客户**
   - DELETE `/customer/{id}`
   - 删除指定客户记录

## 视觉设计
- 使用蓝色主题色（text-info）突出客户管理模块
- 采用人像图标（bi-person-lines-fill）表示客户概念
- 保持与其他模块一致的设计风格和交互体验

## 测试验证
系统已在端口8080成功启动，可通过以下方式验证功能：
1. 访问首页 `http://localhost:8080`
2. 点击导航栏"客户"链接
3. 点击快捷操作按钮"客户管理"
4. 点击客户管理卡片模块

## 注意事项
- 确保客户管理相关的Controller和页面文件存在且功能正常
- 前端页面需要正确引入Bootstrap和相关CSS/JS资源
- 数据库中需要有相应的客户数据表和测试数据