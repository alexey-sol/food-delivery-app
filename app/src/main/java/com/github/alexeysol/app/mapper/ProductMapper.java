package com.github.alexeysol.app.mapper;

import com.github.alexeysol.app.model.dto.CreateProductDto;
import com.github.alexeysol.app.model.dto.ProductDto;
import com.github.alexeysol.app.model.entity.Product;
import com.github.alexeysol.app.model.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(uses = StoreMapper.class)
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    default Set<ProductDto> map(Set<Product> products) {
        return products.stream()
            .map(this::map)
            .collect(Collectors.toSet());
    }

    ProductDto map(Product product);

    Product map(ProductDto dto);

    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "store", source = "store")
    @Mapping(target = "id", ignore = true)
    Product map(CreateProductDto dto, Store store);
}
