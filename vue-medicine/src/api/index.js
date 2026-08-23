import request from '../utils/request';

/**
 * 登录 API
 */
export function login(data) {
  return request({
    url: '/auth/login',
    method: 'post',
    data,
  });
}

/**
 * 登出 API
 */
export function logout() {
  return request({
    url: '/auth/logout',
    method: 'post',
  });
}

/**
 * 注册 API（角色由服务端固定为“用户”）
 */
export function register(data) {
  return request({
    url: '/users/register',
    method: 'post',
    data,
  });
}

/**
 * 药品管理 API
 */
export const drugApi = {
  // 获取药品列表
  getList: () => request.get('/drugs'),
  
  // 根据 ID 获取药品
  getById: (id) => request.get(`/drugs/${id}`),
  
  // 新增药品
  add: (data) => request.post('/drugs', data),
  
  // 修改药品
  update: (id, data) => request.put(`/drugs/${id}`, data),
  
  // 删除药品
  delete: (id) => request.delete(`/drugs/${id}`),
};

/**
 * 客户管理 API
 */
export const customerApi = {
  getList: () => request.get('/customers'),
  getById: (id) => request.get(`/customers/${id}`),
  add: (data) => request.post('/customers', data),
  update: (id, data) => request.put(`/customers/${id}`, data),
  delete: (id) => request.delete(`/customers/${id}`),
};

/**
 * 供应商管理 API
 */
export const supplierApi = {
  getList: () => request.get('/suppliers'),
  getById: (id) => request.get(`/suppliers/${id}`),
  add: (data) => request.post('/suppliers', data),
  update: (id, data) => request.put(`/suppliers/${id}`, data),
  delete: (id) => request.delete(`/suppliers/${id}`),
};

/**
 * 员工管理 API
 */
export const employeeApi = {
  getList: () => request.get('/employees'),
  getById: (id) => request.get(`/employees/${id}`),
  add: (data) => request.post('/employees', data),
  update: (id, data) => request.put(`/employees/${id}`, data),
  delete: (id) => request.delete(`/employees/${id}`),
};

/**
 * 库存管理 API
 */
export const inventoryApi = {
  getList: () => request.get('/inventory'),
  getById: (id) => request.get(`/inventory/${id}`),
  update: (id, data) => request.put(`/inventory/${id}`, data),
};

/**
 * 仓库管理 API
 */
export const warehouseApi = {
  getList: () => request.get('/warehouses'),
  getById: (id) => request.get(`/warehouses/${id}`),
  add: (data) => request.post('/warehouses', data),
  update: (id, data) => request.put(`/warehouses/${id}`, data),
  delete: (id) => request.delete(`/warehouses/${id}`),
};

/**
 * 入库管理 API
 */
export const warehouseInApi = {
  getList: () => request.get('/warehouse-in'),
  getById: (id) => request.get(`/warehouse-in/${id}`),
  add: (data) => request.post('/warehouse-in', data),
};

/**
 * 采购订单 API
 */
export const purchaseOrderApi = {
  getList: () => request.get('/purchase-orders'),
  getById: (id) => request.get(`/purchase-orders/${id}`),
  add: (data) => request.post('/purchase-orders', data),
  update: (id, data) => request.put(`/purchase-orders/${id}`, data),
  delete: (id) => request.delete(`/purchase-orders/${id}`),
};

/**
 * 销售订单 API
 */
export const salesOrderApi = {
  getList: () => request.get('/sales-orders'),
  getById: (id) => request.get(`/sales-orders/${id}`),
  add: (data) => request.post('/sales-orders', data),
  update: (id, data) => request.put(`/sales-orders/${id}`, data),
  delete: (id) => request.delete(`/sales-orders/${id}`),
};
