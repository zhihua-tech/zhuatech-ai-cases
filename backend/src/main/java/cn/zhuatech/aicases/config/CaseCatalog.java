/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aicases.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class CaseCatalog {
    private CaseCatalog() {}

    private static final Map<String, CaseSpec> CASES = new LinkedHashMap<>();

    static {
        register(new CaseSpec("contract-review", "合同风险审查", "法务合规", "识别权责失衡、付款、违约、续约和数据安全风险。", "contract", "navy", true,
            List.of(textarea("contractText", "合同条款", "甲方应在验收后90日内付款……", true), text("contractType", "合同类型", "软件服务合同", false))));
        register(new CaseSpec("meeting-minutes", "会议纪要助手", "协同办公", "从会议记录中提取结论、责任人、截止日期和待确认事项。", "meeting", "teal", true,
            List.of(textarea("transcript", "会议内容", "张敏：本周完成华东区上线……", true), text("meetingTitle", "会议主题", "项目周会", false))));
        register(new CaseSpec("expense-audit", "费用报销审核", "财务管理", "结合金额、票据、预算和差旅规则生成审核意见。", "expense", "amber", true,
            List.of(number("amount", "报销金额", "3680", true), number("budget", "可用预算", "5000", true), text("invoiceStatus", "票据状态", "已验真", true), text("description", "费用说明", "客户现场差旅", false))));
        register(new CaseSpec("invoice-insight", "发票识别与异常检测", "财税票据", "结构化发票字段并检查重复、金额和供应商异常。", "invoice", "blue", true,
            List.of(text("invoiceCode", "发票号码", "3100241560", true), number("amount", "含税金额", "12800", true), number("historicalAverage", "历史均值", "6200", true), text("vendor", "供应商", "华东数字服务有限公司", true))));
        register(new CaseSpec("resume-match", "简历解析与人岗匹配", "人力资源", "提取候选人技能与经历，输出匹配证据和面试问题。", "resume", "violet", true,
            List.of(textarea("resumeText", "简历摘要", "5年Java开发经验，熟悉Spring Boot、MySQL……", true), text("jobSkills", "岗位技能", "Java,Spring Boot,MySQL,Docker", true))));
        register(new CaseSpec("sales-copilot", "销售跟进助手", "客户经营", "识别客户意向、异议和下一步动作，生成跟进建议。", "sales", "green", true,
            List.of(textarea("conversation", "客户沟通记录", "客户关注实施周期和数据迁移风险……", true), number("dealAmount", "预计商机金额", "180000", false), text("stage", "当前阶段", "方案评估", false))));
        register(new CaseSpec("service-quality", "客诉分类与服务质检", "客户服务", "识别投诉主题、情绪、紧急程度并检查服务话术。", "service", "red", false,
            List.of(textarea("dialogue", "服务对话", "客户：系统连续两天无法导出……客服：稍后处理。", true), number("affectedUsers", "影响用户数", "35", false))));
        register(new CaseSpec("document-qa", "企业文档问答", "知识管理", "基于文档片段回答问题，并返回引用依据与可信度。", "document", "cyan", false,
            List.of(textarea("document", "文档内容", "项目验收后提供12个月免费运维支持……", true), text("question", "问题", "免费运维期是多久？", true))));
        register(new CaseSpec("bi-insight", "智能 BI 分析", "经营分析", "根据经营数据识别趋势、异常和管理层关注指标。", "chart", "orange", false,
            List.of(textarea("dataset", "经营数据", "1月收入120万,2月138万,3月131万,4月165万", true), text("goal", "分析目标", "分析收入趋势并给出经营建议", true))));
        register(new CaseSpec("equipment-diagnosis", "设备故障诊断", "生产运维", "结合告警、温度、振动和运行时长生成排查路径。", "equipment", "slate", false,
            List.of(text("equipment", "设备名称", "CNC-07 数控机床", true), number("temperature", "当前温度(℃)", "86", true), number("vibration", "振动值(mm/s)", "9.2", true), textarea("alarms", "告警信息", "主轴温升过快；加工精度漂移", false))));
    }

    private static FieldSpec text(String key, String label, String placeholder, boolean required) {
        return new FieldSpec(key, label, "text", placeholder, required);
    }
    private static FieldSpec textarea(String key, String label, String placeholder, boolean required) {
        return new FieldSpec(key, label, "textarea", placeholder, required);
    }
    private static FieldSpec number(String key, String label, String placeholder, boolean required) {
        return new FieldSpec(key, label, "number", placeholder, required);
    }
    private static void register(CaseSpec spec) { CASES.put(spec.slug(), spec); }
    public static List<CaseSpec> all() { return List.copyOf(CASES.values()); }
    public static CaseSpec get(String slug) { return CASES.get(slug); }

    public record CaseSpec(String slug, String name, String category, String summary, String icon,
                           String accent, boolean featured, List<FieldSpec> fields) {}
    public record FieldSpec(String key, String label, String type, String placeholder, boolean required) {}
}
