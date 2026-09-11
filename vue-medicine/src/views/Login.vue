<template>
  <div class="login-container">
    <div class="auth-decoration auth-decoration-one"></div>
    <div class="auth-decoration auth-decoration-two"></div>
    <el-card class="login-card">
      <template #header>
        <div class="login-header">
          <div class="auth-logo"><el-icon><FirstAidKit /></el-icon></div>
          <span class="auth-kicker">康宁药房 · PHARMACY ADMIN</span>
          <h2>欢迎回来</h2>
          <p>登录您的账户，开始管理药房业务</p>
        </div>
      </template>

      <el-form :model="loginForm" :rules="rules" ref="loginFormRef" label-width="80px">
        <el-alert
          v-if="errorMsg"
          type="error"
          :title="errorMsg"
          show-icon
          closable
          style="margin-bottom: 20px"
        />

        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="请输入用户名"
            prefix-icon="User"
            clearable
          />
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            show-password
            @keyup.enter="handleLogin"
          />
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            :loading="loading"
            @click="handleLogin"
            style="width: 100%"
            size="large"
          >
            登录
          </el-button>
        </el-form-item>

        <div class="register-link">
          <span>还没有账户？</span>
          <el-link type="primary" @click="goToRegister">立即注册</el-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { login } from '../api';
import { useUserStore } from '../stores/user';
import { FirstAidKit } from '@element-plus/icons-vue';

const router = useRouter();
const userStore = useUserStore();
const loginFormRef = ref(null);
const loading = ref(false);
const errorMsg = ref('');

const loginForm = reactive({
  username: '',
  password: '',
});

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于 6 位', trigger: 'blur' },
  ],
};

const handleLogin = async () => {
  if (!loginFormRef.value) return;
  
  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true;
      errorMsg.value = '';

      try {
        const res = await login(loginForm);
        
        // 保存用户信息
        userStore.setUserInfo(res.data);
        
        ElMessage.success('登录成功！');
        
        // 跳转到首页
        setTimeout(() => {
          router.push('/');
        }, 500);
      } catch (error) {
        errorMsg.value = error.message || '登录失败';
      } finally {
        loading.value = false;
      }
    }
  });
};

const goToRegister = () => {
  router.push('/register');
};
</script>

<style scoped>
.login-container {
  position: relative;
  overflow: hidden;
  background:
    radial-gradient(circle at 15% 20%, rgba(148, 224, 218, 0.2), transparent 22rem),
    linear-gradient(135deg, #0d4359 0%, #176b87 52%, #207f8c 100%);
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 24px;
}

.login-card {
  position: relative;
  z-index: 1;
  width: 100%;
  max-width: 430px;
  border: 0;
  box-shadow: 0 24px 70px rgba(3, 36, 48, 0.28);
  border-radius: 22px;
}

.login-card :deep(.el-card__header) {
  padding: 34px 40px 22px;
  border-bottom: 0;
}

.login-card :deep(.el-card__body) {
  padding: 0 40px 36px;
}

.login-header {
  text-align: center;
}

.auth-logo {
  display: grid;
  place-items: center;
  width: 54px;
  height: 54px;
  margin: 0 auto 16px;
  border-radius: 17px;
  color: #fff;
  background: linear-gradient(135deg, #2a96a5, #176b87);
  box-shadow: 0 8px 18px rgba(23, 107, 135, 0.24);
  font-size: 28px;
}

.auth-kicker {
  color: #73a0aa;
  font-size: 10px;
  font-weight: 800;
  letter-spacing: 1.5px;
}

.login-header h2 {
  margin: 10px 0 6px;
  color: #18343d;
  font-size: 27px;
  font-weight: 750;
}

.login-header p {
  color: #789099;
  font-size: 13px;
}

.register-link {
  text-align: center;
  margin-top: 18px;
  color: #789099;
  font-size: 13px;
}

.auth-decoration {
  position: absolute;
  border: 1px solid rgba(255, 255, 255, 0.12);
  border-radius: 50%;
}

.auth-decoration-one {
  width: 460px;
  height: 460px;
  top: -180px;
  right: -100px;
}

.auth-decoration-two {
  width: 280px;
  height: 280px;
  bottom: -110px;
  left: -70px;
}

@media (max-width: 520px) {
  .login-container {
    padding: 16px;
  }

  .login-card :deep(.el-card__header) {
    padding: 28px 24px 18px;
  }

  .login-card :deep(.el-card__body) {
    padding: 0 24px 28px;
  }
}
</style>
