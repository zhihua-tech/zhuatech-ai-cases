/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aicases.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.Map;

public record RunCaseRequest(@NotEmpty Map<String, Object> inputs) {}
