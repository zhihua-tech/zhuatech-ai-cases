<!-- Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ -->
<script setup>
import AppIcon from './AppIcon.vue'
</script>
<template>
  <header class="site-header">
    <div class="header-inner">
      <router-link to="/" class="brand">
        <span class="brand-mark">ZH</span>
        <span><strong>知华科技</strong><small>AI APPLICATION LAB</small></span>
      </router-link>
      <nav>
        <router-link to="/">案例中心</router-link>
        <router-link to="/admin"><AppIcon name="dashboard" :size="17" /> 管理控制台</router-link>
        <a href="https://www.zhuatech.cn/" target="_blank">企业官网</a>
      </nav>
      <router-link to="/cases/contract-review" class="header-action">开始体验</router-link>
    </div>
  </header>
</template>
