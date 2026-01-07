package com.challenge_2026.punto_de_venta_acc.model;

import com.challenge_2026.punto_de_venta_acc.dto.CreatePOSRequest;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pos")
public record PointOfSale(
        @Id String id,
        String name) {


    public static PointOfSale from(CreatePOSRequest dto) {
        return new PointOfSale(dto.id(), dto.name());
    }

    // "Wither": crea una nueva instancia con name actualizado
    public PointOfSale withName(String newName) {
        return new PointOfSale(this.id, newName);
    }


}

