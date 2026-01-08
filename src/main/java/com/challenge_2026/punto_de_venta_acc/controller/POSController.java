package com.challenge_2026.punto_de_venta_acc.controller;

import com.challenge_2026.punto_de_venta_acc.dto.CreatePOSRequest;
import com.challenge_2026.punto_de_venta_acc.dto.POSDto;
import com.challenge_2026.punto_de_venta_acc.dto.POSResponse;
import com.challenge_2026.punto_de_venta_acc.dto.UpdatePOSRequest;
import com.challenge_2026.punto_de_venta_acc.model.PointOfSale;
import com.challenge_2026.punto_de_venta_acc.service.POSService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/v1/point-of-sale") // Prefijo para todos los endpoints de este controlle
public class POSController {

    private final POSService posService;

    // Spring inyecta automáticamente el bean POSService
    public POSController(POSService posService) {
        this.posService = posService;
    }


    @GetMapping("/findAll")
    public List<POSDto> findAllPOS() {
        return posService.findAll();
    }


    @PostMapping("/create")
    public ResponseEntity<POSResponse> createPOS(
            @RequestBody CreatePOSRequest request, UriComponentsBuilder uriBuilder) {

        PointOfSale created = posService.create(PointOfSale.from(request));
        //Construye la URL del recurso recién creado: /v1/point-of-sale/{id}
        URI location = uriBuilder
                .path("/v1/point-of-sale/{id}")
                .build(created.id());

        return ResponseEntity.created(location).body(null);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UpdatePOSRequest> put(
            @PathVariable Long id,
            @RequestBody UpdatePOSRequest body) {
        try {
            PointOfSale updated = posService.updateNamePointOfSale(String.valueOf(id), body.name());
            return ResponseEntity.ok(body);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {
        posService.delete(String.valueOf(id));
        return ResponseEntity.ok("POS Deleted");
    }

}