package com.github.alexeysol.common.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessageConstant {
    public final String ALREADY_EXISTS = "%s already exists";
    public final String INVALID_CREDENTIALS = "The credentials are invalid or the profile doesn't exist";
    public final String NOT_FOUND = "%s doesn't exist";
    public final String NOT_FOUND_BY_ID = "%s with id = %d doesn't exist";
}
