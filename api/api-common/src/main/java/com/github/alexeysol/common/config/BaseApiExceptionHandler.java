package com.github.alexeysol.common.config;

import com.github.alexeysol.common.shared.model.dto.ServiceResponseExceptionDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class BaseApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleResponseStatus(Exception exception, WebRequest request) {
        var dto = new ServiceResponseExceptionDto().toBuilder()
            .detail(exception.getMessage())
            .title(exception.getClass().getSimpleName())
            .build();

        return handleExceptionInternal(exception, dto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
            request);
    }
}
