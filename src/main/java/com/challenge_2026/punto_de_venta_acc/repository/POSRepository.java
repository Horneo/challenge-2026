package com.challenge_2026.punto_de_venta_acc.repository;

import com.challenge_2026.punto_de_venta_acc.entity.PointOfSale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface POSRepository extends JpaRepository<PointOfSale, Long> {

    // Búsqueda parcial por nombre (case-insensitive si se configura adecuadamente el índice/collation)
    List<PointOfSale> findByNameContainingIgnoreCase(String namePart);

    // Retorna el primero por nombre exacto
    Optional<PointOfSale> findFirstByName(String name);
}

