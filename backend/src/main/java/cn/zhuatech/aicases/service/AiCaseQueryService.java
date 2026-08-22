/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aicases.service;

import cn.zhuatech.aicases.common.BusinessException;
import cn.zhuatech.aicases.dto.AiCaseDto;
import cn.zhuatech.aicases.repository.AiCaseDefinitionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AiCaseQueryService {
    private final AiCaseDefinitionRepository repository;
    public AiCaseQueryService(AiCaseDefinitionRepository repository) { this.repository = repository; }

    public List<AiCaseDto> list(boolean includeDisabled) {
        return repository.findAllByOrderBySortOrderAsc().stream()
            .filter(item -> includeDisabled || item.isEnabled())
            .map(AiCaseDto::from).toList();
    }

    public AiCaseDto get(String slug) {
        return repository.findBySlug(slug).filter(item -> item.isEnabled())
            .map(AiCaseDto::from).orElseThrow(() -> new BusinessException("案例不存在或已停用"));
    }
}
