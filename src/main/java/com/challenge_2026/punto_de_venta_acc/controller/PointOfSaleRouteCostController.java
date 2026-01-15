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
import java.util.List;

import static com.challenge_2026.punto_de_venta_acc.dto.GraphPOSResponse.createMessage;
import static com.challenge_2026.punto_de_venta_acc.dto.GraphPOSResponse.removeMessage;

@RestController
@RequestMapping("/v1/point-of-sale-cost") // Prefijo para todos los endpoints de este controlle
public class PointOfSaleRouteCostController {

    private final GraphPOSService graphPosService;

    // Spring inyecta automáticamente el bean POSService
    public PointOfSaleRouteCostController(GraphPOSService posService) {
        this.graphPosService = posService;
    }

        @PostMapping("/create")
        public ResponseEntity<GraphPOSResponse> createRoute(@RequestBody @Valid CreateGraphPOSRequest body, UriComponentsBuilder uriBuilder) {

            graphPosService.create(ResponseGraphPOSMapper.toEntity(body));
            URI location = uriBuilder
                    .path("/v1/point-of-sale-cost")
                    .build("");

            return ResponseEntity.created(location).body(createMessage(body.pointA(), body.pointB(),body.cost()));
        }

    @DeleteMapping("/remove")
    public ResponseEntity<GraphPOSResponse> removeRoute(@RequestBody @Valid DeleteGraphPOSRequest body, UriComponentsBuilder uriBuilder) {

        graphPosService.delete(body.pointA(), body.pointB());

        URI location = uriBuilder
                .path("/v1/point-of-sale-cost/remove")
                .build("");


        return ResponseEntity.created(location).body(removeMessage(body.pointA(), body.pointB()));
    }

    @GetMapping("/findAll")
    public List<GraphPOSDto> findAllGraphPOS() {
        return graphPosService.findAll();
    }

    @GetMapping("/showMinimumRoutes")
    public List<MinimumGraphPOSDto> showMinimumRoutes() {
        return graphPosService.showMinimumRoutes();
    }
}