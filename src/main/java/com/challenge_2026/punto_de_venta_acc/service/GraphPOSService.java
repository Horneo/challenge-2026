package com.challenge_2026.punto_de_venta_acc.service;

import com.challenge_2026.punto_de_venta_acc.dto.GraphPOSDto;
import com.challenge_2026.punto_de_venta_acc.dto.POSDto;
import com.challenge_2026.punto_de_venta_acc.mapper.ResponseGraphPOSMapper;
import com.challenge_2026.punto_de_venta_acc.mapper.ResponsePOSMapper;
import com.challenge_2026.punto_de_venta_acc.model.GraphPointOfSale;
import com.challenge_2026.punto_de_venta_acc.model.PointOfSale;
import com.challenge_2026.punto_de_venta_acc.repository.GraphPOSRepository;
import com.challenge_2026.punto_de_venta_acc.repository.POSRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GraphPOSService {
    private final GraphPOSRepository repo;

    public GraphPOSService(GraphPOSRepository repo) {
        this.repo = repo;
    }

    @Cacheable(value = "graphPointOfSale", key = "'all'")
    public List<GraphPOSDto> findAll() {
        return repo.findAll().stream().map(ResponseGraphPOSMapper::toDto).toList();
    }

    @CacheEvict( value = "graphPointOfSale", key = "#result.id")
    public GraphPointOfSale create(GraphPointOfSale pos) {
        return repo.save(pos);
    }

    //@CacheEvict( value = "pointOfSale", key = "#result.id")
    //public PointOfSale updateNamePointOfSale(String id, String newName) {

      //  PointOfSale pos = repo.findById(id)
        //        .orElseThrow(() -> new RuntimeException("Punto de Venta No Encontrado"));
        //PointOfSale updated = pos.withName(newName);
        //repo.save(updated);
        //return updated;
    //}

    @CacheEvict( value = "pointOfSale", key = "#pointA + '-' + #pointB")
    public void delete(String pointA, String pointB) { repo.deleteByOriginPointOfSaleAndDestinationPointOfSale(pointA, pointB);}

}

