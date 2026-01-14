package com.challenge_2026.punto_de_venta_acc.repository;

import com.challenge_2026.punto_de_venta_acc.model.GraphPointOfSale;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface GraphPOSRepository extends MongoRepository<GraphPointOfSale, ObjectId> {

    void deleteByOriginPointOfSaleAndDestinationPointOfSale(String pointA, String pointB);

}
