package com.example.acreditaciones.service;

import com.example.acreditaciones.dto.FundAccreditationDTO;
import com.example.acreditaciones.dto.PosDTO;
import com.example.acreditaciones.model.FundAccreditation;
import com.example.acreditaciones.repository.FundAccreditationRepository;
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
public class FundAccreditationService {

    private final WebClient webClient;

    private final FundAccreditationRepository repo;

    public FundAccreditationService(@Qualifier("posWebClient") WebClient webClient, FundAccreditationRepository repo) {

        this.webClient= webClient;
        this.repo = repo;
    }


    public Flux<PosDTO> findAll() {
        return webClient.get()
                .uri("/v1/point-of-sale/findAll")
                .retrieve()
                .onStatus(s -> s.is4xxClientError(), resp -> resp.bodyToMono(String.class)
                        .map(body -> new RuntimeException("Error 4xx en findAll POS: " + body)))
                .onStatus(s -> s.is5xxServerError(), resp -> resp.bodyToMono(String.class)
                        .map(body -> new RuntimeException("Error 5xx en POS: " + body)))
                .bodyToFlux(PosDTO.class);
    }


        public void create(FundAccreditationDTO dto) {
            List<PosDTO> posList = findAll().collectList().block(); // ⚠️ bloquea el hilo

            Map<String, String> posNameById = posList.stream()
                    .collect(Collectors.toMap(PosDTO::getId, PosDTO::getName, (a, b) -> a)); // merge function defensiva

            String pointOfSaleName = posNameById.get(dto.idPointOfSale());

            if(Objects.isNull(pointOfSaleName) || pointOfSaleName.isEmpty()){
                throw new RuntimeException("El id de punto de venta no existe");
            }

        FundAccreditation fundAccreditation = new FundAccreditation(null, Instant.now(), dto.amount(), dto.idPointOfSale(), pointOfSaleName);
        repo.save(fundAccreditation);
    }
}



