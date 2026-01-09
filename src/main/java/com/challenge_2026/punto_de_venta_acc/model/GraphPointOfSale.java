package com.challenge_2026.punto_de_venta_acc.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "GraphPointOfSale")
public record GraphPointOfSale(@Id ObjectId id, String originPointOfSale, String destinationPointOfSale, Integer cost) {
}
