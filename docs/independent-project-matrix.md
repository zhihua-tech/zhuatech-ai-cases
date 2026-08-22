# 知华科技 AI 独立案例项目矩阵

每个案例对应一个可独立运行、独立测试、独立发布的代码仓库。`zhuatech-ai-cases` 只承担统一导航与案例索引，不代替下列独立项目。

当前进度：33 个独立项目已全部覆盖。其中首批多模态 6 个、智能语音笔记与本轮 12 个业务 AI 项目为新建工程，14 个既有对应仓库已重新通过 Java 21 自动化测试。完成不代表已推送远程仓库。

| 序号 | 案例方向 | 独立项目 | 实施方式 |
| --- | --- | --- | --- |
| 01 | 音色克隆 | `zhuatech-voiceclone` | ✅ 已完成：授权校验、音频质检与服务适配接口 |
| 02 | 文生视频 | `zhuatech-videogen` | ✅ 已完成：脚本、分镜与渲染任务编排 |
| 03 | 数字人播报 | `zhuatech-digitalhuman` | ✅ 已完成：口播分段、形象/音色授权与合规编排 |
| 04 | 以图搜图 | `zhuatech-imagesearch` | ✅ 已完成：视觉检索流程、授权过滤与可解释排序 |
| 05 | 文生图 | `zhuatech-imagegen` | ✅ 已完成：提示词变体、品牌约束与生成适配接口 |
| 06 | 图像人员检测 | `zhuatech-peopledetect` | ✅ 已完成：匿名区域计数、容量与告警规则 |
| 07 | 合同风险识别 | `zhuatech-contractai` | ✅ 既有独立项目已验证 |
| 08 | 智能语音笔记 | `zhuatech-voicenote` | ✅ 已完成：录音知情确认、逐字稿、智能摘要与行动事项 |
| 09 | 销售单智能助手 | `zhuatech-salesorderai` | ✅ 已完成：价格、信用、毛利与履约风险辅助审查 |
| 10 | 费用报销审核 | `zhuatech-expenseai` | ✅ 既有独立项目已验证 |
| 11 | 智能会议纪要 | `zhuatech-meetingai` | ✅ 既有独立项目已验证 |
| 12 | 智能语音对话 | `zhuatech-voiceassistant` | ✅ 已完成：意图识别、知识问答、服务边界与转人工编排 |
| 13 | 投诉智能分类 | `zhuatech-complaintai` | ✅ 既有独立项目已验证 |
| 14 | 文件智能分类 | `zhuatech-fileclassifier` | ✅ 已完成：文件归类、敏感检查、权限继承与归档建议 |
| 15 | 简历信息提取 | `zhuatech-recruitai` | ✅ 既有独立项目已验证 |
| 16 | 客户跟进管理 | `zhuatech-customerfollowup` | ✅ 已完成：客户健康度、承诺事项与下一步节奏管理 |
| 17 | 智能竞品分析 | `zhuatech-competitorai` | ✅ 已完成：竞品变化、证据资料与验证任务管理 |
| 18 | 智能销售话术 | `zhuatech-salescoach` | ✅ 已完成：场景话术、异议演练与合规检查 |
| 19 | 客服对话质检 | `zhuatech-serviceagent` | ✅ 既有独立项目已验证 |
| 20 | 血常规报告辅助解读 | `zhuatech-labreportai` | ✅ 已完成：健康教育、指标释义与就医提示，不提供诊断 |
| 21 | 智能数据库开发 | `zhuatech-sqlagent` | ✅ 既有独立项目已验证 |
| 22 | 智能邮件助手 | `zhuatech-mailagent` | ✅ 已完成：邮件分类、回复草稿、行动项与发送前检查 |
| 23 | 智能知识图谱 | `zhuatech-knowledgegraph` | ✅ 已完成：实体抽取、关系审核、来源证据与图谱治理 |
| 24 | 智能图表与 BI | `zhuatech-insight` | ✅ 既有独立项目已验证 |
| 25 | 文本结构化 | `zhuatech-documentai` | ✅ 既有独立项目已验证 |
| 26 | 智能发票识别 | `zhuatech-invoiceagent` | ✅ 既有独立项目已验证 |
| 27 | 智能表单识别 | `zhuatech-idp` | ✅ 既有独立项目已验证 |
| 28 | ChatPDF | `zhuatech-rag` | ✅ 既有独立项目已验证 |
| 29 | Excel 智能筛选 | `zhuatech-excelinsight` | ✅ 已完成：自然语言筛选、数据质量检查与规则快照 |
| 30 | 智能销售助手 | `zhuatech-salesagent` | ✅ 既有独立项目已验证 |
| 31 | 设备智能检测 | `zhuatech-maintenanceai` | ✅ 既有独立项目已验证 |
| 32 | 智能出题助手 | `zhuatech-quizagent` | ✅ 已完成：材料溯源、题目生成、人工审核与组卷管理 |
| 33 | 智能会议室预约 | `zhuatech-roomagent` | ✅ 已完成：容量设备匹配、日程冲突与预约建议 |

所有新项目默认使用本地确定性演示逻辑，并预留 DeepSeek 或相应多模态服务适配接口；使用者自行配置模型凭据。
