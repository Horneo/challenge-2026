package com.challenge_2026.punto_de_venta_acc.repository;

import com.challenge_2026.punto_de_venta_acc.model.GraphPointOfSale;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import java.util.List;

@Repository
public class GraphPointOfSaleAggRepository {

    private final MongoTemplate mongoTemplate;

    public GraphPointOfSaleAggRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<GraphPointOfSale> findMinCostPerOriginAndDestination() {
        Aggregation agg = newAggregation(
                // 1) Ordeno por par y por costo (para que el primero sea el de menor costo)
                sort(Sort.by(
                        Sort.Order.asc("originPointOfSale"),
                        Sort.Order.asc("destinationPointOfSale"),
                        Sort.Order.asc("cost")
                )),
                // 2) Agrupo por origin/destination y me quedo con el primer doc (el de menor costo)
                group("originPointOfSale", "destinationPointOfSale")
                        .first("_id").as("id")
                        .first("originPointOfSale").as("originPointOfSale")
                        .first("destinationPointOfSale").as("destinationPointOfSale")
                        .first("cost").as("cost"),
                // 3) Proyección final
                project("id", "originPointOfSale", "destinationPointOfSale", "cost")
                        .andExclude("_id")
        );

        AggregationResults<GraphPointOfSale> results =
                mongoTemplate.aggregate(agg, "GraphPointOfSale", GraphPointOfSale.class);

        return results.getMappedResults();
    }
}

