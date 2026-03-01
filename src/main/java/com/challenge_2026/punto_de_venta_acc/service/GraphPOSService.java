package com.challenge_2026.punto_de_venta_acc.service;

import com.challenge_2026.punto_de_venta_acc.dto.GraphPOSDto;
import com.challenge_2026.punto_de_venta_acc.dto.MinimumGraphPOSDto;
import com.challenge_2026.punto_de_venta_acc.entity.GraphPointOfSale;

import java.util.List;

public interface GraphPOSService {

    public List<GraphPOSDto> findAll();

    public GraphPointOfSale create(GraphPointOfSale pos);

    public List<MinimumGraphPOSDto> showMinimumRoutes();

    public void delete(String pointA, String pointB);
}
