package com.github.alexeysol.common.shared.util;

import java.util.Optional;

// TODO can t make it work https://github.com/mapstruct/mapstruct/issues/2477
public class MapperUtil {
    public static <T> T fromOptional(Optional<T> optional) {
        return optional.orElse(null);
    }
}
