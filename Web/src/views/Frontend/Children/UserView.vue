<template>
    <div class="UserCenter-Container">
        <UpdateUserPwd v-model:isShow="isPwdEdit" />


        <div class="UserInfo-Container ">
            <el-card class="UserInfo-main" shadow="always">
                <template #header>
                    <h2>你的信息</h2>
                </template>
                <el-form ref="UserInfoFormRef" class="UserInfo-form" :model="UserInfoForm" label-position="right"
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
                        <el-button @click="isEdit = !isEdit" class="btn" type="primary">{{ isEdit ? '取消修改' : '修改信息'
                            }}</el-button>
                        <el-button @click="onSubmitLogout" class="btn">退出登录</el-button>
                    </div>
                </template>
            </el-card>
        </div>
        <div class="UserCart-Container">
            <div class="UserCart-main">
                <div class="CartItem-Container">
                    <div class="CartItem" v-if="tempUserCart.length <= 0">
                        <el-empty description="您的购物车还没有商品,去首页选购叭" />
                    </div>
                    <div class="CartItem" v-for="(item, index) in tempUserCart" :key="index">
                        <el-card>

                            <div class="CartItem-Header">
                                <p class="CartItem-Title">{{ item.GoodsName }}</p>
                                <p class="CartItem-PerPrice"> ${{ item.GoodsPerPrice }}/份</p>
                            </div>
                            <template #footer>
                                <div class="CartItem-Footer">
                                    <div class="CartItem-CountPrice">
                                        小计: ${{ item.GoodsPerPrice * item.CountNum }}
                                    </div>
                                    <el-input-number v-model="item.CountNum" :min="0" :max="99" controls-position="right"
                                        :precision="0" />
                                </div>
                            </template>
                        </el-card>
                    </div>
                </div>
                <div class="CartOperate-Container" v-if="tempUserCart.length > 0">
                    <el-button @click="FrontDataStore.clearTempCart"> 清空 </el-button>
                    <div class="CartOperat-CountPrice">
                        <span>合计:</span><span>{{ TotalPrice }} </span>
                    </div>
                    <el-button type="primary" @click=""> 结算 </el-button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { computed, reactive, ref, watch } from 'vue';
import { ElMessage, type FormInstance, type FormRules } from 'element-plus';
import { storeToRefs } from 'pinia';
import UpdateUserPwd from "@/components/Frontend/UpdateUserPwd.vue"
import { useFrontDataStore } from '@/stores/FrontData';

const UserInfoFormRef = ref<FormInstance>();

const FrontDataStore = useFrontDataStore();

const { tempUserCart, UserData } = storeToRefs(FrontDataStore);


const TotalPrice = computed(() => {
    let sum = 0;
    for (let i = 0; i < tempUserCart.value.length; i++) {
        sum += tempUserCart.value[i].GoodsPerPrice * tempUserCart.value[i].CountNum;
    }
    return sum.toFixed(2); // 保留两位小数，并转换为字符串。
});
watch(tempUserCart, (newVal, oldVal) => {
    // 监听tempUserCart的变化，当CountNum为0时，移除该商品。
    for (let i = newVal.length - 1; i >= 0; i--) {
        if (newVal[i].CountNum === 0) {
            newVal.splice(i, 1);
        }
    }

}, { deep: true });

const UserInfoForm = reactive({
    UserID: UserData.value.UserID,
    UserName: UserData.value.UserName,
    UserPhone: UserData.value.UserPhone,
    UserAddr: UserData.value.UserAddr,
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
            FrontDataStore.updateUserData(UserInfoForm);
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
    FrontDataStore.logout();
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
    padding: .5em .25em;
    width: 50vw;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: start;
    align-items: end;
}

.UserCart-Container {
    width: 50vw;
    height: 100%;
    /* flex: 1; */
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: start;
    overflow: hidden;
    overflow-y: auto;
}

.UserInfo-main {
    width: 24em;
    display: flex;
    flex-direction: column;
    justify-content: center;
}


.UserCart-main {
    height: 100%;
    width: 24em;
    display: flex;
    flex-direction: column;
    justify-content: end;
    align-items: start;
    overflow: hidden;
}

.UserInfo-form {
    width: 100%;
}


.btn-container {
    margin: 0 1em;
    flex-wrap: wrap;
    display: flex !important;
    justify-content: center;
    /* flex-direction: column; */
}

.btn {
    width: calc(50% - 1em);
    margin: .5em !important;
}

.CartItem-Container {
    flex: 1;
    width: 24em;
    height: calc(100% - 64px);
    overflow: hidden;
    overflow-y: auto;

}

.CartOperate-Container {
    height: 64px;
    display: flex;
    justify-content: center;
    align-items: center;
    position: sticky;
    padding: 1em;
    bottom: 0;
    width: 100%;
    z-index: 100;
}

.CartItem {
    /* background-color: red; */
    padding: .5em .25em;
    width: 24em;
    /* margin: 1em 0; */
}

.CartItem-Header {
    display: flex;
    justify-content: center;
    align-items: center;
}

.CartItem-Title {
    flex: 1;
    font-size: 1.2em;
    font-weight: bolder;
}


.CartItem-Footer {
    display: flex;
    justify-content: center;
    align-items: center;
}

.CartItem-CountPrice {
    flex: 1;
    font-size: 1.1em;
    font-weight: bolder;
    color: rgb(255, 91, 91);
}


.CartOperat-CountPrice {
    flex: 1;
    display: flex;
    justify-content: end;
    align-items: center;
    font-size: 1.2em;
    font-weight: bolder;
    color: rgb(255, 91, 91);
    margin: 0 1em 0 0 !important;
}

.CartItem-NoItem {
    background-color: red;
    height: 100%;
}
</style>