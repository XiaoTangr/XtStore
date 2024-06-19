<template>
    <div>
        <router-view></router-view>
    </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue';
import { useUserDataStore } from './stores/UserData';

const useUserData = useUserDataStore();

onMounted(() => {
    useUserData.getUserDataByJwt()
});


</script>

<style scoped></style>