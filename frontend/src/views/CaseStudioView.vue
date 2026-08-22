<!-- Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ -->
<script setup>
import { computed, onMounted, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import SiteHeader from '../components/SiteHeader.vue'
import AppIcon from '../components/AppIcon.vue'
import { defaultInputs } from '../data/cases.js'
import { getCase, listCases, runCase } from '../api/client.js'

const route = useRoute(); const router = useRouter()
const item = ref(null); const allCases = ref([]); const inputs = ref({}); const result = ref(null)
const running = ref(false); const error = ref('')
const scoreStyle = computed(() => ({ '--score': `${result.value?.score || 0}%` }))

async function load() {
  item.value = await getCase(route.params.slug)
  if (!item.value) return router.replace('/')
  allCases.value = await listCases()
  inputs.value = defaultInputs(item.value)
  result.value = null; error.value = ''
}
async function execute() {
  error.value = ''
  const missing = item.value.fields.find(field => field.required && !String(inputs.value[field.key] ?? '').trim())
  if (missing) { error.value = `请填写${missing.label}`; return }
  running.value = true
  try { result.value = await runCase(item.value.slug, inputs.value) }
  catch (e) { error.value = e.response?.data?.message || '案例执行失败，请稍后重试' }
  finally { running.value = false }
}
watch(() => route.params.slug, load)
onMounted(async () => { await load(); await execute() })
</script>

<template>
  <SiteHeader />
  <main v-if="item" class="studio-page">
    <div class="studio-breadcrumb"><router-link to="/">案例中心</router-link><span>/</span><b>{{ item.name }}</b></div>
    <div class="studio-layout">
      <aside class="case-sidebar">
        <div class="sidebar-title">全部案例</div>
        <router-link v-for="entry in allCases" :key="entry.slug" :to="`/cases/${entry.slug}`" :class="{active:entry.slug===item.slug}">
          <span :class="['mini-icon',`accent-${entry.accent}`]"><AppIcon :name="entry.icon" :size="18"/></span><span>{{ entry.name }}<small>{{ entry.category }}</small></span>
        </router-link>
      </aside>
      <section class="studio-main">
        <div class="studio-heading">
          <div :class="['studio-icon',`accent-${item.accent}`]"><AppIcon :name="item.icon" :size="27"/></div>
          <div><span>{{ item.category }}</span><h1>{{ item.name }}</h1><p>{{ item.summary }}</p></div>
          <div class="mode-chip"><i></i> 本地规则可运行</div>
        </div>
        <div class="studio-workspace">
          <div class="input-panel">
            <div class="panel-title"><div><span>01</span><b>业务输入</b></div><small>使用示例数据快速体验</small></div>
            <div class="form-fields">
              <label v-for="field in item.fields" :key="field.key">
                <span>{{ field.label }} <em v-if="field.required">*</em></span>
                <textarea v-if="field.type==='textarea'" v-model="inputs[field.key]" rows="5" :placeholder="field.placeholder"></textarea>
                <input v-else v-model="inputs[field.key]" :type="field.type" :placeholder="field.placeholder" />
              </label>
            </div>
            <div v-if="error" class="form-error">{{ error }}</div>
            <button class="run-button" :disabled="running" @click="execute"><AppIcon name="spark" :size="18"/> {{ running ? '正在分析业务数据…' : '运行案例分析' }}</button>
            <p class="privacy-note">演示数据仅在当前环境处理，请勿输入真实个人敏感信息或商业秘密。</p>
          </div>
          <div class="result-panel">
            <div class="panel-title"><div><span>02</span><b>分析结果</b></div><small v-if="result">{{ result.durationMs }} ms · {{ result.executionMode }}</small></div>
            <div v-if="running" class="result-loading"><span></span><b>正在执行规则分析</b><small>结构化输入 · 评估业务信号 · 生成处理建议</small></div>
            <div v-else-if="result" class="result-content">
              <div class="result-summary">
                <div class="score-ring" :style="scoreStyle"><span><b>{{ result.score }}</b><small>综合评分</small></span></div>
                <div><span :class="['level-tag',result.level.toLowerCase()]">{{ result.level }}</span><h2>{{ result.title }}</h2><p>{{ result.summary }}</p></div>
              </div>
              <div class="result-section"><h3><span>关键发现</span><small>{{ result.findings.length }} 项</small></h3><ul class="finding-list"><li v-for="finding in result.findings" :key="finding"><i>✓</i><span>{{ finding }}</span></li></ul></div>
              <div class="result-section"><h3><span>建议动作</span><small>建议人工确认</small></h3><ol class="action-list"><li v-for="(action,index) in result.actions" :key="action"><b>{{ String(index+1).padStart(2,'0') }}</b><span>{{ action }}</span></li></ol></div>
              <div class="result-foot"><span>运行方式：{{ result.executionMode === 'LOCAL_RULES' ? '本地业务规则' : '模型增强' }}</span><span>模型预留：{{ result.model }}</span></div>
            </div>
            <div v-else class="result-empty"><AppIcon name="spark" :size="32"/><b>等待运行案例</b><span>填写业务信息后，分析结果将在这里展示</span></div>
          </div>
        </div>
      </section>
    </div>
  </main>
</template>
