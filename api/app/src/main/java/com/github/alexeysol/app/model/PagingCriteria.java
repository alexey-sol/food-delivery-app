package com.github.alexeysol.app.model;

import lombok.Data;

@Data
public class PagingCriteria {
    private int page = 0;
    private int size = 20;
}
