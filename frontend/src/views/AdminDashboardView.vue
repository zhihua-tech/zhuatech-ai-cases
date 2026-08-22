<!-- Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ -->
<script setup>
import { onMounted, ref } from 'vue'
import SiteHeader from '../components/SiteHeader.vue'
import AppIcon from '../components/AppIcon.vue'
import { adminOverview, listCases, recentExecutions } from '../api/client.js'

const overview = ref({ provider:{} }); const items = ref([]); const runs = ref([])
const slugNames = ref({})
onMounted(async () => {
  [overview.value, items.value, runs.value] = await Promise.all([adminOverview(), listCases(true), recentExecutions()])
  slugNames.value = Object.fromEntries(items.value.map(item => [item.slug, item.name]))
})
</script>

<template>
  <SiteHeader />
  <main class="admin-page">
    <div class="admin-heading"><div><span>OPERATIONS CONSOLE</span><h1>案例运营控制台</h1><p>管理案例状态、模型连接与运行质量。</p></div><div class="admin-date">数据更新时间<br/><b>2026-08-22 19:45</b></div></div>
    <div class="metric-grid">
      <div><span class="metric-icon navy"><AppIcon name="dashboard"/></span><p>案例总数</p><b>{{ overview.caseCount || 0 }}</b><small>{{ overview.enabledCount || 0 }} 个已启用</small></div>
      <div><span class="metric-icon teal"><AppIcon name="spark"/></span><p>累计运行</p><b>{{ (overview.executionCount || 0).toLocaleString() }}</b><small class="positive">较上周 +18.6%</small></div>
      <div><span class="metric-icon amber"><AppIcon name="chart"/></span><p>24 小时调用</p><b>{{ overview.last24Hours || 0 }}</b><small>平均响应 18 ms</small></div>
      <div><span class="metric-icon violet"><AppIcon name="equipment"/></span><p>运行成功率</p><b>99.7%</b><small>{{ overview.failedCount || 0 }} 次需关注</small></div>
    </div>
    <div class="admin-grid">
      <section class="admin-card model-card">
        <div class="admin-card-title"><div><b>模型与运行模式</b><span>可选外部模型连接状态</span></div><span :class="['connection-state',{online:overview.provider?.configured}]"><i></i>{{ overview.provider?.configured ? '已连接' : '本地模式' }}</span></div>
        <div class="model-info"><div class="model-logo">DS</div><div><b>{{ overview.provider?.model || 'deepseek-chat' }}</b><span>OpenAI 兼容调用协议</span></div><button>配置说明</button></div>
        <div class="model-details"><div><span>当前 Provider</span><b>{{ overview.provider?.provider || 'local' }}</b></div><div><span>模型增强</span><b>{{ overview.provider?.configured ? '启用' : '未启用' }}</b></div><div><span>故障回退</span><b>本地规则</b></div></div>
        <p class="model-note">未配置密钥时，全部案例使用确定性业务规则运行；外部模型不可用时自动回退。</p>
      </section>
      <section class="admin-card usage-card">
        <div class="admin-card-title"><div><b>场景调用分布</b><span>最近 30 天</span></div><a>查看明细</a></div>
        <div class="usage-list">
          <div><span>合同风险审查</span><div><i style="width:82%"></i></div><b>28%</b></div>
          <div><span>销售跟进助手</span><div><i style="width:64%"></i></div><b>22%</b></div>
          <div><span>会议纪要助手</span><div><i style="width:52%"></i></div><b>18%</b></div>
          <div><span>企业文档问答</span><div><i style="width:41%"></i></div><b>14%</b></div>
          <div><span>其他案例</span><div><i style="width:52%"></i></div><b>18%</b></div>
        </div>
      </section>
    </div>
    <section class="admin-card cases-table-card">
      <div class="admin-card-title"><div><b>案例状态</b><span>首发案例运行与展示配置</span></div><button class="outline-button">导出配置</button></div>
      <div class="table-wrap"><table><thead><tr><th>案例</th><th>业务分类</th><th>运行方式</th><th>首页推荐</th><th>状态</th><th>操作</th></tr></thead><tbody>
        <tr v-for="item in items.slice(0,6)" :key="item.slug"><td><span :class="['table-icon',`accent-${item.accent}`]"><AppIcon :name="item.icon" :size="17"/></span><b>{{ item.name }}</b></td><td>{{ item.category }}</td><td><span class="rule-chip">本地规则 + 模型预留</span></td><td>{{ item.featured ? '是' : '否' }}</td><td><span class="enabled-dot"><i></i>运行中</span></td><td><router-link :to="`/cases/${item.slug}`">打开案例</router-link></td></tr>
      </tbody></table></div>
    </section>
    <section class="admin-card activity-card">
      <div class="admin-card-title"><div><b>最近运行记录</b><span>业务结果和运行耗时审计</span></div><a>查看全部</a></div>
      <div class="activity-list"><div v-for="run in runs" :key="run.id"><span class="activity-status">✓</span><p><b>{{ slugNames[run.caseSlug] || run.caseSlug }}</b><small>{{ run.resultSummary }}</small></p><span>{{ run.executionMode }}</span><time>{{ run.durationMs }} ms<br/><small>{{ run.createdAt }}</small></time></div></div>
    </section>
  </main>
</template>
