package com.github.alexeysol.common.shared.model.dto;

import lombok.Data;

@Data
public class ServiceResponseExceptionDto {
    private String type;
    private String title;
    private String detail;
    private String instance;
    private Integer status;
}
