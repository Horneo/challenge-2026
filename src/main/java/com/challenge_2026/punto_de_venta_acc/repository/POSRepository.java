package com.challenge_2026.punto_de_venta_acc.repository;

import com.challenge_2026.punto_de_venta_acc.entity.PointOfSale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface POSRepository extends JpaRepository<PointOfSale, Long> {

    Optional<PointOfSale> findByName(String name);
}

