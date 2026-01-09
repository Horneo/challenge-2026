package com.challenge_2026.punto_de_venta_acc.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "GraphPointOfSale")
public record GraphPointOfSale(String originPointOfSale, String destinationPointOfSale, Integer cost) {
}
