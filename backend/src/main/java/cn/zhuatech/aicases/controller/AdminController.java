/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aicases.controller;

import cn.zhuatech.aicases.common.ApiResponse;
import cn.zhuatech.aicases.dto.AiCaseDto;
import cn.zhuatech.aicases.service.AdminDashboardService;
import cn.zhuatech.aicases.service.AiCaseQueryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminDashboardService dashboardService;
    private final AiCaseQueryService queryService;
    public AdminController(AdminDashboardService dashboardService, AiCaseQueryService queryService) {
        this.dashboardService = dashboardService;
        this.queryService = queryService;
    }

    @GetMapping("/overview") public ApiResponse<AdminDashboardService.Overview> overview() { return ApiResponse.ok(dashboardService.overview()); }
    @GetMapping("/cases") public ApiResponse<List<AiCaseDto>> cases() { return ApiResponse.ok(queryService.list(true)); }
    @GetMapping("/executions") public ApiResponse<List<AdminDashboardService.ExecutionView>> executions(@RequestParam(defaultValue = "20") int limit) {
        return ApiResponse.ok(dashboardService.executions(limit));
    }
    @PatchMapping("/cases/{id}") public ApiResponse<AiCaseDto> update(@PathVariable Long id, @RequestBody Map<String, Boolean> values) {
        return ApiResponse.ok(dashboardService.updateCase(id, values));
    }
}
