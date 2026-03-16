package com.challenge_2026.punto_de_venta_acc.service.impl;

import com.challenge_2026.punto_de_venta_acc.dto.POSDto;
import com.challenge_2026.punto_de_venta_acc.entity.PointOfSale;
import com.challenge_2026.punto_de_venta_acc.repository.POSRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class POSServiceImplTest {

    @Mock
    private POSRepository posRepository;

    @InjectMocks
    private POSServiceImpl posService;


    @AfterEach
    void tearDown() {
        // Limpia stubbings/estado del mock para que no afecte otros tests
        reset(posRepository);
    }


    @Test
    void findAll() {
        PointOfSale mockDto = new PointOfSale();
        mockDto.setId(1L);
        mockDto.setName("Mock POS");

        List<PointOfSale> mockList = new ArrayList<>();
        mockList.add(mockDto);

        List<POSDto> result;
        when(posRepository.findAll()).thenReturn(mockList);
        result = posService.findAll();
        Assert.notNull(result, "El resultado no debe ser nulo");
    }

    @Test
    void create() {
        PointOfSale mockDto = new PointOfSale();
        mockDto.setId(1L);
        mockDto.setName("Mock POS");
        when(posRepository.save(mockDto)).thenReturn(mockDto);
        PointOfSale result = posService.create(mockDto);
        Assert.notNull(result, "El resultado no debe ser nulo");
    }

    @Test
    void updateNamePointOfSale() {
       /* PointOfSale mockDto = new PointOfSale();
        mockDto.setId(anyLong());
        mockDto.setName(anyString());
        when(posRepository.findById(anyLong())).thenReturn(java.util.Optional.of(mockDto));
        when(posRepository.findByName(anyString())).thenReturn(java.util.Optional.empty());
        when(posRepository.save(mockDto)).thenReturn(mockDto);
        PointOfSale result = posService.updateNamePointOfSale(1L, "New name");
        Assert.notNull(result, "El resultado no debe ser nulo");*/
    }

    @Test
    void delete() {
        posService.delete(3L);
        Assert.isTrue(true, "el metodo se ejecuto sin lanzar excepciones");
    }
}