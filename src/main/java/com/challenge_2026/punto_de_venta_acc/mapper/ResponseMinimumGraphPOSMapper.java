package com.challenge_2026.punto_de_venta_acc.mapper;

import com.challenge_2026.punto_de_venta_acc.dto.CreateGraphPOSRequest;
import com.challenge_2026.punto_de_venta_acc.dto.GraphPOSDto;
import com.challenge_2026.punto_de_venta_acc.dto.MinimumGraphPOSDto;
import com.challenge_2026.punto_de_venta_acc.model.GraphPointOfSale;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponseMinimumGraphPOSMapper {

    static MinimumGraphPOSDto toDto(GraphPointOfSale dto) {
        return new MinimumGraphPOSDto(dto.originPointOfSale(), dto.destinationPointOfSale(), dto.cost());
    }
}


