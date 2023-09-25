package com.github.alexeysol.common.shared.model;

import lombok.Data;

@Data
public class AppProperties {
    private String baseUrl;
    private String apiPrefix = "/api";
}
