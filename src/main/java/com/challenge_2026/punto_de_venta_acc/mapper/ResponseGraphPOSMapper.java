package com.challenge_2026.punto_de_venta_acc.mapper;

import com.challenge_2026.punto_de_venta_acc.dto.CreateGraphPOSRequest;
import com.challenge_2026.punto_de_venta_acc.dto.GraphPOSDto;
import com.challenge_2026.punto_de_venta_acc.dto.POSDto;
import com.challenge_2026.punto_de_venta_acc.model.GraphPointOfSale;
import com.challenge_2026.punto_de_venta_acc.model.PointOfSale;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponseGraphPOSMapper {

    static GraphPOSDto toDto( GraphPointOfSale dto) {
        return new GraphPOSDto(dto.originPointOfSale(), dto.destinationPointOfSale(), dto.cost());
    }

    static GraphPointOfSale toEntity(CreateGraphPOSRequest dto) {
        return new GraphPointOfSale(dto.pointA(), dto.pointB(), dto.cost());
    }
}


