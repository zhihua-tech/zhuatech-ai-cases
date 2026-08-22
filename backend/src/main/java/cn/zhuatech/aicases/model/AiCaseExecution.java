/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aicases.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "ai_case_execution")
public class AiCaseExecution {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "case_slug", nullable = false, length = 80)
    private String caseSlug;
    @Column(name = "request_summary", nullable = false, length = 1000)
    private String requestSummary;
    @Column(name = "result_summary", nullable = false, columnDefinition = "TEXT")
    private String resultSummary;
    @Column(name = "execution_mode", nullable = false, length = 30)
    private String executionMode;
    @Column(name = "duration_ms", nullable = false)
    private long durationMs;
    @Column(nullable = false)
    private boolean success;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected AiCaseExecution() {}

    public AiCaseExecution(String caseSlug, String requestSummary, String resultSummary,
                           String executionMode, long durationMs, boolean success) {
        this.caseSlug = caseSlug;
        this.requestSummary = requestSummary;
        this.resultSummary = resultSummary;
        this.executionMode = executionMode;
        this.durationMs = durationMs;
        this.success = success;
    }

    @PrePersist void onCreate() { createdAt = Instant.now(); }
    public Long getId() { return id; }
    public String getCaseSlug() { return caseSlug; }
    public String getRequestSummary() { return requestSummary; }
    public String getResultSummary() { return resultSummary; }
    public String getExecutionMode() { return executionMode; }
    public long getDurationMs() { return durationMs; }
    public boolean isSuccess() { return success; }
    public Instant getCreatedAt() { return createdAt; }
}
