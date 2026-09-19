/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.aicases.ai;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Component
public class OpenAiCompatibleGateway {
    private final String provider;
    private final String baseUrl;
    private final String model;
    private final String apiKey;
    private final RestClient restClient;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public OpenAiCompatibleGateway(
        @Value("${zhuatech.ai.provider:local}") String provider,
        @Value("${zhuatech.ai.base-url:https://api.deepseek.com}") String baseUrl,
        @Value("${zhuatech.ai.model:deepseek-chat}") String model,
        @Value("${zhuatech.ai.api-key:}") String apiKey) {
        this.provider = provider;
        this.baseUrl = baseUrl.replaceAll("/+$", "");
        this.model = model;
        this.apiKey = apiKey;
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(8)).build();
        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(client);
        factory.setReadTimeout(Duration.ofSeconds(25));
        this.restClient = RestClient.builder().requestFactory(factory).build();
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Optional<String> complete(String systemPrompt, String businessContext) {
        if ("local".equalsIgnoreCase(provider) || apiKey == null || apiKey.isBlank()) return Optional.empty();
        String context = businessContext.length() > 6000 ? businessContext.substring(0, 6000) : businessContext;
        Map<String, Object> body = Map.of(
            "model", model,
            "temperature", 0.2,
            "messages", List.of(
                Map.of("role", "system", "content", systemPrompt),
                Map.of("role", "user", "content", context)
            )
        );
        try {
            JsonNode response = restClient.post()
                .uri(baseUrl + "/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .body(body)
                .retrieve()
                .body(JsonNode.class);
            if (response == null) return Optional.empty();
            JsonNode content = response.at("/choices/0/message/content");
            return content.isTextual() ? Optional.of(content.asText()) : Optional.empty();
        } catch (RuntimeException ignored) {
            return Optional.empty();
        }
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public ProviderStatus status() {
        boolean configured = !"local".equalsIgnoreCase(provider) && apiKey != null && !apiKey.isBlank();
        return new ProviderStatus(provider, model, baseUrl, configured, configured ? "模型增强已启用" : "本地规则模式");
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record ProviderStatus(String provider, String model, String baseUrl, boolean configured, String label) {}
}
