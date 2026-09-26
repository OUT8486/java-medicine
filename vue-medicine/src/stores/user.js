import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '');
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || '{}'));

  const setUserInfo = (data) => {
    token.value = data.token;
    userInfo.value = data;
    localStorage.setItem('token', data.token);
    localStorage.setItem('userInfo', JSON.stringify(data));
  };

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
