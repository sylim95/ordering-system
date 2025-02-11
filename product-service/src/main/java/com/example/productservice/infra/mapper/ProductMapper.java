package com.example.productservice.infra.mapper;

import com.example.productservice.app.domain.model.Product;
import com.example.productservice.infra.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    List<Product> toDomainList(List<ProductEntity> list);

    Product toDomain(ProductEntity entity);

    List<ProductEntity> toEntityList(List<Product> list);

    ProductEntity toEntity(Product domain);
}
