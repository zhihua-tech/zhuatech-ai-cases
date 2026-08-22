<!-- Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ -->
<script setup>
import AppIcon from './AppIcon.vue'
defineProps({ item: { type: Object, required: true } })
</script>
<template>
  <router-link :to="`/cases/${item.slug}`" class="case-card">
    <div class="case-card-top">
      <span :class="['case-icon', `accent-${item.accent}`]"><AppIcon :name="item.icon" /></span>
      <span v-if="item.featured" class="featured-tag">推荐</span>
    </div>
    <div class="case-category">{{ item.category }}</div>
    <h3>{{ item.name }}</h3>
    <p>{{ item.summary }}</p>
    <div class="case-card-bottom"><span>打开体验工作台</span><span class="arrow">→</span></div>
  </router-link>
</template>
