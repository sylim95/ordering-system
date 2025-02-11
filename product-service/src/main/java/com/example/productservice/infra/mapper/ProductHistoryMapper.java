package com.example.productservice.infra.mapper;

import com.example.productservice.app.domain.model.ProductHistory;
import com.example.productservice.infra.entity.ProductHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductHistoryMapper {

    ProductHistoryMapper INSTANCE = Mappers.getMapper(ProductHistoryMapper.class);

    ProductHistoryEntity toEntity(ProductHistory domain);
}
