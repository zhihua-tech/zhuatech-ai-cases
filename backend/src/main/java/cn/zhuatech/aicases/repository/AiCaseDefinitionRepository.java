/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aicases.repository;

import cn.zhuatech.aicases.model.AiCaseDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AiCaseDefinitionRepository extends JpaRepository<AiCaseDefinition, Long> {
    Optional<AiCaseDefinition> findBySlug(String slug);
    List<AiCaseDefinition> findAllByOrderBySortOrderAsc();
}
