/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aicases.dto;

import cn.zhuatech.aicases.config.CaseCatalog;
import cn.zhuatech.aicases.model.AiCaseDefinition;
import java.util.List;

public record AiCaseDto(Long id, String slug, String name, String category, String summary, String icon,
                        String accent, boolean enabled, boolean featured, List<CaseCatalog.FieldSpec> fields) {
    public static AiCaseDto from(AiCaseDefinition entity) {
        CaseCatalog.CaseSpec spec = CaseCatalog.get(entity.getSlug());
        return new AiCaseDto(entity.getId(), entity.getSlug(), entity.getName(), entity.getCategory(),
            entity.getSummary(), entity.getIcon(), entity.getAccent(), entity.isEnabled(), entity.isFeatured(),
            spec == null ? List.of() : spec.fields());
    }
}
