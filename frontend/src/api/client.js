/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
import axios from 'axios'
import { cases, demoResults } from '../data/cases.js'

const http = axios.create({ baseURL: import.meta.env.VITE_API_BASE_URL || '/api', timeout: 30000 })
const demoMode = import.meta.env.VITE_DEMO_MODE !== 'false'
const delay = ms => new Promise(resolve => setTimeout(resolve, ms))

export async function listCases(includeDisabled = false) {
  if (demoMode) return cases.filter(item => includeDisabled || item.enabled)
  const { data } = await http.get(includeDisabled ? '/admin/cases' : '/cases')
  return data.data
}

export async function getCase(slug) {
  if (demoMode) return cases.find(item => item.slug === slug)
  const { data } = await http.get(`/cases/${slug}`)
  return data.data
}

export async function runCase(slug, inputs) {
  if (demoMode) {
    await delay(650)
    return { caseSlug: slug, ...demoResults[slug], structuredData: { inputFields: Object.keys(inputs).length }, executionMode: 'LOCAL_RULES', provider: 'local', model: 'deepseek-chat', durationMs: 18 }
  }
  const { data } = await http.post(`/cases/${slug}/run`, { inputs })
  return data.data
}

export async function adminOverview() {
  if (demoMode) return { caseCount: 10, enabledCount: 10, executionCount: 1286, last24Hours: 86, failedCount: 4, provider: { provider:'local', model:'deepseek-chat', configured:false, label:'本地规则模式' } }
  const { data } = await http.get('/admin/overview')
  return data.data
}

export async function recentExecutions() {
  if (demoMode) return [
    { id:1068, caseSlug:'contract-review', resultSummary:'识别4项合同风险，建议人工复核', executionMode:'LOCAL_RULES', durationMs:21, success:true, createdAt:'2026-08-22 19:42' },
    { id:1067, caseSlug:'sales-copilot', resultSummary:'客户意向评分81，建议两日内跟进', executionMode:'LOCAL_RULES', durationMs:17, success:true, createdAt:'2026-08-22 19:38' },
    { id:1066, caseSlug:'equipment-diagnosis', resultSummary:'温度和振动触发高风险阈值', executionMode:'LOCAL_RULES', durationMs:13, success:true, createdAt:'2026-08-22 19:32' },
    { id:1065, caseSlug:'document-qa', resultSummary:'回答已生成并返回原文引用', executionMode:'LOCAL_RULES', durationMs:15, success:true, createdAt:'2026-08-22 19:25' }
  ]
  const { data } = await http.get('/admin/executions?limit=20')
  return data.data
}

export { demoMode }
