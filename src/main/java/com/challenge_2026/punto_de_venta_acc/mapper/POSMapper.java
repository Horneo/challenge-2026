package com.challenge_2026.punto_de_venta_acc.mapper;


import com.challenge_2026.punto_de_venta_acc.dto.CreatePOSRequest;
import com.challenge_2026.punto_de_venta_acc.model.PointOfSale;

//@Mapper(componentModel = "spring")
public interface POSMapper {
    PointOfSale toEntity(CreatePOSRequest dto);
}

