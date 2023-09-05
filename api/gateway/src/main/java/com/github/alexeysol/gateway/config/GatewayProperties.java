package com.github.alexeysol.gateway.config;

import com.github.alexeysol.common.model.AppProperties;
import com.github.alexeysol.gateway.model.AdminProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "service")
@Component
@Data
public class GatewayProperties {
    private AppProperties app;
    private AdminProperties admin;
}
