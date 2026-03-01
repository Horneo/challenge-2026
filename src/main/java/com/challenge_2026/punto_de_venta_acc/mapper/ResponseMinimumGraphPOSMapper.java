package com.challenge_2026.punto_de_venta_acc.mapper;

import com.challenge_2026.punto_de_venta_acc.dto.MinimumGraphPOSDto;
import com.challenge_2026.punto_de_venta_acc.entity.GraphPointOfSale;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponseMinimumGraphPOSMapper {

    static MinimumGraphPOSDto toDto(GraphPointOfSale dto) {
        return new MinimumGraphPOSDto(dto.getOriginPointOfSale(), dto.getDestinationPointOfSale(), dto.getCost());
    }
}


