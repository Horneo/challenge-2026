package com.challenge_2026.punto_de_venta_acc.service.impl;

import com.challenge_2026.punto_de_venta_acc.dto.GraphPOSDto;
import com.challenge_2026.punto_de_venta_acc.mapper.ResponseGraphPOSMapper;
import com.challenge_2026.punto_de_venta_acc.entity.GraphPointOfSale;
import com.challenge_2026.punto_de_venta_acc.repository.GraphPOSRepository;
import com.challenge_2026.punto_de_venta_acc.service.GraphPOSService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GraphPOSServiceImpl implements GraphPOSService {
    private final GraphPOSRepository repo;

    private final POSServiceImpl posService;

    public GraphPOSServiceImpl(GraphPOSRepository repo, POSServiceImpl posService) {
        this.repo = repo;
        this.posService = posService;
    }

    @Cacheable( cacheNames = "graphPDVAll", key = "'all'")
    @Transactional(readOnly = true)
    public List<GraphPOSDto> findAll() {
        return repo.findAll().stream().map(ResponseGraphPOSMapper::toDto).toList();
    }

    @Transactional
    @CacheEvict( cacheNames = "graphPDVAll", key = "'all'")
    @CachePut( cacheNames = "graphPDVById", key = "#result.id")
    public GraphPointOfSale create(GraphPointOfSale pos) {
        return repo.save(pos);
    }


    @Cacheable( value = "rutas", key = "{#inicio, #fin}")
    public List<String> calculateMinimumRoutesWithDijstra(String inicio, String fin) {

        List<String> caminoMinimo = new LinkedList<>();

        List<GraphPointOfSale> routes = repo.findAll();

        Set<String> nodos = routes.stream().flatMap(c -> Stream.of(c.getOriginPointOfSale(), c.getDestinationPointOfSale()))
                .collect(Collectors.toSet());

        Map<String, Double> distancias = new HashMap<>();
        Map<String, String> anteriores = new HashMap<>();
        PriorityQueue<String> cola = new PriorityQueue<>(Comparator.comparing(distancias::get));

        nodos.forEach(n -> distancias.put(n, Double.MAX_VALUE));
        distancias.put(inicio, 0.0);
        cola.add(inicio);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            if (actual.equals(fin)) break;

            for (GraphPointOfSale ruta : routes.stream().filter(con ->
                    con.getOriginPointOfSale().equals(actual)).toList()) {
                Double nuevaDistancia = distancias.get(actual) + ruta.getCost();
                if (nuevaDistancia < distancias.get(ruta.getDestinationPointOfSale())) {
                    distancias.put(ruta.getDestinationPointOfSale(), nuevaDistancia);
                    anteriores.put(ruta.getDestinationPointOfSale(), actual);
                    cola.add(ruta.getDestinationPointOfSale());
                }
            }
        }

            for (String at = fin; at != null; at = anteriores.get(at)) caminoMinimo.add(0, at);

            return caminoMinimo;
        }

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict( cacheNames = "graphPDVAll", key = "'all'"),
                    @CacheEvict( cacheNames = "minimumRoutesAll", key = "'all'")
            }
    )
    public void delete(String pointA, String pointB) { repo.deleteByOriginPointOfSaleAndDestinationPointOfSale(pointA, pointB);}

}

