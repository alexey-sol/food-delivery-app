package com.github.alexeysol.common.feature.product.exception;

import com.github.alexeysol.common.shared.constant.ErrorMessageConstant;
import com.github.alexeysol.common.shared.constant.ResourceConstant;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductNotFoundException extends ResponseStatusException {
    public ProductNotFoundException() {
        super(HttpStatus.NOT_FOUND, String.format(ErrorMessageConstant.NOT_FOUND, ResourceConstant.PRODUCT));
    }
}
