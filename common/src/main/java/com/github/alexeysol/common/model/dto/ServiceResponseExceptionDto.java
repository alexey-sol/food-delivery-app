package com.github.alexeysol.common.model.dto;

import lombok.Data;

@Data
public class ServiceResponseExceptionDto {
    private String path;
    private String trace;
    private String error;
    private String message;
    private String timestamp;
    private int status;
}
