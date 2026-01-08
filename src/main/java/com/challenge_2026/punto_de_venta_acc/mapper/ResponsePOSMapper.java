package com.challenge_2026.punto_de_venta_acc.mapper;

import com.challenge_2026.punto_de_venta_acc.dto.CreatePOSRequest;
import com.challenge_2026.punto_de_venta_acc.dto.POSDto;
import com.challenge_2026.punto_de_venta_acc.dto.POSResponse;
import com.challenge_2026.punto_de_venta_acc.model.PointOfSale;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponsePOSMapper {

    static POSDto toEntity(PointOfSale dto) {
        return new POSDto(dto.id(), dto.name());
    }
}


