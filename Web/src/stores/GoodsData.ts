import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import ApiUtil from '@/utils/ApiUtil'


export const useGoodsDataStore = defineStore('GoodsData', () => {

    const GoodsList = ref() // 商品列表数据源，用于存储从后端获取的商品列表数据。

    const hasGoods = computed(() => { // 计算属性，用于判断商品列表数据源是否为空。
        return GoodsList.value && GoodsList.value.length > 0 // 如果商品列表数据源不为空，则返回true，否则返回false。
    })

    const getGoodsListByGoodsCate = (GoodsCate: string) => {
        // 尝试从后端获取商品列表数据。
        let url = '/api/goods'
        if (GoodsCate !== "all") {
            url += '?GoodsCate=' + GoodsCate
        }

        // 设置后端接口的URL。
        ApiUtil.get(url).then(res => { // 调用后端接口获取商品列表数据。
            GoodsList.value = res.data.data // 将获取到的数据存储到GoodsList变量中。
        })
    }
    

    // 获取商品列表数据的方法。}
    const getGoodsList = () => { // 获取商品列表数据的方法。
        // 尝试从后端获取商品列表数据。
        let url = '/api/goods' // 设置后端接口的URL。
        ApiUtil.get(url).then(res => { // 调用后端接口获取商品列表数据。
            GoodsList.value = res.data.data // 将获取到的数据存储到GoodsList变量中。
        })
    }
    return { GoodsList, getGoodsList, getGoodsListByGoodsCate, hasGoods }
})