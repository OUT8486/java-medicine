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
 * 生成一组标准 CRUD 接口，各资源仅路径不同。
 */
function createCrudApi(base) {
  return {
    getPage: (page, size) => request.get(`${base}/page`, { params: { page, size } }),
    getById: (id) => request.get(`${base}/${id}`),
    add: (data) => request.post(base, data),
    update: (id, data) => request.put(`${base}/${id}`, data),
    delete: (id) => request.delete(`${base}/${id}`),
  };
}

export const drugApi = createCrudApi('/drugs');
export const customerApi = createCrudApi('/customers');
export const supplierApi = createCrudApi('/suppliers');
export const employeeApi = createCrudApi('/employees');
export const inventoryApi = createCrudApi('/inventory');
export const warehouseApi = createCrudApi('/warehouses');
export const purchaseOrderApi = createCrudApi('/purchase-orders');
export const salesOrderApi = createCrudApi('/sales-orders');
