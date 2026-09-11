<template>
  <div class="register-container">
    <div class="auth-decoration auth-decoration-one"></div>
    <div class="auth-decoration auth-decoration-two"></div>
    <el-card class="register-card">
      <template #header>
        <div class="register-header">
          <div class="auth-logo"><el-icon><FirstAidKit /></el-icon></div>
          <span class="auth-kicker">康宁药房 · PHARMACY ADMIN</span>
          <h2>创建新账户</h2>
          <p>填写信息，开启药房协同管理</p>
        </div>
      </template>

      <el-form :model="registerForm" :rules="rules" ref="registerFormRef" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="registerForm.username"
            placeholder="请输入用户名"
            prefix-icon="User"
            clearable
          />
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input
            v-model="registerForm.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>

        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
            v-model="registerForm.confirmPassword"
            type="password"
            placeholder="请再次输入密码"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            :loading="loading"
            @click="handleRegister"
            style="width: 100%"
            size="large"
          >
            注册
          </el-button>
        </el-form-item>

        <div class="login-link">
          <span>已有账号？</span>
          <el-link type="primary" @click="goToLogin">立即登录</el-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { register } from '../api';
import { FirstAidKit } from '@element-plus/icons-vue';

const router = useRouter();
const registerFormRef = ref(null);
const loading = ref(false);

const registerForm = reactive({
  username: '',
  password: '',
  confirmPassword: '',
});

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== registerForm.password) {
    callback(new Error('两次输入的密码不一致'));
  } else {
    callback();
  }
};

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于 6 位', trigger: 'blur' },
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' },
  ],
};

const handleRegister = async () => {
  if (!registerFormRef.value) return;

  await registerFormRef.value.validate(async (valid) => {
    if (!valid) return;

    loading.value = true;
    try {
      await register({
        user_name: registerForm.username.trim(),
        password: registerForm.password,
      });
      ElMessage.success('注册成功！请登录');
      router.push('/login');
    } catch (error) {
      ElMessage.error(error.message || '注册失败');
    } finally {
      loading.value = false;
    }
  });
};

const goToLogin = () => {
  router.push('/login');
};
</script>

<style scoped>
.register-container {
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

.register-card {
  position: relative;
  z-index: 1;
  width: 100%;
  max-width: 430px;
  border: 0;
  border-radius: 22px;
  box-shadow: 0 24px 70px rgba(3, 36, 48, 0.28);
}

.register-card :deep(.el-card__header) {
  padding: 34px 40px 22px;
  border-bottom: 0;
}

.register-card :deep(.el-card__body) {
  padding: 0 40px 36px;
}

.register-header {
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

.register-header h2 {
  margin: 10px 0 6px;
  color: #18343d;
  font-size: 27px;
  font-weight: 750;
}

.register-header p {
  color: #789099;
  font-size: 13px;
}

.login-link {
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
  .register-container {
    padding: 16px;
  }

  .register-card :deep(.el-card__header) {
    padding: 28px 24px 18px;
  }

  .register-card :deep(.el-card__body) {
    padding: 0 24px 28px;
  }
}
</style>
