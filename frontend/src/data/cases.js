/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
const field = (key, label, type, placeholder, required = true) => ({ key, label, type, placeholder, required })

export const cases = [
  { id: 1, slug: 'contract-review', name: '合同风险审查', category: '法务合规', icon: 'contract', accent: 'navy', featured: true, enabled: true,
    summary: '识别权责失衡、付款、违约、续约和数据安全风险。', fields: [field('contractText','合同条款','textarea','甲方应在验收后90日内付款……'), field('contractType','合同类型','text','软件服务合同',false)] },
  { id: 2, slug: 'meeting-minutes', name: '会议纪要助手', category: '协同办公', icon: 'meeting', accent: 'teal', featured: true, enabled: true,
    summary: '从会议记录中提取结论、责任人、截止日期和待确认事项。', fields: [field('transcript','会议内容','textarea','张敏：本周完成华东区上线……'), field('meetingTitle','会议主题','text','项目周会',false)] },
  { id: 3, slug: 'expense-audit', name: '费用报销审核', category: '财务管理', icon: 'expense', accent: 'amber', featured: true, enabled: true,
    summary: '结合金额、票据、预算和差旅规则生成审核意见。', fields: [field('amount','报销金额','number','3680'), field('budget','可用预算','number','5000'), field('invoiceStatus','票据状态','text','已验真'), field('description','费用说明','text','客户现场差旅',false)] },
  { id: 4, slug: 'invoice-insight', name: '发票识别与异常检测', category: '财税票据', icon: 'invoice', accent: 'blue', featured: true, enabled: true,
    summary: '结构化发票字段并检查重复、金额和供应商异常。', fields: [field('invoiceCode','发票号码','text','3100241560'), field('amount','含税金额','number','12800'), field('historicalAverage','历史均值','number','6200'), field('vendor','供应商','text','华东数字服务有限公司')] },
  { id: 5, slug: 'resume-match', name: '简历解析与人岗匹配', category: '人力资源', icon: 'resume', accent: 'violet', featured: true, enabled: true,
    summary: '提取候选人技能与经历，输出匹配证据和面试问题。', fields: [field('resumeText','简历摘要','textarea','5年Java开发经验，熟悉Spring Boot、MySQL……'), field('jobSkills','岗位技能','text','Java,Spring Boot,MySQL,Docker')] },
  { id: 6, slug: 'sales-copilot', name: '销售跟进助手', category: '客户经营', icon: 'sales', accent: 'green', featured: true, enabled: true,
    summary: '识别客户意向、异议和下一步动作，生成跟进建议。', fields: [field('conversation','客户沟通记录','textarea','客户关注实施周期和数据迁移风险……'), field('dealAmount','预计商机金额','number','180000',false), field('stage','当前阶段','text','方案评估',false)] },
  { id: 7, slug: 'service-quality', name: '客诉分类与服务质检', category: '客户服务', icon: 'service', accent: 'red', featured: false, enabled: true,
    summary: '识别投诉主题、情绪、紧急程度并检查服务话术。', fields: [field('dialogue','服务对话','textarea','客户：系统连续两天无法导出……客服：稍后处理。'), field('affectedUsers','影响用户数','number','35',false)] },
  { id: 8, slug: 'document-qa', name: '企业文档问答', category: '知识管理', icon: 'document', accent: 'cyan', featured: false, enabled: true,
    summary: '基于文档片段回答问题，并返回引用依据与可信度。', fields: [field('document','文档内容','textarea','项目验收后提供12个月免费运维支持……'), field('question','问题','text','免费运维期是多久？')] },
  { id: 9, slug: 'bi-insight', name: '智能 BI 分析', category: '经营分析', icon: 'chart', accent: 'orange', featured: false, enabled: true,
    summary: '根据经营数据识别趋势、异常和管理层关注指标。', fields: [field('dataset','经营数据','textarea','1月收入120万,2月138万,3月131万,4月165万'), field('goal','分析目标','text','分析收入趋势并给出经营建议')] },
  { id: 10, slug: 'equipment-diagnosis', name: '设备故障诊断', category: '生产运维', icon: 'equipment', accent: 'slate', featured: false, enabled: true,
    summary: '结合告警、温度、振动和运行时长生成排查路径。', fields: [field('equipment','设备名称','text','CNC-07 数控机床'), field('temperature','当前温度(℃)','number','86'), field('vibration','振动值(mm/s)','number','9.2'), field('alarms','告警信息','textarea','主轴温升过快；加工精度漂移',false)] }
]

