<template>
    <el-dialog v-model="isShow" title="修改密码" center destroy-on-close width="300">
        <div class="updatePwd-form">
            <el-form ref="PwdFormRef" :model="PwdForm" label-position="left" :rules="PwdFormRules">
                <el-form-item label="原始密码" prop="oldPassword">
                    <el-input style="width: 200px;" v-model="PwdForm.oldPassword" clearable show-password
                        type="password" autocomplete="off" />
                </el-form-item>
                <el-form-item label="修改密码" prop="Password">
                    <el-input style="width: 200px;" v-model="PwdForm.Password" clearable show-password type="password"
                        autocomplete="off" />
                </el-form-item>
                <el-form-item label="确认密码" prop="Password1">
                    <el-input style="width: 200px;" v-model="PwdForm.Password1" clearable show-password type="password"
                        autocomplete="off" />
                </el-form-item>
            </el-form>
        </div>

        <template #footer>
            <el-button @click="onSubmitUpdate" type="primary" style="width: 150px;">
                提交
            </el-button>
        </template>
    </el-dialog>
</template>

<script setup lang="ts">
// 使用 defineProps 定义 props
import { useFrontDataStore } from '@/stores/FrontData';
import type { FormInstance, FormRules } from 'element-plus';
import { defineModel, reactive, ref } from 'vue'


const isShow = defineModel("isShow")

const PwdFormRef = ref<FormInstance>();
const FrontDataStore = useFrontDataStore();


const PwdForm = reactive({
    oldPassword: '',
    Password: '',
    Password1: '',
});


const validatePasswordConfirmation = (rule: any, value: string, callback: (error?: Error) => void) => {
    if (value !== PwdForm.Password) {
        callback(new Error('两次输入的密码不一致'));
    } else {
        callback();
    }
};
const PwdFormRules = reactive<FormRules<typeof PwdForm>>({
    oldPassword: [
        { required: true, message: '请输入原始密码!', trigger: 'blur' },
        { min: 4, max: 16, message: '密码长度为4-16位字符!', trigger: 'blur' }
    ],
    Password: [
        { required: true, message: '请输入密码!', trigger: 'blur' },
        { min: 4, max: 16, message: '密码长度为4-16位字符!', trigger: 'blur' }
    ],
    Password1: [
        { required: true, message: '请再次输入密码!', trigger: 'blur' },
        { validator: validatePasswordConfirmation, trigger: 'blur' }
    ]
});
const onSubmitUpdate = () => {
    const formRef = PwdFormRef.value;
    if (!formRef) return;
    formRef.validate((valid) => {
        if (valid) {
            FrontDataStore.updateUserPwd(PwdForm.oldPassword, PwdForm.Password, PwdForm.Password1);

        } else {
            console.log('表单验证失败');
        }
    });
};












// 更新值并触发事件
const updateValue = () => {
    isShow.value = false // 更新值并关闭弹窗
};

</script>

<style scoped>
.updatePwd-form{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;  /* 水平居中 */
}
</style>