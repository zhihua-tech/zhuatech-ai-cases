/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aicases.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "ai_case_definition")
public class AiCaseDefinition {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 80)
    private String slug;
    @Column(nullable = false, length = 120)
    private String name;
    @Column(nullable = false, length = 40)
    private String category;
    @Column(nullable = false, length = 500)
    private String summary;
    @Column(nullable = false, length = 40)
    private String icon;
    @Column(nullable = false, length = 20)
    private String accent;
    @Column(nullable = false)
    private boolean enabled;
    @Column(nullable = false)
    private boolean featured;
    @Column(name = "sort_order", nullable = false)
    private int sortOrder;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected AiCaseDefinition() {}

    public AiCaseDefinition(String slug, String name, String category, String summary, String icon,
                            String accent, boolean featured, int sortOrder) {
        this.slug = slug;
        this.name = name;
        this.category = category;
        this.summary = summary;
        this.icon = icon;
        this.accent = accent;
        this.enabled = true;
        this.featured = featured;
        this.sortOrder = sortOrder;
    }

    @PrePersist void onCreate() { createdAt = updatedAt = Instant.now(); }
    @PreUpdate void onUpdate() { updatedAt = Instant.now(); }
    public Long getId() { return id; }
    public String getSlug() { return slug; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getSummary() { return summary; }
    public String getIcon() { return icon; }
    public String getAccent() { return accent; }
    public boolean isEnabled() { return enabled; }
    public boolean isFeatured() { return featured; }
    public int getSortOrder() { return sortOrder; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public void setFeatured(boolean featured) { this.featured = featured; }
}
