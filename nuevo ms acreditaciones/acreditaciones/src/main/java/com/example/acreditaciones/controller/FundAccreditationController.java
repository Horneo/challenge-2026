package com.example.acreditaciones.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/accreditation") // Prefijo para todos los endpoints de este controlle
public class FundAccreditationController {

    // Spring inyecta automáticamente el bean POSService
    public FundAccreditationController() {

    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewAccreditation(@RequestBody @Valid String body, UriComponentsBuilder uriBuilder) {


        URI location = uriBuilder
                .path("/v1/accreditation/add")
                .build("");

        return ResponseEntity.created(location).body("");
    }

}
