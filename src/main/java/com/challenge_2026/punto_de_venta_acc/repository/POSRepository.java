package com.challenge_2026.punto_de_venta_acc.repository;

import com.challenge_2026.punto_de_venta_acc.model.PointOfSale;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface POSRepository extends MongoRepository<PointOfSale, String> {

    // Búsqueda parcial por nombre (case-insensitive si se configura adecuadamente el índice/collation)
    List<PointOfSale> findByNameContainingIgnoreCase(String namePart);

    // Retorna el primero por nombre exacto
    Optional<PointOfSale> findFirstByName(String name);
}

