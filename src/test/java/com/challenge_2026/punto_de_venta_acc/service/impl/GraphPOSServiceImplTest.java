package com.challenge_2026.punto_de_venta_acc.service.impl;

import com.challenge_2026.punto_de_venta_acc.entity.GraphPointOfSale;
import com.challenge_2026.punto_de_venta_acc.repository.GraphPOSRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GraphPOSServiceImplTest {

    @Mock
    private GraphPOSRepository repo;

    @InjectMocks
    private GraphPOSServiceImpl graphPOSService;

    @Test
    void findAll() {
        when(repo.findAll()).thenReturn(List.of());
        var result = graphPOSService.findAll();
        assertNotNull(result, "El resultado no debe ser nulo");
    }

    @Test
    void create() {
        when(repo.save(Mockito.any())).thenReturn(new  GraphPointOfSale());
        var result = graphPOSService.create(new GraphPointOfSale());
        assertNotNull(result, "El resultado no debe ser nulo");
    }

    @Test
    void calculateMinimumRoutesWithDijstra() {
        when(repo.findAll()).thenReturn(List.of());
        assertThrows(RuntimeException.class , () -> graphPOSService.calculateMinimumRoutesWithDijstra("A", "B"), "Se esperaba una RuntimeException al no encontrar los puntos de venta en la base de datos");
    }

    @Test
    void delete() {
         doNothing().when(repo).deleteByOriginPointOfSaleAndDestinationPointOfSale(anyString(), anyString());
         graphPOSService.delete("A", "B");
    }

    @Test
    void ejecutarDijkstra() {

    }
}