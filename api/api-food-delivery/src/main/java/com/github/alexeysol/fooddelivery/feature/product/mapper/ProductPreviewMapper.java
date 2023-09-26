package com.github.alexeysol.fooddelivery.feature.product.mapper;

import com.github.alexeysol.common.feature.product.model.dto.ProductPreviewDto;
import com.github.alexeysol.fooddelivery.feature.product.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductPreviewMapper {
    default Page<ProductPreviewDto> map(Page<Product> productPage, Pageable pageable) {
        var productDtoList = map(productPage.getContent());
        return new PageImpl<>(productDtoList, pageable, productPage.getTotalElements());
    }

    default List<ProductPreviewDto> map(List<Product> products) {
        return products.stream()
            .map(this::map)
            .collect(Collectors.toList());
    }

    ProductPreviewDto map(Product product);
}
