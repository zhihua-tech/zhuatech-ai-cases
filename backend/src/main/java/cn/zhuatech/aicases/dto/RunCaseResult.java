/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aicases.dto;

import java.util.List;
import java.util.Map;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public record RunCaseResult(String caseSlug, String title, String level, int score, String summary,
                            List<String> findings, List<String> actions, Map<String, Object> structuredData,
                            String executionMode, String provider, String model, long durationMs) {}
