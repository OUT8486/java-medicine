import { useUserStore } from '../stores/user';

/**
 * v-admin：仅管理员可见。非管理员时在挂载阶段从 DOM 中移除元素。
 */
export default {
  mounted(el) {
    const userStore = useUserStore();
    const role = userStore.userInfo && userStore.userInfo.role;
    if (role !== '管理员' && el.parentNode) {
      el.parentNode.removeChild(el);
    }
  },
};
