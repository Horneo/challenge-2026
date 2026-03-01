package com.challenge_2026.punto_de_venta_acc.repository;

import com.challenge_2026.punto_de_venta_acc.entity.GraphPointOfSale;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GraphPOSRepository extends JpaRepository<GraphPointOfSale, Long> {

    void deleteByOriginPointOfSaleAndDestinationPointOfSale(String pointA, String pointB);

}
