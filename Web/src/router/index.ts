import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
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
      path: "/auth",
      name: "auth",
      component: () => import('../views/AuthView.vue')
    },
  ]
})

export default router
