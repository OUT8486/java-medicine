import axios from 'axios';
import { ElMessage } from 'element-plus';

// 创建 axios 实例
const request = axios.create({
  baseURL: 'http://localhost:8080/api', // 后端 API 基础地址
  timeout: 10000, // 请求超时时间
});

// 请求拦截器
request.interceptors.request.use(
  (config) => {
    // 从 localStorage 获取 token
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = token;
    }
    
    // 打印请求信息用于调试
    console.log('[HTTP Request]', config.method.toUpperCase(), config.url);
    console.log('[Request Params]', config.params || config.data);
    
    return config;
  },
  (error) => {
    console.error('[Request Error]', error);
    return Promise.reject(error);
  }
);

// 响应拦截器
request.interceptors.response.use(
  (response) => {
    const res = response.data;
    
    // 如果响应状态码不是 200，说明有错误
    if (res.code !== 200) {
      ElMessage.error(res.message || '请求失败');
      
      // 如果是 401 错误，说明未登录或 token 过期，跳转到登录页
      if (res.code === 401) {
        localStorage.removeItem('token');
        window.location.href = '/login';
      }
      
      return Promise.reject(new Error(res.message || '请求失败'));
    }
    
    return res;
  },
  (error) => {
    console.error('请求错误:', error);
    ElMessage.error(error.message || '网络错误');
    return Promise.reject(error);
  }
);

export default request;
