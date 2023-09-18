package com.github.alexeysol.gateway.config;

import com.github.alexeysol.common.exception.ServiceResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {
    private final GatewayProperties gatewayProperties;

    @Bean
    public WebClient appWebClient() {
        var appProperties = gatewayProperties.getApp();
        var baseUrl = appProperties.getBaseUrl() + appProperties.getApiPrefix();

        DefaultUriBuilderFactory uriBuilder = new DefaultUriBuilderFactory(baseUrl);
        uriBuilder.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        return WebClient.builder()
            .uriBuilderFactory(uriBuilder)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultStatusHandler(HttpStatusCode::isError, (response) ->
                response
                    .bodyToMono(String.class)
                    .defaultIfEmpty("") // TODO doesn't persist res status code and details (gives just 500). TO test this behavior, delete security config from app - so that it always returns 401
                    .flatMap(json -> Mono.error(new ServiceResponseException(json))))
            .build();
    }
}
