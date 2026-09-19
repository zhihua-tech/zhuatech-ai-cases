/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aicases.service;

import cn.zhuatech.aicases.ai.OpenAiCompatibleGateway;
import cn.zhuatech.aicases.common.BusinessException;
import cn.zhuatech.aicases.dto.AiCaseDto;
import cn.zhuatech.aicases.model.AiCaseExecution;
import cn.zhuatech.aicases.repository.AiCaseDefinitionRepository;
import cn.zhuatech.aicases.repository.AiCaseExecutionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class AdminDashboardService {
    private final AiCaseDefinitionRepository caseRepository;
    private final AiCaseExecutionRepository executionRepository;
    private final OpenAiCompatibleGateway gateway;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public AdminDashboardService(AiCaseDefinitionRepository caseRepository,
                                 AiCaseExecutionRepository executionRepository,
                                 OpenAiCompatibleGateway gateway) {
        this.caseRepository = caseRepository;
        this.executionRepository = executionRepository;
        this.gateway = gateway;
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Overview overview() {
        long enabled = caseRepository.findAll().stream().filter(item -> item.isEnabled()).count();
        return new Overview(caseRepository.count(), enabled, executionRepository.count(),
            executionRepository.countByCreatedAtAfter(Instant.now().minus(1, ChronoUnit.DAYS)),
            executionRepository.countBySuccessFalse(), gateway.status());
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public List<ExecutionView> executions(int limit) {
        return executionRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, Math.min(Math.max(limit, 1), 100)))
            .stream().map(ExecutionView::from).toList();
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Transactional
    public AiCaseDto updateCase(Long id, Map<String, Boolean> values) {
        var entity = caseRepository.findById(id).orElseThrow(() -> new BusinessException("案例不存在"));
        if (values.containsKey("enabled")) entity.setEnabled(Boolean.TRUE.equals(values.get("enabled")));
        if (values.containsKey("featured")) entity.setFeatured(Boolean.TRUE.equals(values.get("featured")));
        return AiCaseDto.from(caseRepository.save(entity));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Overview(long caseCount, long enabledCount, long executionCount, long last24Hours,
                           long failedCount, OpenAiCompatibleGateway.ProviderStatus provider) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record ExecutionView(Long id, String caseSlug, String requestSummary, String resultSummary,
                                String executionMode, long durationMs, boolean success, Instant createdAt) {
        /**
         * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
         */
        static ExecutionView from(AiCaseExecution entity) {
            return new ExecutionView(entity.getId(), entity.getCaseSlug(), entity.getRequestSummary(),
                entity.getResultSummary(), entity.getExecutionMode(), entity.getDurationMs(), entity.isSuccess(), entity.getCreatedAt());
        }
    }
}
