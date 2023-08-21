package com.github.alexeysol.common.model;

import lombok.Data;

@Data
public class ServiceProperties {
    private String baseUrl;
    private String apiPrefix = "/api";
}
