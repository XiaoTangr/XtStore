import ApiUtil from '@/utils/ApiUtil';
import InstanceUtil from '@/utils/ApiUtil';
import JwtUtil from '@/utils/JwtUtil';
import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/:GoodsCate?',
      name: 'home',
      component: () => import('../views/Frontend/IndexView.vue'),
      children: [
        {
          path: "",
          name: "frontindex",
          component: () => import('../views/Frontend/Children/IndexView.vue'),
        },
        {
          path: "user",
          name: "frontuser",
          component: () => import('../views/Frontend/Children/UserView.vue')
        },
        {
          path: "about",
          name: "frontabout",
          component: () => import('../views/Frontend/Children/AboutView.vue')
        }
      ]
    },
    {
      path: "/dashboard",
      name: "dashboard",
      component: () => import('../views/Dashboard/IndexView.vue'),
      children: [
        {
          path: "",
          name: "dashindex",
          component: () => import('../views/Dashboard/Children/ForIndex.vue'),
        },
        {
          path: "user",
          name: "dashuser",
          component: () => import('../views/Dashboard/Children/ForUser.vue')
        },
        {
          path: "goods",
          name: "dashabout",
          component: () => import('../views/Dashboard/Children/ForGoods.vue')
        }
      ]
    },
    {
      path: "/auth/:operation?",
      name: "auth",
      component: () => import('../views/AuthView.vue'),
      beforeEnter: (to, from, next) => {
        const operation = to.params.operation || 'login';
        if (operation !== 'login' && operation !== 'register') {
          next('/auth/login');
        } else {
          to.meta.operation = operation;
          next();
        }
      }
    }
  ]
})

router.beforeEach((to, from, next) => {
  const allowedPaths = ['','/','/about', '/auth/', '/auth/login', '/auth/register'];
  if (allowedPaths.includes(to.path)) {
    next();
  } else {
    if (JwtUtil.isJwtExist()) {
      ApiUtil.get('/api/auth').then(response => {
        if (response.data.code === 200) {
          next();
        } else {
          router.push('/auth/login');
        }
      })
    } else {
      router.push('/auth/login');
    }
  }
})
export default router
