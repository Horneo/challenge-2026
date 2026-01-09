package com.challenge_2026.punto_de_venta_acc.controller;

import com.challenge_2026.punto_de_venta_acc.dto.*;
import com.challenge_2026.punto_de_venta_acc.mapper.ResponseGraphPOSMapper;
import com.challenge_2026.punto_de_venta_acc.service.GraphPOSService;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/point-of-sale-cost") // Prefijo para todos los endpoints de este controlle
public class PointOfSaleCostController {

    private final GraphPOSService graphPosService;

    // Spring inyecta automáticamente el bean POSService
    public PointOfSaleCostController(GraphPOSService posService) {
        this.graphPosService = posService;
    }

        @PostMapping("/create")
        public ResponseEntity<GraphPOSResponse> createPOS(@RequestBody @Valid CreateGraphPOSRequest body, UriComponentsBuilder uriBuilder) {

            graphPosService.create(ResponseGraphPOSMapper.toEntity(body));
            URI location = uriBuilder
                    .path("/v1/point-of-sale-cost")
                    .build("");

            StringBuilder sb = buildMessageResponse(body);
            GraphPOSResponse response = new GraphPOSResponse(sb.toString());
            return ResponseEntity.created(location).body(response);
        }

    private static @NonNull StringBuilder buildMessageResponse(CreateGraphPOSRequest body) {
        StringBuilder sb = new StringBuilder();
        sb.append("Se creo el camino entre");
        sb.append(body.pointA());
        sb.append(" y ");
        sb.append(body.pointB());
        sb.append("con un costo de: ");
        sb.append(body.cost());
        return sb;
    }
}