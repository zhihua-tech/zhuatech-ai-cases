/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aicases;

import cn.zhuatech.aicases.dto.RunCaseRequest;
import cn.zhuatech.aicases.service.CaseExecutionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@SpringBootTest
@Transactional
class CaseExecutionServiceTests {
    @Autowired CaseExecutionService service;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void contractReviewFindsRiskEvidence() {
        var result = service.run("contract-review", new RunCaseRequest(Map.of(
            "contractText", "付款周期90日，甲方拥有单方解除权，合同到期自动续约。",
            "contractType", "软件服务合同")));
        assertThat(result.score()).isGreaterThanOrEqualTo(60);
        assertThat(result.findings()).hasSizeGreaterThanOrEqualTo(3);
        assertThat(result.executionMode()).isEqualTo("LOCAL_RULES");
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void allTenCasesHaveRunnableLocalRules() {
        Map<String, Map<String, Object>> samples = Map.of(
            "meeting-minutes", Map.of("transcript", "会议决定周五上线，张敏负责完成发布并跟进验收。"),
            "expense-audit", Map.of("amount", 1200, "budget", 5000, "invoiceStatus", "已验真"),
            "invoice-insight", Map.of("invoiceCode", "3100241560", "amount", 12800, "historicalAverage", 6200, "vendor", "华东服务公司"),
            "resume-match", Map.of("resumeText", "熟悉Java Spring Boot MySQL", "jobSkills", "Java,Spring Boot,MySQL,Docker"),
            "sales-copilot", Map.of("conversation", "客户关注上线周期和数据迁移风险，准备讨论报价"),
            "service-quality", Map.of("dialogue", "系统连续无法导出，稍后处理", "affectedUsers", 30),
            "document-qa", Map.of("document", "项目验收后提供12个月免费运维支持。", "question", "运维期多久"),
            "bi-insight", Map.of("dataset", "收入120,138,131,165", "goal", "趋势分析"),
            "equipment-diagnosis", Map.of("equipment", "CNC-07", "temperature", 86, "vibration", 9.2, "alarms", "主轴温升")
        );
        samples.forEach((slug, input) -> assertThat(service.run(slug, new RunCaseRequest(input)).summary()).isNotBlank());
    }
}
