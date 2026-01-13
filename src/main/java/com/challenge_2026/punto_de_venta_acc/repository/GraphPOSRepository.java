package com.challenge_2026.punto_de_venta_acc.repository;

import com.challenge_2026.punto_de_venta_acc.model.GraphPointOfSale;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GraphPOSRepository extends MongoRepository<GraphPointOfSale, ObjectId> {

     void deleteByOriginPointOfSaleAndDestinationPointOfSale(String pointA, String pointB);

     @Aggregation(pipeline = {
             "{ $sort: { originPointOfSale: 1, destinationPointOfSale: 1, cost: 1 } }",
             " $group: { _id: { origin: '$originPointOfSale', destination: '$destinationPointOfSale'}, doc { $first: '$$ROOT'} } }",
             " $replaceRoot: { newRoot: '$doc'} }"
     })
     List<GraphPointOfSale> findMinCostPerOriginAndDestination();

}