export const demoResults = {
  'contract-review': { title:'合同审查完成', level:'HIGH', score:82, summary:'发现付款周期、单方解除和自动续约等关键风险，建议在签署前完成条款澄清。', findings:['付款周期偏长，可能形成现金流占用','单方解除条款导致权责不对等','自动续约缺少提前通知机制','数据安全责任边界需要补充'], actions:['将付款周期调整至验收后30日内','增加双方对等的解除与整改期限','补充数据处理、保密和泄露责任'] },
  'meeting-minutes': { title:'会议纪要已生成', level:'NORMAL', score:91, summary:'会议形成3项结论和4项行动安排，关键责任与时间节点已结构化。', findings:['华东区项目按原计划推进','数据迁移需在上线前完成复核','周五进行上线评审'], actions:['张敏周三前提交迁移清单','王涛周四完成接口压测','项目经理周五组织上线评审'] },
  'expense-audit': { title:'报销审核：建议通过', level:'LOW', score:16, summary:'预算、金额和票据状态均符合当前审核规则。', findings:['报销金额未超过可用预算','票据已完成验真','费用说明与差旅场景一致'], actions:['进入下一审批节点','保留原始票据与出差申请'] },
  'invoice-insight': { title:'发票异常检查完成', level:'MEDIUM', score:57, summary:'发票金额为供应商历史均值的2.06倍，建议结合订单与验收记录复核。', findings:['金额显著高于历史均值','发票号码和供应商字段完整','未检测到格式异常'], actions:['执行合同、订单、验收三单匹配','检查重复报销和重复入账'] },
  'resume-match': { title:'候选人匹配度 78%', level:'MATCH', score:78, summary:'候选人的后端开发经验与岗位核心要求基本一致，Docker经验需要进一步验证。', findings:['命中 Java、Spring Boot、MySQL','具备5年相关项目经验','Docker实战证据不足'], actions:['围绕容器化部署设计情景题','核验最近项目中的个人职责','由招聘人员完成最终判断'] },
  'sales-copilot': { title:'销售意向评分 81', level:'HOT', score:81, summary:'客户已进入方案与商务评估阶段，主要异议集中在交付周期和迁移风险。', findings:['明确关注上线时间','开始讨论预算与报价','数据迁移是核心顾虑'], actions:['提供分阶段迁移与回滚方案','确认决策人与预算审批链','两个工作日内安排方案沟通'] },
  'service-quality': { title:'客诉分类：产品故障', level:'HIGH', score:76, summary:'问题持续发生且影响范围较大，客服回复缺少明确处理时限。', findings:['故障连续发生两天','预计影响35名用户','回复缺少同理心和时限承诺'], actions:['升级P1事件并建立处理群','30分钟内向客户同步进展','结案后完成服务回访'] },
  'document-qa': { title:'文档问答已完成', level:'GROUNDED', score:88, summary:'根据文档约定，项目验收后提供12个月免费运维支持。', findings:['引用：项目验收后提供12个月免费运维支持','答案直接来自当前文档片段'], actions:['正式决策前核对完整合同原文','保留文档版本和引用页码'] },
  'bi-insight': { title:'经营数据分析完成', level:'INSIGHT', score:85, summary:'样本期收入整体增长37.5%，3月出现短暂回落，4月达到阶段峰值。', findings:['1月至4月收入增长约37.5%','3月环比出现5.1%回落','4月收入达到165万元'], actions:['拆分4月增长的客户与产品贡献','结合毛利和回款验证增长质量','复盘3月回落的业务原因'] },
  'equipment-diagnosis': { title:'设备诊断：建议停机检查', level:'HIGH', score:88, summary:'温度与振动同时超过观察阈值，告警与主轴机械状态高度相关。', findings:['温度86℃，超过观察阈值','振动9.2mm/s，处于高风险区间','精度漂移可能与主轴状态相关'], actions:['安全停机检查润滑、轴承和主轴','对比同工况历史趋势','由持证维护人员确认处理方案'] }
}

export function defaultInputs(item) {
  return Object.fromEntries(item.fields.map(input => [input.key, input.placeholder]))
}
