<!-- Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ -->
<script setup>
defineProps({ name: { type: String, default: 'spark' }, size: { type: Number, default: 22 } })
const paths = {
  contract: '<path d="M7 3h7l4 4v14H7z"/><path d="M14 3v5h5M10 12h5M10 16h5"/>',
  meeting: '<path d="M4 6h16v11H8l-4 4z"/><path d="M8 10h8M8 14h5"/>',
  expense: '<rect x="4" y="5" width="16" height="14" rx="2"/><path d="M8 9h8M8 13h4M15 13h1M8 16h8"/>',
  invoice: '<path d="M6 3h12v18l-3-2-3 2-3-2-3 2z"/><path d="M9 8h6M9 12h6M9 16h3"/>',
  resume: '<rect x="5" y="3" width="14" height="18" rx="2"/><circle cx="12" cy="9" r="2"/><path d="M8 16c1-3 7-3 8 0"/>',
  sales: '<path d="M4 19V9M10 19V5M16 19v-7M3 19h18"/><path d="m5 7 5-4 6 6 4-4"/>',
  service: '<path d="M4 13v-2a8 8 0 0 1 16 0v2"/><path d="M4 13h3v6H5a2 2 0 0 1-2-2v-2a2 2 0 0 1 1-2ZM20 13h-3v6h2a2 2 0 0 0 2-2v-2a2 2 0 0 0-1-2ZM17 19c0 2-2 2-4 2"/>',
  document: '<path d="M6 3h9l3 3v15H6z"/><path d="M14 3v5h5M9 12h6M9 16h6"/>',
  chart: '<rect x="3" y="3" width="18" height="18" rx="2"/><path d="m7 15 3-4 3 2 4-6M7 18h10"/>',
  equipment: '<circle cx="12" cy="12" r="3"/><path d="M19.4 15a1.7 1.7 0 0 0 .3 1.9l.1.1-2.8 2.8-.1-.1a1.7 1.7 0 0 0-1.9-.3 1.7 1.7 0 0 0-1 1.6v.2h-4V21a1.7 1.7 0 0 0-1-1.6 1.7 1.7 0 0 0-1.9.3l-.1.1L4.2 17l.1-.1a1.7 1.7 0 0 0 .3-1.9A1.7 1.7 0 0 0 3 14H2.8v-4H3a1.7 1.7 0 0 0 1.6-1 1.7 1.7 0 0 0-.3-1.9L4.2 7 7 4.2l.1.1A1.7 1.7 0 0 0 9 4.6a1.7 1.7 0 0 0 1-1.6v-.2h4V3a1.7 1.7 0 0 0 1 1.6 1.7 1.7 0 0 0 1.9-.3l.1-.1L19.8 7l-.1.1a1.7 1.7 0 0 0-.3 1.9 1.7 1.7 0 0 0 1.6 1h.2v4H21a1.7 1.7 0 0 0-1.6 1Z"/>',
  dashboard: '<rect x="3" y="3" width="7" height="7" rx="1"/><rect x="14" y="3" width="7" height="4" rx="1"/><rect x="14" y="11" width="7" height="10" rx="1"/><rect x="3" y="14" width="7" height="7" rx="1"/>',
  spark: '<path d="m12 3 1.7 4.3L18 9l-4.3 1.7L12 15l-1.7-4.3L6 9l4.3-1.7z"/><path d="m18 15 .8 2.2L21 18l-2.2.8L18 21l-.8-2.2L15 18l2.2-.8z"/>'
}
</script>
<template>
  <svg :width="size" :height="size" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true" v-html="paths[name] || paths.spark" />
</template>
