package com.github.alexeysol.common.feature.place.exception;

import com.github.alexeysol.common.shared.constant.ErrorMessageConstant;
import com.github.alexeysol.common.shared.constant.ResourceConstant;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PlaceNotFoundException extends ResponseStatusException {
    public PlaceNotFoundException() {
        super(HttpStatus.NOT_FOUND, String.format(ErrorMessageConstant.NOT_FOUND, ResourceConstant.PLACE));
    }
}
