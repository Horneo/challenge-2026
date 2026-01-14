package com.example.acreditaciones.service;

import com.example.acreditaciones.dto.FundAccreditationDTO;
import com.example.acreditaciones.model.FundAccreditation;
import com.example.acreditaciones.repository.FundAccreditationRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class FundAccreditationService {
    private final FundAccreditationRepository repo;

    public FundAccreditationService(FundAccreditationRepository repo) {
        this.repo = repo;
    }

    public void create(FundAccreditationDTO dto) {
        FundAccreditation fundAccreditation = new FundAccreditation(null, Instant.now(), dto.amount(), dto.idPointOfSale(), "in-process");
        repo.save(fundAccreditation);
    }
}



