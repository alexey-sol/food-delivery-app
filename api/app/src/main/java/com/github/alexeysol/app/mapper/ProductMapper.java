package com.github.alexeysol.app.mapper;

import com.github.alexeysol.app.model.entity.Product;
import com.github.alexeysol.app.model.entity.Store;
import com.github.alexeysol.common.model.dto.CreateProductDto;
import com.github.alexeysol.common.model.dto.ProductDto;
import com.github.alexeysol.common.model.dto.UpdateProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(uses = StoreMapper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    default Page<ProductDto> map(Page<Product> productPage, Pageable pageable) {
        var productDtoList = map(productPage.getContent());
        return new PageImpl<>(productDtoList, pageable, productPage.getTotalElements());
    }

    default List<ProductDto> map(List<Product> products) {
        return products.stream()
            .map(this::map)
            .toList();
    }

    ProductDto map(Product product);

    Product map(ProductDto dto);

    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "store", source = "store")
    @Mapping(target = "id", ignore = true)
    Product map(CreateProductDto dto, Store store);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "store", ignore = true)
    Product map(UpdateProductDto dto, @MappingTarget Product product);
}
