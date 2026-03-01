package com.example.acreditaciones.service;

import com.example.acreditaciones.dto.FundAccreditationDTO;
import com.example.acreditaciones.dto.PosDTO;
//import com.example.acreditaciones.model.FundAccreditation;
//import com.example.acreditaciones.repository.FundAccreditationRepository;
import com.example.acreditaciones.entity.FundAccreditationPOS;
import com.example.acreditaciones.repository.FundAccreditationRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public interface FundAccreditationService {


    public Flux<PosDTO> findAll();


    public void create(FundAccreditationDTO dto);

}



