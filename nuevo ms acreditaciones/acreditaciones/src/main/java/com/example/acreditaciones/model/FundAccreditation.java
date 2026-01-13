package com.example.acreditaciones.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "FundAccreditation")
public record FundAccreditation(@Id ObjectId id, Instant receivedAt, BigDecimal amount, String idPointOfSale, String namePointOfSale) {
}
