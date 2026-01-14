package com.challenge_2026.punto_de_venta_acc.service;

import com.challenge_2026.punto_de_venta_acc.dto.GraphPOSDto;
import com.challenge_2026.punto_de_venta_acc.dto.MinimumGraphPOSDto;
import com.challenge_2026.punto_de_venta_acc.dto.POSDto;
import com.challenge_2026.punto_de_venta_acc.mapper.ResponseGraphPOSMapper;
import com.challenge_2026.punto_de_venta_acc.mapper.ResponseMinimumGraphPOSMapper;
import com.challenge_2026.punto_de_venta_acc.model.GraphPointOfSale;
import com.challenge_2026.punto_de_venta_acc.repository.GraphPOSRepository;
import com.challenge_2026.punto_de_venta_acc.repository.GraphPointOfSaleAggRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GraphPOSService {
    private final GraphPOSRepository repo;

    private final GraphPointOfSaleAggRepository minimumRoutesRepo;

    private final POSService posService;

    public GraphPOSService(GraphPOSRepository repo, GraphPointOfSaleAggRepository minimumRoutesRepo, POSService posService) {
        this.repo = repo;
        this.minimumRoutesRepo = minimumRoutesRepo;
        this.posService = posService;
    }

    @Cacheable(value = "graphPointOfSale", key = "'all'")
    public List<GraphPOSDto> findAll() {
        return repo.findAll().stream().map(ResponseGraphPOSMapper::toDto).toList();
    }

    @CacheEvict( value = "graphPointOfSale", key = "#result.id")
    public GraphPointOfSale create(GraphPointOfSale pos) {
        return repo.save(pos);
    }


    @Cacheable(value = "graphPointOfSaleMinimumRoutes", key = "'allMinimumRoutes'")
    public List<MinimumGraphPOSDto> showMinimumRoutes() {
        // 1) Obtengo rutas mínimas (IDs)
        List<MinimumGraphPOSDto> routes = minimumRoutesRepo.findMinCostPerOriginAndDestination()
                .stream()
                .map(ResponseMinimumGraphPOSMapper::toDto)
                .toList();

        if (routes.isEmpty()) {
            return routes;
        }

        // 2) Cargo POS una sola vez y armo mapa id -> nombre
        List<POSDto> allPos = posService.findAll(); // cacheado por @Cacheable en POSService
        Map<String, String> posNameById = allPos.stream()
                .collect(Collectors.toMap(POSDto::getId, POSDto::getName, (a, b) -> a)); // merge function defensiva

        // 3) Construyo nuevos DTOs con nombres (si existen en el mapa)
        return routes.stream()
                .map(r -> {
                    String originId = r.getOriginPOS();
                    String destId   = r.getDestionationPOS(); // <-- corregir a getDestinationPOS cuando arregles el DTO

                    String originName = posNameById.get(originId);
                    String destName   = posNameById.get(destId);

                    MinimumGraphPOSDto out = new MinimumGraphPOSDto();
                    out.setOriginPOS(originName != null ? originName : originId);
                    out.setDestionationPOS(destName != null ? destName : destId);
                    out.setMinimumCost(r.getMinimumCost());
                    return out;
                })
                .toList();
    }

    @CacheEvict( value = "pointOfSale", key = "#pointA + '-' + #pointB")
    public void delete(String pointA, String pointB) { repo.deleteByOriginPointOfSaleAndDestinationPointOfSale(pointA, pointB);}

}

