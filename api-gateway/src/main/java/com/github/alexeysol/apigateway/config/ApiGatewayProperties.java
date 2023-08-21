package com.github.alexeysol.apigateway.config;

import com.github.alexeysol.common.model.ServiceProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "service")
@Component
@Data
public class ApiGatewayProperties {
    private ServiceProperties app;
}
