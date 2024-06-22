<template>
    <div class="Footer-Container">
        <div class="Footer-L">
            <p v-for="(item, index) in Copyright" :key="index"> <span> {{ item }} </span> </p>
        </div>
        <div class="Footer-R">
            <p> <span> {{ ICP }}</span> <span>{{ POLICE }}</span> </p>
            <p> <span> {{ Contact }} </span> </p>
            <p><span> {{ Address }} </span> </p>
        </div>


    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
const Copyright = ref([
    "一颗橘子Tang 版权所有 © 2023 - 2024",
    "Copyright 2023 - 2024 XiaoTangr. All Rights Reserved."
])

const POLICE = ref("川公网安备 510821XXXXXXX 号") // 替换为你的公安网备案号
const ICP = ref("蜀ICP备 2022XXXXXX号 -1") // 替换为你的ICP备案号

const Contact = ref("联系方式:xiaotang0829@gmail.com") // 替换为你的联系方式
const Address = ref("地址:四川省德阳市广汉市新平镇航天大道155号") // 替换为你的地址信息

</script>

<style scoped>
.Footer-Container {
    font-size: 12px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #f5f5f5;
    height: 6em;
}

.Footer-L,
.Footer-R {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 50%;
}

p span::after {
    margin: .5em;
    content: "|";
    color: #7d7d7d;

}

p span:last-child::after {
    content: "";
}
</style>