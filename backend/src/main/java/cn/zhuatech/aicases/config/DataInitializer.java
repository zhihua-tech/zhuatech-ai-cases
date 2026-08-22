/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aicases.config;

import cn.zhuatech.aicases.model.AiCaseDefinition;
import cn.zhuatech.aicases.repository.AiCaseDefinitionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final AiCaseDefinitionRepository repository;
    public DataInitializer(AiCaseDefinitionRepository repository) { this.repository = repository; }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) return;
        int order = 1;
        for (CaseCatalog.CaseSpec spec : CaseCatalog.all()) {
            repository.save(new AiCaseDefinition(spec.slug(), spec.name(), spec.category(), spec.summary(),
                spec.icon(), spec.accent(), spec.featured(), order++));
        }
    }
}
