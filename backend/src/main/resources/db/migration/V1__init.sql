-- Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/
CREATE TABLE ai_case_definition (
    id BIGINT NOT NULL AUTO_INCREMENT,
    slug VARCHAR(80) NOT NULL,
    name VARCHAR(120) NOT NULL,
    category VARCHAR(40) NOT NULL,
    summary VARCHAR(500) NOT NULL,
    icon VARCHAR(40) NOT NULL,
    accent VARCHAR(20) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    featured BOOLEAN NOT NULL DEFAULT FALSE,
    sort_order INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_ai_case_slug (slug)
);

CREATE TABLE ai_case_execution (
    id BIGINT NOT NULL AUTO_INCREMENT,
    case_slug VARCHAR(80) NOT NULL,
    request_summary VARCHAR(1000) NOT NULL,
    result_summary TEXT NOT NULL,
    execution_mode VARCHAR(30) NOT NULL,
    duration_ms BIGINT NOT NULL,
    success BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_execution_case_created (case_slug, created_at)
);
