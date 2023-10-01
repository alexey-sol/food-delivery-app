package com.github.alexeysol.common.feature.order.exception;

import com.github.alexeysol.common.shared.constant.ErrorMessageConstant;
import com.github.alexeysol.common.shared.constant.ResourceConstant;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderNotFoundException extends ResponseStatusException {
    public OrderNotFoundException() {
        super(HttpStatus.NOT_FOUND, String.format(ErrorMessageConstant.NOT_FOUND, ResourceConstant.ORDER));
    }
}
