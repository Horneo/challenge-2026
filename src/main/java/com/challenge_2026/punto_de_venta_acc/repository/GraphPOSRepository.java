package com.challenge_2026.punto_de_venta_acc.repository;

import com.challenge_2026.punto_de_venta_acc.model.GraphPointOfSale;
import com.challenge_2026.punto_de_venta_acc.model.PointOfSale;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface GraphPOSRepository extends MongoRepository<GraphPointOfSale, String> {

}

