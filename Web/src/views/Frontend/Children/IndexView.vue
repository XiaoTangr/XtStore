<template>
    <div class="Detail-Container">
        {{ Detail }}
    </div>
    <div class="GoodsCate-Container">
        <span>
            分类
        </span>
        <el-button size="small" @click="orderByCate('all')" class="GoodsCate-Button">全部</el-button>
        <el-button size="small" v-for="item in CateList" @click="orderByCate(item.CateName)" class="GoodsCate-Button">{{
            item.CateName }}</el-button>
    </div>
    <div class=" Goods-container">
        <div class="Goods-Item-noitem" v-if="!useGoodsData.hasGoods">
            当前分类下没有商品!
        </div>
        <div class="Goods-Item" v-for="item in GoodsData.GoodsList.value" :key="item.GoodsID" :data="item">

            <!-- 商品图片 -->

            <div class="Goods-Item-Image-Container">
                <img :src="item.GoodsImg" class="Goods-Item-image" />
            </div>
            <div class="Goods-Item-Info-Container">
                <div class="Goods-Item-name">
                    {{ item.GoodsName }}
                </div>
                <div class="Goods-Item-price">
                    <span class="Goods-Item-price-per">
                        ${{ item.GoodsPerPrice }}
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
                <el-button type="primary" circle :icon="Plus" />
            </div>
        </div>
    </div>

</template>
<script setup lang="ts">
import { computed, onBeforeMount, onMounted, ref } from 'vue';
import ApiUtil from '@/utils/ApiUtil';

import { Plus } from '@element-plus/icons-vue';
import { useRoute, useRouter } from 'vue-router';
import { useGoodsDataStore } from '@/stores/GoodsData';
import { storeToRefs } from 'pinia';


const Route = useRoute(); // 获取路由参数

const Detail = ref('文字描述')

const useGoodsData = useGoodsDataStore();

const GoodsData = storeToRefs(useGoodsData); // 获取商品列表数据

const CateList = ref()

const orderByCate = (CateName: string) => {
    useGoodsData.getGoodsListByGoodsCate(CateName);
}
onMounted(() => {
    useGoodsData.getGoodsList();


    ApiUtil.get("/api/category").then((res) => {
        CateList.value = res.data.data; // 获取分类列表数据
    })

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
    padding: .5em 1em;
    position: sticky;
    top: 0;
    background: rgba(240, 240, 240, 0.589);
    backdrop-filter: blur(1em);
}


.Detail-Container {

    padding: .5em 1em;
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
