package com.github.alexeysol.app.util;

import com.github.alexeysol.app.model.PagingCriteria;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@UtilityClass
public class PageableUtil {
    public Pageable convert(String criteriaJson) {
        var criteria = JsonUtil.deserialize(criteriaJson, PagingCriteria.class);
        return PageRequest.of(criteria.getPage(), criteria.getSize());
    }

    public Pageable convert(PagingCriteria criteria) {
        return PageRequest.of(criteria.getPage(), criteria.getSize());
    }
}
