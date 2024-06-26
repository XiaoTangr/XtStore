<template>
    <el-dialog modal-class="goods-detail-modal" draggable align-center v-model="isShow" width="24em" center title="商品详情"
        destroy-on-close @closed="onClose">
        <div class="container">
            <div class="header">
                <el-image :src="activeDetailGoods.GoodsImg" />
            </div>
            <div class="body">
                <div class="GoodsName">
                    商品名称： {{ activeDetailGoods.GoodsName }}
                </div>
                <div class="GoodsPrice">
                    商品价格：￥{{ activeDetailGoods.GoodsPerPrice }} {{ activeDetailGoods.GoodsPerUnit }}
                </div>
                <div class="GoodsDesc">
                    商品描述： {{ activeDetailGoods.GoodsDesc }}
                </div>
                <div class="GoodsInven">
                    剩余库存： {{ activeDetailGoods.GoodsInven }}
                </div>
                <div class="GoodsCate">
                    所属分类： {{ activeDetailGoods.GoodsCate }}
                </div>
            </div>
        </div>
    </el-dialog>

</template>

<script setup lang="ts">
import { useFrontDataStore } from '@/stores/FrontData';
import { storeToRefs } from 'pinia';
import { computed } from 'vue';

const FrontDataStore = useFrontDataStore();

const { activeDetailGoods } = storeToRefs(FrontDataStore); // 解构出store中的activeDetailGoods属性，并将其转换为响应式对象。

const isShow = computed(() => {
    return activeDetailGoods.value.GoodsID > 0;
})

const onClose = () => {
    activeDetailGoods.value.GoodsID = -2; // 关闭弹窗时，将activeDetailGoods的GoodsID属性重置为-1。
}
</script>

<style scoped>
.body *{
    margin:  .25em 0;
} 

</style>
<style>
.goods-detail-modal {
    backdrop-filter: blur(1em) !important;
}
</style>