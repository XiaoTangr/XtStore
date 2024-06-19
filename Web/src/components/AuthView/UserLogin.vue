<template>
    <div class="LoginForm-Container">
        <el-form class="login-form" :model="loginForm" ref="loginFormRef" label-position="left" :rules="loginRules">
            <el-form-item label="登录账号" prop="UserID">
                <el-input style="width: 200px;" clearable v-model="loginForm.UserID" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="登录密码" prop="Password">
                <el-input style="width: 200px;" clearable show-password type="password" v-model="loginForm.Password"
                    autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item class="btn-container">
                <el-button class="btn" type="primary" @click="submitLoginForm">登录</el-button>
                <el-button class="btn" @click="toRegister">注册</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { useRoute, useRouter } from 'vue-router';
import ApiUtil from '@/utils/ApiUtil';
import { useUserDataStore } from '@/stores/UserData';

const route = useRoute(); // 获取当前路由信息
const router = useRouter(); // 路由实例，用于导航到其他路由页面。

const UserData = useUserDataStore(); // 用户数据状态管理实例。

const loginForm = reactive({
    UserID: '',
    Password: ''
});

const loginRules = {
    UserID: [
        { required: true, message: '请输入登录账号!', trigger: 'blur' },
        { pattern: /^\d{6,11}$/, message: '账号为6-11位数字!', trigger: 'blur' }
    ],
    Password: [
        { required: true, message: '请输入密码!', trigger: 'blur' },
        { min: 4, max: 16, message: '密码格式不合法!', trigger: 'blur' }
    ]
};

const loginFormRef = ref();

const submitLoginForm = () => {
    loginFormRef.value?.validate((valid: boolean) => {
        if (valid) {
            UserData.login(loginForm.UserID, loginForm.Password)
        } else {
            return false;
        }
    });
};

const toRegister = () => {
    router.push('/auth/register');
};
</script>

<style scoped>
.LoginForm-Container {
    padding: 2em;
    display: flex;
    /* 使用flex布局 */
    flex-direction: column;
    /* 设置子元素排列方向为垂直方向 */
    align-items: center;
    /* 水平居中对齐 */
    justify-content: center;
    /* 垂直居中对齐 */
}

.btn-container {
    margin-bottom: 0 !important;
}

.btn {
    flex: 1;
}
</style>
