import { computed, ref } from 'vue'
import { defineStore, storeToRefs } from 'pinia'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import ApiUtil from '@/utils/ApiUtil'
import JwtUtil from '@/utils/JwtUtil'
import type { CartItem } from '@/types/CartItem'


export const useFrontDataStore = defineStore('FrontData', () => {
  // Public
  const router = useRouter()

  const DataInit = () => {

    getUserDataByJwt()
    getCartfromDB()
    getGoodsfromDB()

  }

  // UserData
  const UserJwt = ref('')
  const UserData = ref({
    UserID: -1,
    oldPassword: '',
    Password: '',
    Password1: '',
    UserType: 0,
    UserName: '',
    UserPhone: '',
    UserAddr: "",
    UserCart: [] as CartItem[]
  })
  const logout = () => {
    JwtUtil.deleteJwt();
    UserJwt.value = ''
    // 重置UserData和UserJwt
    UserData.value = {
      UserID: -1,
      oldPassword: '',
      Password: '',
      Password1: '',
      UserType: 0,
      UserName: '',
      UserPhone: '',
      UserAddr: "",
      UserCart: []
    }
    router.push('/')
    clearTempCart()
  }

  const login = async (UserID: string, Password: string): Promise<boolean> => {
    const router = useRouter();
    try {
      const res = await ApiUtil.post('/api/auth', {
        UserID,
        Password,
      });

      if (res.data.code === 200) {
        ElMessage({
          message: res.data.msg,
          type: 'success',
        });

        UserData.value = { ...res.data.data };
        UserJwt.value = res.headers.Authorization;

        if (UserData.value.UserType > 0) {
          await router.push('/dashboard');
        } else {
          await router.push('/');
        }

        return true;
      } else {
        ElMessage({
          message: res.data.msg,
          type: 'error',
        });
        return false;
      }
    } catch (error) {
      console.error(error);
      ElMessage({
        message: '登录过程中发生错误',
        type: 'error',
      });
      return false;
    }
  };
  const deleteAccount = async (): Promise<boolean> => {
    try {
      const res = await ApiUtil.delete('/api/user');

      if (res.data.code === 200) {
        ElMessage({
          message: res.data.msg,
          type: 'success',
        });
        return true;
      } else {
        ElMessage({
          message: res.data.msg || '删除账户失败',
          type: 'error',
        });
        return false;
      }
    } catch (error) {
      console.error(error);
      ElMessage({
        message: '删除账户过程中发生错误',
        type: 'error',
      });
      return false;
    }
  };
  const updateUserData = async (User: any): Promise<boolean> => {
    try {
      const res = await ApiUtil.put('/api/user', {
        UserID: User.UserID,
        UserName: User.UserName,
        UserType: User.UserType,
        UserPhone: User.UserPhone,
        UserAddr: User.UserAddr,
      });

      if (res.data.code === 200) {
        ElMessage({
          message: res.data.msg,
          type: 'success',
        });
        UserData.value = { ...res.data.data };
        UserJwt.value = res.headers.Authorization;
        return true;
      } else {
        ElMessage({
          message: res.data.msg || '更新用户数据失败',
          type: 'error',
        });
        return false;
      }
    } catch (error) {
      console.error(error);
      ElMessage({
        message: '更新用户数据过程中发生错误',
        type: 'error',
      });
      return false;
    }
  };
  const updateUserPwd = async (old: any, pwd: any, pwd1: any): Promise<boolean> => {
    try {
      const res = await ApiUtil.put('/api/user', {
        oldPassword: old,
        Password: pwd,
        Password1: pwd1,
      });

      if (res.data.code === 200) {
        ElMessage({
          message: res.data.msg,
          type: 'success',
        });
        logout();
        return true;
      } else {
        ElMessage({
          message: res.data.msg || 'Failed to update password',
          type: 'error',
        });
        return false;
      }
    } catch (err) {
      console.error(err);
      ElMessage({
        message: 'An error occurred while updating the password',
        type: 'error',
      });
      return false;
    }
  };


  const register = async (User: any): Promise<boolean> => {
    const router = useRouter();
    try {
      const res = await ApiUtil.post('/api/user', {
        UserID: User.UserID,
        UserName: User.UserName,
        Password: User.Password,
        Password1: User.Password,
        UserType: 0,
        UserPhone: User.UserPhone,
        UserAddr: User.UserAddr,
      });

      if (res.data.code === 200) {
        ElMessage({
          message: res.data.msg,
          type: 'success',
        });
        UserData.value = res.data.data;
        UserJwt.value = res.headers.Authorization;
        // 跳转到首页
        await router.push('/auth/login');
        return true;
      } else {
        ElMessage({
          message: res.data.msg || '注册失败',
          type: 'error',
        });
        return false;
      }
    } catch (error) {
      console.error(error);
      ElMessage({
        message: '注册过程中发生错误',
        type: 'error',
      });
      return false;
    }
  };

  const getUserDataByJwt = async (): Promise<boolean> => {
    try {
      const jwt = JwtUtil.getJwt();
      if (!jwt) {
        return false; // JWT 不存在时返回 false
      }

      UserJwt.value = jwt;
      const res = await ApiUtil.get('/api/auth');

      if (res.data.code === 200) {
        UserData.value.UserID = res.data.data.UserID;
        UserData.value.UserName = res.data.data.UserName;
        UserData.value.UserType = res.data.data.UserType;
        UserData.value.UserPhone = res.data.data.UserPhone;
        UserData.value.UserAddr = res.data.data.UserAddr;
        UserData.value.UserCart = res.data.data.UserCart;
        UserJwt.value = res.headers.Authorization;
        return true;
      } else {
        return false; // 响应代码不为 200 时返回 false
      }
    } catch (error) {
      console.error(error);
      return false; // 发生错误时返回 false
    }
  };




  //Goods

  const GoodsList = ref([]); // 定义商品列表数据源。
  const UIGoodsList: any = ref([]);
  const CateList: any = ref([])

  const hasUIGoods = computed(() => { // 计算属性，用于判断商品列表数据源是否为空。
    return UIGoodsList.value.length > 0
  })


  const getGoodsfromDB = () => {
    // 尝试从后端获取商品列表数据。
    let url = '/api/goods' // 设置后端接口的URL。
    ApiUtil.get(url).then(res => { // 调用后端接口获取商品列表数据。
      GoodsList.value = res.data.data;
      UIGoodsList.value = res.data.data; // 将获取到的商品列表数据存储到商品列表数据源中。
    })
    ApiUtil.get('/api/category').then(res => { // 调用后端接口获取分类列表数据。
      CateList.value = res.data.data; // 将获取到的分类列表数据存储到分类列表数据源中。
    })
  }

  const setUIGoodsListByCateName = (CateName: string) => {
    if (CateName === 'all') {
      UIGoodsList.value = GoodsList.value
    } else {
      UIGoodsList.value = GoodsList.value.filter((item: { GoodsCate: string }) => item.GoodsCate === CateName); // 根据分类名称过滤商品列表数据。
    }
  }



  //Cart

  const tempUserCart = ref<CartItem[]>([]);

  const getCartfromDB = () => { // 获取购物车列表，从数据库中获取。
    // 调用数据库接口，获取购物车列表。
    ApiUtil.get('/api/cart').then((res: any) => {

      if (res.data.code === 200) { // 获取成功，更新购物车列表。
        tempUserCart.value = res.data.data; // 更新临时购物车列表。
        UserData.value.UserCart = res.data.data; // 更新UserDataStore中的UserData.CartList字段。
        ElMessage.success(res.data.msg); // 提示用户获取成功。
      }
    })
  }
  const addToTempCart = (GoodsID: number) => {
    const IteminGoodsList: any = GoodsList.value.find((item: { GoodsID: number }) => item.GoodsID === GoodsID) as CartItem | undefined
    const IteminCartList: any = tempUserCart.value.find(item => item.GoodsID === GoodsID) as CartItem | undefined
    const ItemtoCart: CartItem = {
      GoodsID: IteminGoodsList.GoodsID,
      GoodsName: IteminGoodsList.GoodsName,
      // GoodsImg: IteminGoodsList.GoodsImg,
      GoodsPerPrice: IteminGoodsList.GoodsPrice,
      CountNum: 1,
    }
    if (IteminCartList) { // 购物车中已有该商品，数量加1
      IteminCartList.CountNum++;
      ElMessage.success('数量+1'); // 提示用户添加成功
    } else {
      ElMessage.success('添加成功');
      tempUserCart.value.push(ItemtoCart); // 购物车中没有该商品，添加到购物车中
    }

  }

  const clearTempCart = () => { // 清空临时购物车，以便重新添加商品。
    tempUserCart.value = []; // 清空临时购物车。
  }
  const saveCarttoDB = async (): Promise<boolean> => {
    try {
      const res = await ApiUtil.put('/api/cart', {
        UserID: UserData.value.UserID,
        UserCart: JSON.stringify(tempUserCart.value),
      });

      if (res.data.code === 200) {
        UserData.value.UserCart = res.data.data;
        UserJwt.value = res.headers.Authorization;
        return true;
      } else {
        console.error('Error code:', res.data.code);
        return false;
      }
    } catch (err) {
      console.error(err);
      return false;
    }
  };


  return {
    DataInit,
    // User
    UserData,
    UserJwt,
    login,
    logout,
    register,
    deleteAccount,
    updateUserData,
    updateUserPwd,

    // Goods
    GoodsList,
    UIGoodsList,
    CateList,
    setUIGoodsListByCateName,
    getGoodsfromDB,
    hasUIGoods,

    // Cart
    tempUserCart,
    getCartfromDB,
    saveCarttoDB,
    clearTempCart,
    addToTempCart,
  }
})

