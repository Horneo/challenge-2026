package com.challenge_2026.punto_de_venta_acc.service;

import com.challenge_2026.punto_de_venta_acc.model.PointOfSale;
import com.challenge_2026.punto_de_venta_acc.repository.POSRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class POSService {
    private final POSRepository repo;

    public POSService(POSRepository repo) {
        this.repo = repo;
    }

    @Cacheable(value = "pointOfSale")
    public List<PointOfSale> findAll() {
        return repo.findAll();
    }

    @CacheEvict( value = "pointOfSale", key = "#result.id")
    public PointOfSale create(PointOfSale pos) {
        return repo.save(pos);
    }

    @CacheEvict( value = "pointOfSale", key = "#result.id")
    public PointOfSale updateNamePointOfSale(String id, String newName) {

        PointOfSale pos = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Punto de Venta No Encontrado"));
        PointOfSale updated = pos.withName(newName);
        repo.save(updated);
        return updated;
    }

    @CacheEvict( value = "pointOfSale", key = "#id")
    public void delete(String id) { repo.deleteById(id);}

}

