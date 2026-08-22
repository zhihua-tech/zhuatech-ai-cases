/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aicases.repository;

import cn.zhuatech.aicases.model.AiCaseExecution;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Instant;
import java.util.List;

public interface AiCaseExecutionRepository extends JpaRepository<AiCaseExecution, Long> {
    List<AiCaseExecution> findAllByOrderByCreatedAtDesc(Pageable pageable);
    long countByCreatedAtAfter(Instant after);
    long countBySuccessFalse();
}
