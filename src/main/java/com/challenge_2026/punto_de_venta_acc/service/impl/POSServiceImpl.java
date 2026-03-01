package com.challenge_2026.punto_de_venta_acc.service.impl;

import com.challenge_2026.punto_de_venta_acc.dto.POSDto;
import com.challenge_2026.punto_de_venta_acc.mapper.ResponsePOSMapper;
import com.challenge_2026.punto_de_venta_acc.entity.PointOfSale;
import com.challenge_2026.punto_de_venta_acc.repository.POSRepository;
import com.challenge_2026.punto_de_venta_acc.service.PosService;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class POSServiceImpl implements PosService {
    private final POSRepository repo;

    public POSServiceImpl(POSRepository repo) {
        this.repo = repo;
    }

    @Cacheable(cacheNames = "posAllV2", key = "'all'")
    @Transactional(readOnly = true)
    public List<POSDto> findAll() {
        return repo.findAll().stream().map(ResponsePOSMapper::toEntity).toList();
    }

    @Transactional
    @CacheEvict( cacheNames = "posAllV2", key = "'all'")
    @CachePut(cacheNames = "posByIdV2", key = "#result.id")
    public PointOfSale create(PointOfSale pos) {
        return repo.save(pos);
    }

    @Transactional
    @CacheEvict(cacheNames = "posAllV2", key = "'all'")
    @CachePut(cacheNames = "posByIdV2", key = "#id")
    public PointOfSale updateNamePointOfSale(Long id, String newName) {

        PointOfSale pos = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Punto de Venta No Encontrado"));
        PointOfSale updated = pos.withName(newName);
        repo.save(updated);
        return updated;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict( cacheNames = "posAllV2", key = "'all'"),
            @CacheEvict( cacheNames = "posByIdV2", key = "#id")
    })
    public void delete(Long id) { repo.deleteById(id);}

}

