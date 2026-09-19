/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aicases.controller;

import cn.zhuatech.aicases.common.ApiResponse;
import cn.zhuatech.aicases.dto.AiCaseDto;
import cn.zhuatech.aicases.dto.RunCaseRequest;
import cn.zhuatech.aicases.dto.RunCaseResult;
import cn.zhuatech.aicases.service.AiCaseQueryService;
import cn.zhuatech.aicases.service.CaseExecutionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/cases")
public class AiCaseController {
    private final AiCaseQueryService queryService;
    private final CaseExecutionService executionService;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public AiCaseController(AiCaseQueryService queryService, CaseExecutionService executionService) {
        this.queryService = queryService;
        this.executionService = executionService;
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @GetMapping public ApiResponse<List<AiCaseDto>> list() { return ApiResponse.ok(queryService.list(false)); }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @GetMapping("/{slug}") public ApiResponse<AiCaseDto> get(@PathVariable String slug) { return ApiResponse.ok(queryService.get(slug)); }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/{slug}/run")
    public ApiResponse<RunCaseResult> run(@PathVariable String slug, @Valid @RequestBody RunCaseRequest request) {
        return ApiResponse.ok(executionService.run(slug, request));
    }
}
