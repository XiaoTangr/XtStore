import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { ElAlert, ElMessage } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import ApiUtil from '@/utils/ApiUtil'
import JwtUtil from '@/utils/JwtUtil'

export const useUserDataStore = defineStore('UserData', () => {
  const UserData = ref({
    UserID: -1,
    oldPassword: '',
    Password: '',
    Password1: '',
    UserType: 0,
    UserName: '',
    UserPhone: '',
    UserAddr: "",
    UserCart: ""
  })
  const router = useRouter()
  const UserJwt = ref('')
  const UserCart = computed(() => UserData.value.UserCart)

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
      UserCart: ""
    }
    router.push('/')
  }

  const login = async (UserID: string, Password: string) => {
    ApiUtil.post('/api/auth', {
      UserID: UserID,
      Password: Password
    }).then(res => {
      if (res.data.code === 200) {
        ElMessage({
          message: res.data.msg,
          type: 'success',
        });

        UserData.value.UserID = res.data.data.UserID;
        UserData.value.UserName = res.data.data.UserName;
        UserData.value.UserType = res.data.data.UserType;
        UserData.value.UserPhone = res.data.data.UserPhone;
        UserData.value.UserAddr = res.data.data.UserAddr;

        UserData.value.UserCart = res.data.data.UserCart;
        UserJwt.value = res.headers.Authorization

        if(UserData.value.UserType > 0){
          router.push('/dashboard')
        }else{
          // 跳转到首页
          router.push('/')
        }


      } else {
        ElMessage({
          message: res.data.msg,
          type: 'error',
        });
      }
    })
  }

  const deleteAcccount = async () => {

    ApiUtil.delete('/api/user').then(res => {
      if (res.data.code === 200) {
        ElMessage({
          message: res.data.msg,
          type: 'success',
        });
      }
    })
  }

  const updateUserData = async (User: any) => {
    ApiUtil.put('/api/user', {
      UserID: User.UserID,
      UserName: User.UserName,
      oldPassword: User.oldPassword,
      Password: User.Password,
      Password1: User.Password,
      UserType: User.UserType,
      UserPhone: User.UserPhone,
      UserAddr: User.UserAddr,
      UserCart: User.UserCart

    }).then((res) => {
      if (res.data.code === 200) {
        ElMessage({
          message: res.data.msg,
          type: 'success',
        });
        UserData.value.UserID = res.data.data.UserID;
        UserData.value.UserName = res.data.data.UserName;
        UserData.value.UserType = res.data.data.UserType;
        UserData.value.UserPhone = res.data.data.UserPhone;
        UserData.value.UserAddr = res.data.data.UserAddr;

        UserData.value.UserCart = res.data.data.UserCart;
        UserJwt.value = res.headers.Authorization
      }
    })
  }





  const updateUserPwd = async (old: any, pwd: any, pwd1: any) => {
    ApiUtil.put('/api/user', {
      UserID: UserData.value.UserID,
      UserName: UserData.value.UserName,
      oldPassword: old,
      Password: pwd,
      Password1: pwd1,
      UserType: UserData.value.UserType,
      UserPhone: UserData.value.UserPhone,
      UserAddr: UserData.value.UserAddr,
      UserCart: UserData.value.UserCart

    }).then((res) => {
      if (res.data.code === 200) {
        ElMessage({
          message: res.data.msg,
          type: 'success',
        });
        logout()
      }
    })
  }


  const register = async (User: any) => {
    ApiUtil.post('/api/user', {

      UserID: User.UserID,
      UserName: User.UserName,
      Password: User.Password,
      Password1: User.Password,
      UserType: 0,
      UserPhone: User.UserPhone,
      UserAddr: User.UserAddr,

    }).then(res => {
      if (res.data.code === 200) {
        ElMessage({
          message: res.data.msg,
          type: 'success',
        });
        UserData.value = res.data.data;
        UserJwt.value = res.headers.Authorization
        // 跳转到首页
        router.push('/auth/login')
      }
    })
  }

  const getUserDataByJwt = () => {
    const jwt = JwtUtil.getJwt();
    if (jwt) {
      UserJwt.value = jwt;
      ApiUtil.get('/api/auth').then(res => {
        if (res.data.code === 200) {
          UserData.value.UserID = res.data.data.UserID;
          UserData.value.UserName = res.data.data.UserName;
          UserData.value.UserType = res.data.data.UserType;
          UserData.value.UserPhone = res.data.data.UserPhone;
          UserData.value.UserAddr = res.data.data.UserAddr;

          UserData.value.UserCart = res.data.data.UserCart;
          UserJwt.value = res.headers.Authorization
        }else {
          alert(res.data.msg)
        }
      })
    }
  }

  return { UserData, UserJwt, login, register, logout, UserCart, deleteAcccount, updateUserData, getUserDataByJwt, updateUserPwd }
})

