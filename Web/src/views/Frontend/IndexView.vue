<template>

    <FrontHeader />
    <main>
        <RouterView />
    </main>
    <FrontFooter />
    <!-- <p>{{ Data }}</p> -->

</template>
<script setup lang="ts">

import FrontHeader from '@/components/Frontend/FrontHeader.vue';
import FrontFooter from '@/components/Frontend/FrontFooter.vue';

</script>
<style scoped>
main {
    height: calc(100vh - 6em);
    overflow-y: auto;
}
</style>
