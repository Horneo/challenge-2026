package com.challenge_2026.punto_de_venta_acc;

import com.challenge_2026.punto_de_venta_acc.controller.PointOfSaleRouteCostController;
import com.challenge_2026.punto_de_venta_acc.dto.CreateGraphPOSRequest;
import com.challenge_2026.punto_de_venta_acc.dto.DeleteGraphPOSRequest;
import com.challenge_2026.punto_de_venta_acc.dto.GraphPOSDto;
import com.challenge_2026.punto_de_venta_acc.dto.GraphPOSResponse;
import com.challenge_2026.punto_de_venta_acc.dto.MinimumGraphPOSDto;
import com.challenge_2026.punto_de_venta_acc.service.GraphPOSService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

class PointOfSaleRouteCostControllerTest {

    private GraphPOSService graphPOSService;
    private PointOfSaleRouteCostController controller;
    private UriComponentsBuilder uriBuilder;

    @BeforeEach
    void setUp() {
        graphPOSService = mock(GraphPOSService.class);
        controller = new PointOfSaleRouteCostController(graphPOSService);
        uriBuilder = UriComponentsBuilder.fromPath("");
    }

    @Test
    void createRoute_devuelve201ConLocationYMensajeEsperado() {
        // Arrange
        CreateGraphPOSRequest body = mock(CreateGraphPOSRequest.class);
        when(body.pointA()).thenReturn("A");
        when(body.pointB()).thenReturn("B");
        when(body.cost()).thenReturn(10);
        // graphPOSService.create(...) es void; no hace falta stub adicional

        // Act
        ResponseEntity<GraphPOSResponse> response = controller.createRoute(body, uriBuilder);

        // Assert
        assertEquals(CREATED, response.getStatusCode());
        URI location = response.getHeaders().getLocation();
        assertNotNull(location);
        assertTrue(location.toString().endsWith("/v1/point-of-sale-cost"),
                "Location debería terminar en /v1/point-of-sale-cost pero fue: " + location);

        GraphPOSResponse respBody = response.getBody();
        assertNotNull(respBody);
        // IMPORTANTE: el método buildMessageResponse concatena sin espacios entre "entre" y A,
        // y sin espacio antes de "con un costo de" según el código actual.
        String esperado = "Se creo el camino entre" + "A" + " y " + "B" + "con un costo de: " + 10;
        // Si GraphPOSResponse es un record con accessor message():
        try {
            // Preferimos llamar respBody.message() si existe
            String actual = (String) respBody.getClass().getMethod("message").invoke(respBody);
            assertEquals(esperado, actual);
        } catch (ReflectiveOperationException e) {
            // Si no existe message(), probablemente haya getMessage():
            try {
                String actual = (String) respBody.getClass().getMethod("getMessage").invoke(respBody);
                assertEquals(esperado, actual);
            } catch (ReflectiveOperationException ex) {
                fail("No se encontró message() ni getMessage() en GraphPOSResponse para validar el contenido");
            }
        }

        verify(graphPOSService).create(any());
        verifyNoMoreInteractions(graphPOSService);
    }

    @Test
    void removeRoute_devuelve201ConLocationYMensaje() {
        // Arrange
        DeleteGraphPOSRequest body = mock(DeleteGraphPOSRequest.class);
        when(body.pointA()).thenReturn("A");
        when(body.pointB()).thenReturn("B");

        // Act
        ResponseEntity<GraphPOSResponse> response = controller.removeRoute(body, uriBuilder);

        // Assert
        assertEquals(CREATED, response.getStatusCode());
        URI location = response.getHeaders().getLocation();
        assertNotNull(location);
        assertTrue(location.toString().endsWith("/v1/point-of-sale-cost/remove"),
                "Location debería terminar en /v1/point-of-sale-cost/remove pero fue: " + location);

        GraphPOSResponse respBody = response.getBody();
        assertNotNull(respBody);
        String esperado = "Se removio con exito el camino entre " + "A" + " y " + "B";
        try {
            String actual = (String) respBody.getClass().getMethod("message").invoke(respBody);
            assertEquals(esperado, actual);
        } catch (ReflectiveOperationException e) {
            try {
                String actual = (String) respBody.getClass().getMethod("getMessage").invoke(respBody);
                assertEquals(esperado, actual);
            } catch (ReflectiveOperationException ex) {
                fail("No se encontró message() ni getMessage() en GraphPOSResponse para validar el contenido");
            }
        }

        verify(graphPOSService).delete("A", "B");
        verifyNoMoreInteractions(graphPOSService);
    }

    @Test
    void findAllGraphPOS_devuelveListaVacia() {
        when(graphPOSService.findAll()).thenReturn(List.of());

        List<GraphPOSDto> result = controller.findAllGraphPOS();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(graphPOSService).findAll();
        verifyNoMoreInteractions(graphPOSService);
    }

    @Test
    void showMinimumRoutes_devuelveListaVacia() {
        when(graphPOSService.showMinimumRoutes()).thenReturn(List.of());

        List<MinimumGraphPOSDto> result = controller.showMinimumRoutes();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(graphPOSService).showMinimumRoutes();
        verifyNoMoreInteractions(graphPOSService);
    }
}

