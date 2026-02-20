package com.challenge_2026.punto_de_venta_acc.service;

import com.challenge_2026.punto_de_venta_acc.dto.POSDto;
import com.challenge_2026.punto_de_venta_acc.model.PointOfSale;

import java.util.List;

public interface PosService {

    public List<POSDto> findAll();

    public PointOfSale create(PointOfSale pos);

    public PointOfSale updateNamePointOfSale(String id, String newName);

    public void delete(String id);
}
