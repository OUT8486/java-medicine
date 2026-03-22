import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '');
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || '{}'));

  // 设置用户信息
  const setUserInfo = (data) => {
    token.value = data.token;
    userInfo.value = data;
    localStorage.setItem('token', data.token);
    localStorage.setItem('userInfo', JSON.stringify(data));
  };

  // 清除用户信息 (登出)
  const clearUserInfo = () => {
    token.value = '';
    userInfo.value = {};
    localStorage.removeItem('token');
    localStorage.removeItem('userInfo');
  };

  return {
    token,
    userInfo,
    setUserInfo,
    clearUserInfo,
  };
});
