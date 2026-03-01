package com.challenge_2026.punto_de_venta_acc.controller;

import com.challenge_2026.punto_de_venta_acc.dto.CreatePOSRequest;
import com.challenge_2026.punto_de_venta_acc.dto.POSDto;
import com.challenge_2026.punto_de_venta_acc.dto.POSResponse;
import com.challenge_2026.punto_de_venta_acc.dto.UpdatePOSRequest;
import com.challenge_2026.punto_de_venta_acc.entity.PointOfSale;
import com.challenge_2026.punto_de_venta_acc.service.impl.POSServiceImpl;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/v1/point-of-sale") // Prefijo para todos los endpoints de este controlle
public class POSController {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(POSController.class);

    private final POSServiceImpl posService;

    // Spring inyecta automáticamente el bean POSService
    public POSController(POSServiceImpl posService) {

        this.posService = posService;
    }


    @GetMapping("/findAll")
    public List<POSDto> findAllPOS()
    {
        logger.info("endpoint Find all POS called");
        return posService.findAll();
    }


    @PostMapping("/create")
    public ResponseEntity<POSResponse> createPOS(
            @RequestBody CreatePOSRequest request, UriComponentsBuilder uriBuilder) {
        logger.info("endpoint createPos called with name: {}", request.name());

        PointOfSale created = posService.create(PointOfSale.from(request));
        //Construye la URL del recurso recién creado: /v1/point-of-sale/{id}
        URI location = uriBuilder
                .path("/v1/point-of-sale/{id}")
                .build(created.getId());

        return ResponseEntity.created(location).body(new POSResponse(created.getId(), created.getName()));
    }


    @PutMapping("/{id}")
    public ResponseEntity<UpdatePOSRequest> put(
            @PathVariable Long id,
            @RequestBody UpdatePOSRequest body) {
        logger.info("endpoint updatePos called with id: {} and new name: {}", id, body.name());
        try {
            PointOfSale updated = posService.updateNamePointOfSale(id, body.name());
            return ResponseEntity.ok(body);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {
        logger.info("endpoint deletePos called with id: {}", id);
        posService.delete(id);
        return ResponseEntity.ok("POS Deleted");
    }

}