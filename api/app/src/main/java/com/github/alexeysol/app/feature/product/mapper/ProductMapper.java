package com.github.alexeysol.app.feature.product.mapper;

import com.github.alexeysol.app.feature.place.mapper.PlaceMapper;
import com.github.alexeysol.app.feature.product.model.entity.Product;
import com.github.alexeysol.app.feature.place.model.entity.Place;
import com.github.alexeysol.common.feature.product.model.dto.CreateProductDto;
import com.github.alexeysol.common.feature.product.model.dto.ProductDto;
import com.github.alexeysol.common.feature.product.model.dto.UpdateProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = PlaceMapper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    default Page<ProductDto> map(Page<Product> productPage, Pageable pageable) {
        var productDtoList = map(productPage.getContent());
        return new PageImpl<>(productDtoList, pageable, productPage.getTotalElements());
    }

    default List<ProductDto> map(List<Product> products) {
        return products.stream()
            .map(this::map)
            .collect(Collectors.toList());
    }

    ProductDto map(Product product);

    Product map(ProductDto dto);

    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "place", source = "place")
    @Mapping(target = "id", ignore = true)
    Product map(CreateProductDto dto, Place place);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "place", ignore = true)
    Product map(UpdateProductDto dto, @MappingTarget Product product);
}
