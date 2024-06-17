<template>

    <p>{{ Data }}</p>

</template>
<script setup lang="ts">
import { onMounted, ref } from 'vue';
import InstanceUtil from '@/utils/InstanceUtil';

const Data = ref([])

onMounted(() => {
    // 获取数据
    InstanceUtil.get('/api/goods')
        .then(response => {
            console.log('Goods:', response.data);
            // 处理数据
            Data.value = response.data.data;
        })
        .catch(error => {
            console.error('Error fetching goods:', error);
        });
});
</script>
<style scoped></style>
