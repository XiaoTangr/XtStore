<template>
    <div class="auth-Container">
        <div class="image-Container">
            <img class="img" src="@/assets/imgs/618.png" alt="Auth Image" />
        </div>
        <div class="form-Container">
            <UserRegister v-if="operation === 'register'" />
            <UserLogin v-else />
        </div>
        <el-button class="close-btn" :icon="Close" circle type="danger" size="small"
            @click="$router.push('/')"></el-button>
    </div>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router';

import UserLogin from '@/components/AuthView/UserLogin.vue';
import UserRegister from "@/components/AuthView/UserRegister.vue"
import { onMounted, ref, watch } from 'vue';
import { Close } from '@element-plus/icons-vue'
import JwtUtil from '@/utils/JwtUtil';
import ApiUtil from '@/utils/ApiUtil';

const route = useRoute();
const router = useRouter();
const operation = ref();

onMounted(() => {
    operation.value = route.params.operation;

    if (JwtUtil.isJwtExist()) {
        ApiUtil.get('/api/auth').then(response => {
            if (response.data.code === 200) {
                router.go(-1)
            } else {
                router.push('/auth/login');
            }
        })
    } else {
        router.push('/auth/login');
    }

});
watch(() => route.params.operation, (newValue) => {
    operation.value = newValue;
});

</script>

<style scoped>
.auth-Container {
    height: 26em;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #ffffff;
    overflow: hidden;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-Container {
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.image-Container {
    height: 100%;
}

.img {
    height: 100%;
}

.close-btn {
    position: absolute;
    top: 10px;
    right: 10px
}
</style>