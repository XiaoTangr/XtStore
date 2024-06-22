<template>
    <div>
        <router-view></router-view>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue';
import FrontHeader from '@/components/Frontend/FrontHeader.vue';
import FrontFooter from '@/components/Frontend/FrontFooter.vue';
import { useFrontDataStore } from '@/stores/FrontData';

const FrontDataStore = useFrontDataStore();



</script>


<style scoped></style>