<template>
    <div>
        <el-menu class="nav" :default-active="activeIndex" mode="horizontal" :ellipsis="false" @select="handleSelect">
            <el-menu-item index="0">
                {{ Title }}
            </el-menu-item>
            <div class="flex-grow" />
            <el-menu-item v-if="isLogin" @click="$router.push('/user')">
                <span>
                    欢迎用户: {{ UserData.UserData.value.UserName }}
                </span>
            </el-menu-item>

            <el-menu-item v-for="(item, index) in linkconf" :index="index.toString()" :key="index" :href="item.url">
                {{ item.name }}
            </el-menu-item>
        </el-menu>
    </div>
</template>

<script setup lang="ts">
import { useUserDataStore } from '@/stores/UserData';
import JwtUtil from '@/utils/JwtUtil';
import { storeToRefs } from 'pinia';
import { computed, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const useUserData = useUserDataStore();
const UserData = storeToRefs(useUserData);
const Title = ref("航天扶贫购物中心")

const linkconf = ref([
    { name: '首页', url: '/' },
    { name: '关于', url: '/about' },
    { name: '个人中心', url: '/user' },
])


const isLogin = computed(() => {
    return UserData.UserData.value.UserID !== -1;
})
const activeIndex = ref("0")
const handleSelect = (key: string, keyPath: string[]) => {
    activeIndex.value = key;
    router.push(linkconf.value[parseInt(key)].url);
}
onMounted(() => {
    for (let i = 0; i < linkconf.value.length; i++) {
        if (linkconf.value[i].url === router.currentRoute.value.path) {
            activeIndex.value = i.toString();
            break;
        }
    }
})
</script>

<style scoped>
.nav {
    height: 2em;
}

.flex-grow {
    flex-grow: 1;
}
</style>