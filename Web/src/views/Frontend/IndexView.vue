<template>

    <FrontHeader />
    <main>
        <RouterView />
    </main>
    <FrontFooter />
    <!-- <p>{{ Data }}</p> -->

</template>
<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue';
import FrontHeader from '@/components/Frontend/FrontHeader.vue';
import FrontFooter from '@/components/Frontend/FrontFooter.vue';
import { useFrontDataStore } from '@/stores/FrontData';

const FrontDataStore = useFrontDataStore();



// 离开时保存购物车
onMounted(() => {
    FrontDataStore.DataInit()
    window.addEventListener('beforeunload', handleBeforeUnload);
});
const handleBeforeUnload = () => {
    FrontDataStore.saveCarttoDB();
};

onBeforeUnmount(() => {
    window.removeEventListener('beforeunload', handleBeforeUnload);
});

</script>
<style scoped>
main {
    height: calc(100vh - 6em);
    overflow-y: auto;
}
</style>
