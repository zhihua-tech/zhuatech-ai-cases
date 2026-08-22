/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import CaseStudioView from '../views/CaseStudioView.vue'
import AdminDashboardView from '../views/AdminDashboardView.vue'

export default createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', component: HomeView },
    { path: '/cases/:slug', component: CaseStudioView },
    { path: '/admin', component: AdminDashboardView }
  ],
  scrollBehavior: () => ({ top: 0 })
})
