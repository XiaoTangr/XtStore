<template>
    <div class="UserCenter-Container">
        <UpdateUserPwd v-model:isShow="isPwdEdit" />

        <el-card class="UserInfo-Container" shadow="always">
            <template #header>
                <h2>你的信息</h2>
            </template>
            <el-form ref="UserInfoFormRef" class="login-form" :model="UserInfoForm" label-position="right"
                :rules="UserInfoRules">
                <el-form-item label="登录账号" prop="UserID">
                    <el-input disabled style="width: 250px;" v-model="UserInfoForm.UserID" clearable
                        autocomplete="off" />
                </el-form-item>
                <el-form-item label="用户昵称" prop="UserName">
                    <el-input :disabled="!isEdit" style="width: 250px;" v-model="UserInfoForm.UserName" clearable
                        autocomplete="off" />
                </el-form-item>
                <el-form-item label="手机号码" prop="UserPhone">
                    <el-input :disabled="!isEdit" style="width: 250px;" v-model="UserInfoForm.UserPhone" clearable
                        autocomplete="off" />
                </el-form-item>
                <el-form-item label="收货地址" prop="UserAddr">
                    <el-input :disabled="!isEdit" style="width: 250px;" v-model="UserInfoForm.UserAddr" clearable
                        autocomplete="off" />
                </el-form-item>
                <el-form-item v-if="isEdit">
                    <div class="btn-container">
                        <el-button @click="onSubmitDelete" class="btn" type="danger">注销账号</el-button>
                        <el-button @click="isPwdEdit = true" class="btn" type="warning">修改密码</el-button>
                        <el-button @click="onSubmitUpdate" class="btn" type="primary">提交修改</el-button>
                        <el-button @click="onCancelUpdate" class="btn" type="info">取消修改</el-button>
                    </div>
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="btn-container">
                    <el-button @click="isEdit = !isEdit" class="btn" type="primary">{{ isEdit ? '取消修改' :
                        '修改信息' }}</el-button>

                    <el-button @click="onSubmitLogout" class="btn">退出登录</el-button>
                </div>
            </template>


        </el-card>
        <div class="UserCart-Container">
        </div>
    </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
import type { FormInstance, FormRules } from 'element-plus';
import { useUserDataStore } from '@/stores/UserData';
import { storeToRefs } from 'pinia';
import UpdateUserPwd from "@/components/Frontend/UpdateUserPwd.vue"

const UserInfoFormRef = ref<FormInstance>();

const useUserData = useUserDataStore();

const UserData = storeToRefs(useUserData);
const UserInfoForm = reactive({
    UserID: UserData.UserData.value.UserID,
    UserName: UserData.UserData.value.UserName,
    UserPhone: UserData.UserData.value.UserPhone,
    UserAddr: UserData.UserData.value.UserAddr,
});

const isEdit = ref(false);
const isPwdEdit = ref(false);

const UserInfoRules = reactive<FormRules<typeof UserInfoForm>>({
    UserID: [
        { required: true, message: '请输入登录账号!', trigger: 'blur' },
        { pattern: /^\d{6,11}$/, message: '账号为6-11位数字!', trigger: 'blur' }
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
const onSubmitUpdate = () => {
    const formRef = UserInfoFormRef.value;
    if (!formRef) return;
    formRef.validate((valid) => {
        if (valid) {
            useUserData.updateUserData(UserInfoForm);
        } else {
            console.log('表单验证失败');
        }
    });
};

const onCancelUpdate = () => {
    isEdit.value = false;
    UserInfoFormRef.value?.resetFields();
}
const onSubmitDelete = () => {
    //TODO 提交删除账号请求
}
const onSubmitLogout = () => {
    useUserData.logout();
};
</script>

<style scoped>
.UserCenter-Container {
    display: flex;
    width: 100%;
    height: 100%;
    min-height: 32em;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    overflow: hidden;
}

.UserInfo-Container {
    width: 24em;
    /* height: 32em; */
    margin: 1em;
    margin-right: .5em;
}

.UserCart-Container {
    flex: 1;
    height: 100%;
    overflow: hidden;
    overflow-y: auto;
    margin: 1em;
    margin-left: .5em;
}

.btn-container {
    margin: 0 1em;
    flex-wrap: wrap;
    display: flex !important;
    justify-content: center;
    /* flex-direction: column; */

    .btn {
        width: calc(50% - 1em);
        margin: .5em !important;
    }
}
</style>