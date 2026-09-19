/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aicases;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@SpringBootTest
@AutoConfigureMockMvc
class AiCasesApiIntegrationTests {
    @Autowired MockMvc mvc;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void listsTenCases() throws Exception {
        mvc.perform(get("/api/cases"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.length()").value(10));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void runsInvoiceInsight() throws Exception {
        mvc.perform(post("/api/cases/invoice-insight/run")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"inputs\":{\"invoiceCode\":\"3100241560\",\"amount\":12800,\"historicalAverage\":6200,\"vendor\":\"华东服务公司\"}}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.caseSlug").value("invoice-insight"))
            .andExpect(jsonPath("$.data.structuredData.amountRatio").exists());
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void exposesAdminOverview() throws Exception {
        mvc.perform(get("/api/admin/overview"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.caseCount").value(10))
            .andExpect(jsonPath("$.data.provider.configured").value(false));
    }
}
