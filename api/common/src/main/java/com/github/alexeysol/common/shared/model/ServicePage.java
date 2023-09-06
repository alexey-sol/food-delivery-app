package com.github.alexeysol.common.shared.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ServicePage<E> {
    private List<E> content;
    private int size;
    private int totalElements;
}
