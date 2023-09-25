package com.github.alexeysol.common.shared.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ServiceResponseException extends RuntimeException {
    private final String json;
}
