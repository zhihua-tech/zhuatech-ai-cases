/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aicases.dto;

import java.util.List;
import java.util.Map;

public record RunCaseResult(String caseSlug, String title, String level, int score, String summary,
                            List<String> findings, List<String> actions, Map<String, Object> structuredData,
                            String executionMode, String provider, String model, long durationMs) {}
