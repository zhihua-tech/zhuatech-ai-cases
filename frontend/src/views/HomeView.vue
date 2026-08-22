<!-- Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ -->
<script setup>
import { computed, onMounted, ref } from 'vue'
import SiteHeader from '../components/SiteHeader.vue'
import CaseCard from '../components/CaseCard.vue'
import AppIcon from '../components/AppIcon.vue'
import { listCases } from '../api/client.js'

const items = ref([])
const active = ref('全部场景')
const categories = computed(() => ['全部场景', ...new Set(items.value.map(item => item.category))])
const filtered = computed(() => active.value === '全部场景' ? items.value : items.value.filter(item => item.category === active.value))
onMounted(async () => { items.value = await listCases() })
</script>

<template>
  <SiteHeader />
  <main>
    <section class="hero">
      <div class="hero-inner">
        <div class="hero-copy">
          <div class="eyebrow"><span></span> 企业 AI 场景验证与交付底座</div>
          <h1>把 AI 能力，落到<br/><em>真实业务流程</em>里</h1>
          <p>面向企业管理、财务、法务、销售与生产运维的可运行案例。默认本地规则即可体验，也可接入 DeepSeek 等兼容模型进行增强。</p>
          <div class="hero-actions">
            <a href="#case-library" class="primary-button">浏览案例库 <span>→</span></a>
            <router-link to="/admin" class="secondary-button">查看管理控制台</router-link>
          </div>
          <div class="trust-row">
            <span><b>10</b> 个首发案例</span><i></i><span><b>3</b> 层可替换架构</span><i></i><span><b>0</b> 密钥也可运行</span>
          </div>
        </div>
        <div class="hero-panel">
          <div class="panel-head"><span>本周案例运行概览</span><small>实时演示数据</small></div>
          <div class="panel-metrics"><div><b>1,286</b><span>累计运行</span></div><div><b>97.8%</b><span>规则命中率</span></div></div>
          <div class="activity-chart">
            <div class="chart-labels"><span>业务调用趋势</span><strong>+18.6%</strong></div>
            <svg viewBox="0 0 420 128" preserveAspectRatio="none">
              <defs><linearGradient id="area" x1="0" y1="0" x2="0" y2="1"><stop offset="0" stop-color="#187b77" stop-opacity=".28"/><stop offset="1" stop-color="#187b77" stop-opacity="0"/></linearGradient></defs>
              <path d="M0 104 C45 92 58 96 92 72 S145 84 178 58 S230 72 264 44 S320 56 352 30 S390 36 420 16 V128 H0Z" fill="url(#area)"/>
              <path d="M0 104 C45 92 58 96 92 72 S145 84 178 58 S230 72 264 44 S320 56 352 30 S390 36 420 16" fill="none" stroke="#187b77" stroke-width="3"/>
            </svg>
            <div class="chart-days"><span>周一</span><span>周二</span><span>周三</span><span>周四</span><span>周五</span><span>周六</span><span>今天</span></div>
          </div>
          <div class="latest-run"><span class="run-icon"><AppIcon name="contract" :size="19"/></span><div><b>合同风险审查</b><small>刚刚完成 · 识别 4 项风险</small></div><span class="run-status">已完成</span></div>
        </div>
      </div>
    </section>

    <section class="value-strip">
      <div><AppIcon name="spark"/><span><b>规则与模型协同</b><small>结果稳定，解释清晰</small></span></div>
      <div><AppIcon name="document"/><span><b>场景即开即用</b><small>表单、接口、结果完整</small></span></div>
      <div><AppIcon name="dashboard"/><span><b>统一运营管理</b><small>案例、调用、模型可观测</small></span></div>
      <div><AppIcon name="equipment"/><span><b>企业级可扩展</b><small>方便对接现有业务系统</small></span></div>
    </section>

    <section id="case-library" class="case-library section-shell">
      <div class="section-heading">
        <div><span class="section-kicker">CASE LIBRARY</span><h2>企业 AI 应用案例库</h2><p>选择一个业务场景，查看输入、规则分析和结构化输出的完整过程。</p></div>
        <span class="case-count">{{ filtered.length }} 个案例</span>
      </div>
      <div class="filter-row">
        <button v-for="category in categories" :key="category" :class="{ active: active === category }" @click="active = category">{{ category }}</button>
      </div>
      <div class="case-grid"><CaseCard v-for="item in filtered" :key="item.slug" :item="item" /></div>
    </section>

    <section class="architecture-section">
      <div class="section-shell architecture-inner">
        <div class="architecture-copy"><span class="section-kicker light">DELIVERY READY</span><h2>不是一组静态页面，<br/>而是一套可交付的案例运行平台</h2><p>前端体验、后端规则、调用记录、模型适配和后台运营形成完整闭环，方便企业在验证后继续扩展为生产系统。</p><a href="https://www.zhuatech.cn/" target="_blank">联系知华科技进行深度定制 →</a></div>
        <div class="architecture-flow">
          <div><span>01</span><b>业务输入</b><small>文本、表单与指标</small></div><i>→</i><div><span>02</span><b>规则分析</b><small>确定性业务结论</small></div><i>→</i><div><span>03</span><b>模型增强</b><small>DeepSeek 可选接入</small></div><i>→</i><div><span>04</span><b>审计输出</b><small>结果、证据与记录</small></div>
        </div>
      </div>
    </section>
  </main>
  <footer><div><span>© 2026 上海如静知华信息科技有限公司</span><a href="https://www.zhuatech.cn/">www.zhuatech.cn</a></div><small>本社区源码仅限个人学习交流，商业使用须获得书面授权。</small></footer>
</template>
