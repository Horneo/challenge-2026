package com.challenge_2026.punto_de_venta_acc.repository;

import com.challenge_2026.punto_de_venta_acc.entity.GraphPointOfSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraphPointOfSaleRouteRepository extends JpaRepository<GraphPointOfSale, Long> {

    @Query(value = "SELECT g.* FROM graph_pos g " +
            "INNER JOIN ( " +
            "  SELECT origin_pos, destination_pos, MIN(cost) AS min_cost " +
            "  FROM graph_pos " +
            "  GROUP BY origin_pos, destination_pos " +
            ") m ON g.origin_pos = m.origin_pos " +
            "AND g.destination_pos = m.destination_pos " +
            "AND g.cost = m.min_cost",
            nativeQuery = true)
    List<GraphPointOfSale> findMinCostPerOriginAndDestination();
}

