import axios from 'axios';
import { ElMessage } from 'element-plus';

// 创建 axios 实例，统一走 /api 前缀（开发由 Vite 代理，部署由 nginx 代理）
const request = axios.create({
  baseURL: '/api',
  timeout: 10000,
});

// 请求拦截器：携带 JWT（Bearer 格式），不打印请求参数，避免密码等敏感信息落日志
request.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// 响应拦截器：统一处理业务错误码与 401 跳转
request.interceptors.response.use(
  (response) => {
    const res = response.data;

    if (res.code !== 200) {
      ElMessage.error(res.message || '请求失败');
      if (res.code === 401) {
        redirectToLogin();
      }
      return Promise.reject(new Error(res.message || '请求失败'));
    }

    return res;
  },
  (error) => {
    const status = error.response && error.response.status;
    const message =
      (error.response && error.response.data && error.response.data.message) ||
      error.message ||
      '网络错误';

    if (status === 401) {
      redirectToLogin();
    }
    ElMessage.error(message);
    return Promise.reject(error);
  }
);

function redirectToLogin() {
  localStorage.removeItem('token');
  localStorage.removeItem('userInfo');
  if (window.location.pathname !== '/login') {
    window.location.href = '/login';
  }
}

export default request;
