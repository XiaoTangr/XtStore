<template>
    <div class="Detail-Container">
        <el-carousel type="card" height="240px" motion-blur>
            <el-carousel-item v-for="item in Detail" :key="item">
                <div class="Detail-ImageItem">
                    <el-image :src="item.src" style="height: 240px;" fit="cover" class="Detail-Image" />
                </div>
            </el-carousel-item>
        </el-carousel>
    </div>
    <div class="GoodsCate-Container">
        <el-button size="small" @click="FrontDataStore.setUIGoodsListByCateName('all')" class="GoodsCate-Button"
            :type="activeCate === 'all' ? 'primary' : ''">全部</el-button>
        <el-button size="small" v-for="item in CateList" :type="activeCate === item.CateName ? 'primary' : ''"
            @click="FrontDataStore.setUIGoodsListByCateName(item.CateName)" class="GoodsCate-Button">{{
                item.CateName
            }}</el-button>
    </div>
    <div class=" Goods-container">
        <el-empty class="Goods-Item-noitem" description="当前分类下没有商品!" v-if="!hasUIGoods" />
        <div class="Goods-Item" v-for="item in UIGoodsList" :key="item.GoodsID" :data="item">
            <div class="Goods-Item-Image-Container">
                <img :src="item.GoodsImg" class="Goods-Item-image" />
            </div>
            <div class="Goods-Item-Info-Container">
                <div class="Goods-Item-name">
                    {{ item.GoodsName }}
                </div>
                <div class="Goods-Item-price">
                    <span class="Goods-Item-price-per">
                        ￥{{ item.GoodsPerPrice }}
                    </span>
                    <span class="Goods-Item-price-unit">
                        {{ item.GoodsPerUnit }}
                    </span>
                </div>
            </div>
            <div class="Goods-Item-desc-Contaier">
                <div class="Goods-Item-desc">
                    {{ item.GoodsDesc }}
                </div>
                <div class="Goods-Item-cate">
                    分类: {{ item.GoodsCate }}
                </div>
            </div>
            <div class="Goods-Item-operate-Contaier">
                <div class="Goods-Item-Inven">
                    库存: {{ item.GoodsInven }}
                </div>
                <div class="Goods-Item-operate-Btns">
                    <el-button type="primary" @click="FrontDataStore.activeDetailGoodsByID(item.GoodsID)" circle
                        :icon="View" />
                    <el-button type="primary" @click="FrontDataStore.addToTempCart(item.GoodsID)" circle :icon="Plus" />
                </div>
            </div>
        </div>
        <GoodsDetail />
    </div>

</template>
<script setup lang="ts">
import GoodsDetail from "@/components/Frontend/GoodsDetail.vue"; // 导入组件

import { onBeforeUnmount, ref } from 'vue';
import { Plus, View } from '@element-plus/icons-vue';

import { storeToRefs } from 'pinia';
import { useFrontDataStore } from '@/stores/FrontData';

const FrontDataStore = useFrontDataStore();
const { UIGoodsList, CateList, hasUIGoods, activeCate } = storeToRefs(FrontDataStore);
const Detail = ref([
    { src: "src/assets/imgs/header/11.png" },
    { src: "src/assets/imgs/header/22.png" },
    { src: "src/assets/imgs/header/33.png" },
    { src: "src/assets/imgs/header/44.png" },
])

onBeforeUnmount(() => {
    FrontDataStore.saveCarttoDB()
});
</script>
<style scoped>
.Goods-Item-noitem {
    align-items: center;
    justify-content: center;
    display: flex;
    width: 100%;
    font-size: 1.5em;
    font-weight: bold;
    color: #888
}

.GoodsCate-Container {
    z-index: 99;
    padding: .5em 1em;
    position: sticky;
    top: 0;
    background: rgba(240, 240, 240, 0.589);
    backdrop-filter: blur(1em);
}


.Detail-Container {
    height: 240px;
    width: 100%;
}

.Detail-ImageItem {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
}

.Goods-Item-Info-Container,
.Goods-Item-desc-Contaier,
.Goods-Item-operate-Contaier {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 10px;
    height: 3em;
    /* 设置容器高度 */
}

.Goods-Item-operate-Contaier {
    padding-bottom: 8px;
}

.Goods-Item-desc-Contaier {
    flex-direction: column;
    /* 使描述和分类垂直排列 */
    justify-content: center;
    align-items: start;
}

.Goods-Item-desc {
    width: 100%;
    font-size: 12px;
    line-height: 14px;
    min-height: 42px;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 3;
    overflow: hidden;
    text-overflow: ellipsis;
}


.Goods-Item-Inven,
.Goods-Item-cate,
.Goods-Item-price-unit {
    font-size: .7em;
    color: #888;
    /* 设置分类和库存文字颜色 */
}

.Goods-Item-price-per {
    font-size: 1.2em;
    font-weight: bolder;
    color: rgb(255, 89, 89);
}


.Goods-container {
    height: 100%;
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    justify-content: stretch;
    /* 使子元素在必要时换行 */
    gap: 10px;
    /* 子元素之间的间距 */
}

.Goods-Item {
    width: 15em;
    height: 24em;
    background: rgb(255, 255, 255);
    margin: .5em;
}

img {
    width: 15em;
    height: 15em;
    object-fit: cover;
    /* 图片适应容器 */
    background: #f0f0f0;
    /* 图片加载时的占位背景 */
}
</style>
