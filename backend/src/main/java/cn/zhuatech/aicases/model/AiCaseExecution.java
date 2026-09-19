/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aicases.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
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

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected AiCaseExecution() {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public AiCaseExecution(String caseSlug, String requestSummary, String resultSummary,
                           String executionMode, long durationMs, boolean success) {
        this.caseSlug = caseSlug;
        this.requestSummary = requestSummary;
        this.resultSummary = resultSummary;
        this.executionMode = executionMode;
        this.durationMs = durationMs;
        this.success = success;
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PrePersist void onCreate() { createdAt = Instant.now(); }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Long getId() { return id; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getCaseSlug() { return caseSlug; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getRequestSummary() { return requestSummary; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getResultSummary() { return resultSummary; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getExecutionMode() { return executionMode; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public long getDurationMs() { return durationMs; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public boolean isSuccess() { return success; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Instant getCreatedAt() { return createdAt; }
}
