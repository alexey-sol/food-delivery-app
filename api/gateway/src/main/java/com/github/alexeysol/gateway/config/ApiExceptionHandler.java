package com.github.alexeysol.gateway.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.alexeysol.common.shared.exception.ServiceResponseException;
import com.github.alexeysol.common.shared.model.dto.ServiceResponseExceptionDto;
import com.github.alexeysol.common.shared.util.JsonUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { ServiceResponseException.class })
    protected ResponseEntity<Object> handleResponseStatus(ServiceResponseException exception, WebRequest request) {
        var dto = JsonUtil.deserialize(exception.getJson(),
            new TypeReference<ServiceResponseExceptionDto>() {});

        return handleExceptionInternal(exception, dto, new HttpHeaders(), HttpStatus.valueOf(dto.getStatus()),
            request);
    }
}
