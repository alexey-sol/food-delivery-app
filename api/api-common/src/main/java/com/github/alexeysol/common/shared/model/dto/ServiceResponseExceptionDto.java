package com.github.alexeysol.common.shared.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponseExceptionDto {
    private String type = "about:blank";
    private String title = "";
    private String detail = "";
    private String instance = "";
    private Integer status = HttpStatus.INTERNAL_SERVER_ERROR.value();
}
