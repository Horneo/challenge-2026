package com.example.acreditaciones.controller;

import com.example.acreditaciones.dto.FundAccreditationDTO;
import com.example.acreditaciones.service.FundAccreditationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/accreditation") // Prefijo para todos los endpoints de este controlle
public class FundAccreditationController {

    private final FundAccreditationService fundAccreditationService;

    public FundAccreditationController(@Qualifier("FundAccreditationServiceImpl") FundAccreditationService  fundAccreditationService) {
        this.fundAccreditationService = fundAccreditationService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewAccreditation(@RequestBody @Valid FundAccreditationDTO body, UriComponentsBuilder uriBuilder) {


        fundAccreditationService.create(body);

        URI location = uriBuilder
                .path("/v1/accreditation/add")
                .build("");

        return ResponseEntity.created(location).body("");
    }

}
