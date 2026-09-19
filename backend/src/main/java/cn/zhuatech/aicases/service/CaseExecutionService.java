/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aicases.service;

import cn.zhuatech.aicases.ai.OpenAiCompatibleGateway;
import cn.zhuatech.aicases.common.BusinessException;
import cn.zhuatech.aicases.config.CaseCatalog;
import cn.zhuatech.aicases.dto.RunCaseRequest;
import cn.zhuatech.aicases.dto.RunCaseResult;
import cn.zhuatech.aicases.model.AiCaseExecution;
import cn.zhuatech.aicases.repository.AiCaseDefinitionRepository;
import cn.zhuatech.aicases.repository.AiCaseExecutionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class CaseExecutionService {
    private final AiCaseDefinitionRepository caseRepository;
    private final AiCaseExecutionRepository executionRepository;
    private final OpenAiCompatibleGateway gateway;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public CaseExecutionService(AiCaseDefinitionRepository caseRepository,
                                AiCaseExecutionRepository executionRepository,
                                OpenAiCompatibleGateway gateway) {
        this.caseRepository = caseRepository;
        this.executionRepository = executionRepository;
        this.gateway = gateway;
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Transactional
    public RunCaseResult run(String slug, RunCaseRequest request) {
        var definition = caseRepository.findBySlug(slug)
            .filter(item -> item.isEnabled())
            .orElseThrow(() -> new BusinessException("案例不存在或已停用"));
        validateInputs(slug, request.inputs());
        long started = System.nanoTime();
        RuleOutcome local = switch (slug) {
            case "contract-review" -> contract(request.inputs());
            case "meeting-minutes" -> meeting(request.inputs());
            case "expense-audit" -> expense(request.inputs());
            case "invoice-insight" -> invoice(request.inputs());
            case "resume-match" -> resume(request.inputs());
            case "sales-copilot" -> sales(request.inputs());
            case "service-quality" -> serviceQuality(request.inputs());
            case "document-qa" -> documentQa(request.inputs());
            case "bi-insight" -> biInsight(request.inputs());
            case "equipment-diagnosis" -> equipment(request.inputs());
            default -> throw new BusinessException("案例运行器尚未配置");
        };

        String context = "案例：%s\n输入：%s\n本地规则结果：%s\n发现：%s\n建议：%s"
            .formatted(definition.getName(), compact(request.inputs()), local.summary(), local.findings(), local.actions());
        Optional<String> enhanced = gateway.complete(
            "你是知华科技企业AI案例助手。请基于既有规则结论给出简洁、可核验、不过度承诺的中文业务说明，不得改变数值结论。",
            context);
        long durationMs = Math.max(1, (System.nanoTime() - started) / 1_000_000);
        var status = gateway.status();
        String mode = enhanced.isPresent() ? "MODEL_ENHANCED" : "LOCAL_RULES";
        String summary = enhanced.orElse(local.summary());
        executionRepository.save(new AiCaseExecution(slug, compact(request.inputs()), summary, mode, durationMs, true));
        return new RunCaseResult(slug, local.title(), local.level(), local.score(), summary,
            local.findings(), local.actions(), local.data(), mode, status.provider(), status.model(), durationMs);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private void validateInputs(String slug, Map<String, Object> inputs) {
        CaseCatalog.CaseSpec spec = CaseCatalog.get(slug);
        if (spec == null) throw new BusinessException("案例配置不存在");
        spec.fields().stream().filter(CaseCatalog.FieldSpec::required).forEach(field -> {
            Object value = inputs.get(field.key());
            if (value == null || String.valueOf(value).isBlank()) throw new BusinessException(field.label() + "不能为空");
        });
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private RuleOutcome contract(Map<String, Object> input) {
        String text = value(input, "contractText");
        List<String> findings = new ArrayList<>();
        int score = 18;
        if (contains(text, "90日", "90天", "六个月后付款")) { score += 22; findings.add("付款周期偏长，需评估现金流占用"); }
        if (contains(text, "自动续约", "默认续期")) { score += 18; findings.add("存在自动续约安排，应增加提前通知机制"); }
        if (contains(text, "单方解除", "最终解释权")) { score += 25; findings.add("单方权利条款可能导致权责失衡"); }
        if (!contains(text, "数据安全", "保密", "个人信息")) { score += 15; findings.add("未发现清晰的数据安全或保密约定"); }
        if (!contains(text, "违约责任", "违约金")) { score += 12; findings.add("违约责任边界不够明确"); }
        score = Math.min(100, score);
        if (findings.isEmpty()) findings.add("核心条款未触发高风险规则，仍建议法务人工复核");
        return outcome("合同审查完成", risk(score), score, "共识别 %d 项需要关注的合同事项。".formatted(findings.size()), findings,
            List.of("确认交付与验收口径", "补充数据安全和保密责任", "由法务结合交易背景完成最终审核"),
            Map.of("riskItems", findings.size(), "reviewScope", "付款/履约/续约/数据/违约"));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private RuleOutcome meeting(Map<String, Object> input) {
        String transcript = value(input, "transcript");
        List<String> sentences = Arrays.stream(transcript.split("[。；;！!\\n]+"))
            .map(String::trim).filter(item -> item.length() > 4).limit(12).toList();
        List<String> actions = sentences.stream().filter(item -> contains(item, "完成", "负责", "截止", "上线", "跟进"))
            .limit(4).toList();
        List<String> findings = sentences.stream().filter(item -> contains(item, "决定", "确认", "同意", "结论"))
            .limit(3).toList();
        if (findings.isEmpty()) findings = List.of("会议形成了推进共识，但决策表达需要进一步结构化");
        if (actions.isEmpty()) actions = List.of("补充责任人和明确截止日期", "会后向参会人确认纪要");
        int score = Math.min(96, 62 + actions.size() * 7 + findings.size() * 5);
        return outcome("会议纪要已生成", "NORMAL", score, "已整理 %d 条决策信息和 %d 项行动建议。".formatted(findings.size(), actions.size()),
            findings, actions, Map.of("sentenceCount", sentences.size(), "decisionCount", findings.size(), "actionCount", actions.size()));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private RuleOutcome expense(Map<String, Object> input) {
        BigDecimal amount = number(input, "amount");
        BigDecimal budget = number(input, "budget");
        String invoice = value(input, "invoiceStatus");
        List<String> findings = new ArrayList<>();
        int score = 8;
        if (amount.compareTo(budget) > 0) { score += 52; findings.add("报销金额超出当前可用预算"); }
        if (!contains(invoice, "验真", "有效")) { score += 35; findings.add("票据尚未完成有效性验证"); }
        if (amount.compareTo(BigDecimal.valueOf(5000)) > 0) { score += 12; findings.add("金额超过常规快速审批阈值"); }
        if (findings.isEmpty()) findings.add("金额、预算和票据状态符合当前演示规则");
        score = Math.min(100, score);
        String decision = score >= 60 ? "退回补充" : score >= 30 ? "人工复核" : "建议通过";
        return outcome("报销审核：" + decision, risk(score), score, "系统完成预算、金额和票据三项检查。", findings,
            List.of(score >= 30 ? "补充预算或票据证据" : "进入下一审批节点", "保留原始票据和业务说明"),
            Map.of("decision", decision, "budgetBalance", budget.subtract(amount)));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private RuleOutcome invoice(Map<String, Object> input) {
        BigDecimal amount = number(input, "amount");
        BigDecimal average = number(input, "historicalAverage");
        BigDecimal ratio = average.signum() == 0 ? BigDecimal.ONE : amount.divide(average, 2, RoundingMode.HALF_UP);
        List<String> findings = new ArrayList<>();
        int score = 12;
        if (ratio.compareTo(BigDecimal.valueOf(2)) > 0) { score += 45; findings.add("金额超过该供应商历史均值两倍"); }
        if (value(input, "invoiceCode").length() < 8) { score += 30; findings.add("发票号码长度异常"); }
        if (value(input, "vendor").length() < 4) { score += 15; findings.add("供应商名称信息不完整"); }
        if (findings.isEmpty()) findings.add("结构化字段完整，未发现明显金额异常");
        score = Math.min(100, score);
        return outcome("发票结构化与异常检查完成", risk(score), score, "发票金额为历史均值的 %s 倍。".formatted(ratio), findings,
            List.of(score >= 50 ? "核对合同、订单与验收记录" : "进入三单匹配流程", "检查是否存在重复报销或重复入账"),
            Map.of("amountRatio", ratio, "invoiceCode", value(input, "invoiceCode"), "vendor", value(input, "vendor")));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private RuleOutcome resume(Map<String, Object> input) {
        String resume = value(input, "resumeText").toLowerCase(Locale.ROOT);
        List<String> skills = Arrays.stream(value(input, "jobSkills").split("[,，/ ]+"))
            .filter(item -> !item.isBlank()).distinct().toList();
        List<String> matched = skills.stream().filter(skill -> resume.contains(skill.toLowerCase(Locale.ROOT))).toList();
        List<String> missing = skills.stream().filter(skill -> !matched.contains(skill)).toList();
        int score = skills.isEmpty() ? 50 : (int) Math.round(matched.size() * 100.0 / skills.size());
        List<String> findings = new ArrayList<>();
        findings.add("已匹配技能：" + (matched.isEmpty() ? "暂无明确命中" : String.join("、", matched)));
        if (!missing.isEmpty()) findings.add("待验证技能：" + String.join("、", missing));
        return outcome("候选人匹配度 " + score + "%", score >= 75 ? "MATCH" : score >= 50 ? "REVIEW" : "GAP", score,
            "匹配结果只基于岗位技能和履历证据，不使用年龄、性别等敏感属性。", findings,
            List.of("围绕待验证技能设计情景题", "核验项目职责与成果证据", "由招聘人员完成最终判断"),
            Map.of("matchedSkills", matched, "missingSkills", missing, "matchRate", score));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private RuleOutcome sales(Map<String, Object> input) {
        String conversation = value(input, "conversation");
        List<String> findings = new ArrayList<>();
        int intent = 45;
        if (contains(conversation, "预算", "报价", "合同")) { intent += 18; findings.add("客户已进入商务条件讨论"); }
        if (contains(conversation, "周期", "上线", "交付")) { intent += 15; findings.add("客户关注实施周期与交付确定性"); }
        if (contains(conversation, "风险", "担心", "迁移")) { findings.add("主要异议集中在迁移或项目风险"); }
        if (contains(conversation, "决策", "老板", "领导")) { intent += 10; findings.add("对话涉及潜在决策链信息"); }
        intent = Math.min(100, intent);
        if (findings.isEmpty()) findings.add("客户需求仍处于初步探索阶段");
        return outcome("销售意向评分 " + intent, intent >= 75 ? "HOT" : intent >= 55 ? "WARM" : "NURTURE", intent,
            "已从沟通记录中提取意向、异议和推进信号。", findings,
            List.of("提供实施里程碑与迁移方案", "确认决策人、预算和目标上线时间", "在两个工作日内安排下一次方案沟通"),
            Map.of("stage", value(input, "stage"), "dealAmount", value(input, "dealAmount"), "signals", findings.size()));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private RuleOutcome serviceQuality(Map<String, Object> input) {
        String dialogue = value(input, "dialogue");
        int affected = number(input, "affectedUsers").intValue();
        int score = 20;
        List<String> findings = new ArrayList<>();
        String category = contains(dialogue, "无法", "报错", "故障") ? "产品故障" : contains(dialogue, "退款", "收费") ? "费用争议" : "服务咨询";
        if (contains(dialogue, "连续", "多次", "一直")) { score += 25; findings.add("问题具有持续或重复发生特征"); }
        if (affected >= 20) { score += 30; findings.add("影响用户范围较大"); }
        if (!contains(dialogue, "抱歉", "理解", "马上", "预计")) { score += 20; findings.add("客服回复缺少同理心或明确响应时限"); }
        score = Math.min(100, score);
        if (findings.isEmpty()) findings.add("对话响应基本符合当前质检规则");
        return outcome("客诉分类：" + category, risk(score), score, "已完成主题、影响范围和服务话术质检。", findings,
            List.of(score >= 60 ? "升级至值班负责人并建立事件群" : "按标准服务流程跟进", "向客户提供明确处理时限", "结案前完成回访"),
            Map.of("category", category, "affectedUsers", affected, "priority", score >= 65 ? "P1" : score >= 40 ? "P2" : "P3"));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private RuleOutcome documentQa(Map<String, Object> input) {
        String document = value(input, "document");
        String question = value(input, "question");
        String[] sentences = document.split("[。；;！!\\n]+");
        String answer = Arrays.stream(sentences)
            .filter(sentence -> sentence.contains("运维") || sentence.contains("支持") || sentence.contains("验收"))
            .findFirst().orElse(sentences.length == 0 ? "文档中未找到直接依据" : sentences[0]).trim();
        int confidence = answer.contains("未找到") ? 35 : 88;
        return outcome("文档问答已完成", confidence >= 70 ? "GROUNDED" : "REVIEW", confidence,
            "回答：" + answer, List.of("问题：" + question, "引用片段：" + answer),
            List.of("在正式决策前核对原文上下文", "补充文档版本、页码和权限信息"),
            Map.of("confidence", confidence, "citation", answer));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private RuleOutcome biInsight(Map<String, Object> input) {
        String dataset = value(input, "dataset");
        Matcher matcher = Pattern.compile("(?:收入)?(\\d+(?:\\.\\d+)?)").matcher(dataset);
        List<BigDecimal> values = new ArrayList<>();
        while (matcher.find()) values.add(new BigDecimal(matcher.group(1)));
        List<String> findings = new ArrayList<>();
        int score = 70;
        if (values.size() >= 2) {
            BigDecimal first = values.getFirst();
            BigDecimal last = values.getLast();
            BigDecimal change = first.signum() == 0 ? BigDecimal.ZERO : last.subtract(first).divide(first, 3, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
            findings.add("首尾指标变化约 " + change.stripTrailingZeros().toPlainString() + "%");
            BigDecimal max = values.stream().max(Comparator.naturalOrder()).orElse(BigDecimal.ZERO);
            findings.add("样本期峰值为 " + max.stripTrailingZeros().toPlainString());
            score = 85;
        } else findings.add("可识别的数据点不足，建议上传结构化数据");
        return outcome("经营数据分析完成", "INSIGHT", score, "已识别 %d 个可分析数据点。".formatted(values.size()), findings,
            List.of("拆分增长的产品、区域和客户贡献", "结合毛利与回款验证增长质量", "对异常月份补充经营事件说明"),
            Map.of("dataPoints", values, "goal", value(input, "goal")));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private RuleOutcome equipment(Map<String, Object> input) {
        BigDecimal temperature = number(input, "temperature");
        BigDecimal vibration = number(input, "vibration");
        int score = 12;
        List<String> findings = new ArrayList<>();
        if (temperature.compareTo(BigDecimal.valueOf(80)) > 0) { score += 38; findings.add("设备温度超过 80℃ 观察阈值"); }
        if (vibration.compareTo(BigDecimal.valueOf(7.1)) > 0) { score += 35; findings.add("振动值超过常用机械状态观察阈值"); }
        if (contains(value(input, "alarms"), "精度", "温升", "过载")) { score += 20; findings.add("告警信息与机械或主轴状态相关"); }
        score = Math.min(100, score);
        if (findings.isEmpty()) findings.add("当前指标未触发演示规则中的异常阈值");
        return outcome("设备诊断：" + (score >= 65 ? "建议停机检查" : score >= 35 ? "计划检修" : "继续监测"), risk(score), score,
            "诊断基于温度、振动和告警文本，不替代设备厂商维护规范。", findings,
            List.of(score >= 65 ? "安全停机并检查润滑、轴承和主轴" : "增加点检频次", "对比同工况历史趋势", "由持证维护人员确认处理方案"),
            Map.of("equipment", value(input, "equipment"), "temperature", temperature, "vibration", vibration));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private static RuleOutcome outcome(String title, String level, int score, String summary,
                                       List<String> findings, List<String> actions, Map<String, Object> data) {
        return new RuleOutcome(title, level, score, summary, List.copyOf(findings), List.copyOf(actions), Map.copyOf(data));
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private static String value(Map<String, Object> input, String key) { return String.valueOf(input.getOrDefault(key, "")).trim(); }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private static BigDecimal number(Map<String, Object> input, String key) {
        try { return new BigDecimal(value(input, key).isBlank() ? "0" : value(input, key)); }
        catch (NumberFormatException exception) { throw new BusinessException(key + " 必须是有效数字"); }
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private static boolean contains(String source, String... keywords) { return Arrays.stream(keywords).anyMatch(source::contains); }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private static String compact(Map<String, Object> input) {
        String value = input.toString();
        return value.length() > 900 ? value.substring(0, 900) : value;
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private static String risk(int score) { return score >= 70 ? "HIGH" : score >= 40 ? "MEDIUM" : "LOW"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private record RuleOutcome(String title, String level, int score, String summary,
                               List<String> findings, List<String> actions, Map<String, Object> data) {}
}
