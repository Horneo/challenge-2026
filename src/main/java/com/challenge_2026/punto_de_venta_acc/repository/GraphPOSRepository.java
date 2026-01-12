package com.challenge_2026.punto_de_venta_acc.repository;

import com.challenge_2026.punto_de_venta_acc.model.GraphPointOfSale;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GraphPOSRepository extends MongoRepository<GraphPointOfSale, String> {

     void deleteByOriginPointOfSaleAndDestinationPointOfSale(String pointA, String pointB);
}

