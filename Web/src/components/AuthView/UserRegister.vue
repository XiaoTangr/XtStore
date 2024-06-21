<template>
    <div class="RegisterForm-Container">


        <el-form ref="registerFormRef" class="login-form" :model="registerForm" label-position="right"
            :rules="registerRules">
            <el-form-item label="登录账号" prop="UserID">
                <el-input style="width: 200px;" v-model="registerForm.UserID" clearable autocomplete="off" />
            </el-form-item>
            <el-form-item label="用户昵称" prop="UserName">
                <el-input style="width: 200px;" v-model="registerForm.UserName" clearable autocomplete="off" />
            </el-form-item>
            <el-form-item label="登录密码" prop="Password">
                <el-input style="width: 200px;" v-model="registerForm.Password" clearable show-password type="password"
                    autocomplete="off" />
            </el-form-item>
            <el-form-item label="确认密码" prop="Password1">
                <el-input style="width: 200px;" v-model="registerForm.Password1" clearable show-password type="password"
                    autocomplete="off" />
            </el-form-item>

            <el-form-item label="手机号码" prop="UserPhone">
                <el-input style="width: 200px;" v-model="registerForm.UserPhone" clearable autocomplete="off" />
            </el-form-item>
            <el-form-item label="收货地址" prop="UserAddr">
                <el-input style="width: 200px;" v-model="registerForm.UserAddr" clearable autocomplete="off" />
            </el-form-item>
            <el-form-item class="btn-container">
                <el-button class="btn" type="primary" @click="submitRegisterForm">注册</el-button>
                <el-button class="btn" @click="toLogin">返回登录</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { useRoute, useRouter } from 'vue-router';
import type { FormInstance, FormRules } from 'element-plus';
import { storeToRefs } from 'pinia';
import { useFrontDataStore } from '@/stores/FrontData';

const registerFormRef = ref<FormInstance>();
const route = useRoute();
const router = useRouter();
const FrontDataStore = useFrontDataStore();
const { UserData } = storeToRefs(FrontDataStore)



const registerForm = reactive({
    UserID: '',
    Password: '',
    Password1: '',
    UserName: '',
    UserPhone: '',
    UserAddr: '',
});



const validatePasswordConfirmation = (rule: any, value: string, callback: (error?: Error) => void) => {
    if (value !== registerForm.Password) {
        callback(new Error('两次输入的密码不一致'));
    } else {
        callback();
    }
};
const registerRules = reactive<FormRules<typeof registerForm>>({
    UserID: [
        { required: true, message: '请输入登录账号!', trigger: 'blur' },
        { pattern: /^\d{6,11}$/, message: '账号为6-11位数字!', trigger: 'blur' }
    ],
    Password: [
        { required: true, message: '请输入密码!', trigger: 'blur' },
        { min: 4, max: 16, message: '密码长度为4-16位字符!', trigger: 'blur' }
    ],
    Password1: [
        { required: true, message: '请再次输入密码!', trigger: 'blur' },
        { validator: validatePasswordConfirmation, trigger: 'blur' }
    ],
    UserName: [
        { required: true, message: '请输入用户昵称!', trigger: 'blur' }
    ],
    UserPhone: [
        { required: true, message: '请输入手机号码!', trigger: 'blur' },
        { pattern: /^1[3456789]\d{9}$/, message: '手机号格式不正确!', trigger: 'blur' }
    ],
    UserAddr: [
        { required: true, message: '请输入收货地址!', trigger: 'blur' },
    ],
});
const submitRegisterForm = () => {
    const formRef = registerFormRef.value;
    if (!formRef) return;

    formRef.validate((valid) => {
        if (valid) {
            FrontDataStore.register(registerForm)
        } else {
            console.log('表单验证失败');
        }
    });
};

const toLogin = () => {
    // 返回登录页面的逻辑
    router.push('/auth/login'); // 示例中使用 Vue Router 进行页面跳转
};
</script>

<style scoped>
.RegisterForm-Container {
    height: 100%;
    margin: 2em;
    display: flex;
    /* 使用flex布局 */
    flex-direction: column;
    /* 设置子元素排列方向为垂直方向 */
    align-items: center;
    /* 水平居中对齐 */
    justify-content: end;
    /* 垂直居中对齐 */
}

.btn-container {
    margin-bottom: 0 !important;
}

.btn {
    flex: 1;
}
</style>
