package com.example.acreditaciones.repository;

import com.example.acreditaciones.entity.FundAccreditationPOS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundAccreditationRepo extends JpaRepository<FundAccreditationPOS, Long> {
}
