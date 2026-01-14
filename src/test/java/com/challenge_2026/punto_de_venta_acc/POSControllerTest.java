package com.challenge_2026.punto_de_venta_acc;

import com.challenge_2026.punto_de_venta_acc.controller.POSController;
import com.challenge_2026.punto_de_venta_acc.dto.CreatePOSRequest;
import com.challenge_2026.punto_de_venta_acc.dto.POSDto;
import com.challenge_2026.punto_de_venta_acc.dto.POSResponse;
import com.challenge_2026.punto_de_venta_acc.dto.UpdatePOSRequest;
import com.challenge_2026.punto_de_venta_acc.model.PointOfSale;
import com.challenge_2026.punto_de_venta_acc.service.POSService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import static org.springframework.http.HttpStatus.*;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class POSControllerTest {

    private POSService posService;
    private POSController controller;
    private UriComponentsBuilder uriBuilder;

    @BeforeEach
    void setUp() {
        posService = mock(POSService.class);
        controller = new POSController(posService);
        uriBuilder = UriComponentsBuilder.fromPath("");
    }

    @Test
    void findAllPOS_devuelveListaVacia() {
        when(posService.findAll()).thenReturn(List.of());
        List<POSDto> result = controller.findAllPOS();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(posService).findAll();
        verifyNoMoreInteractions(posService);
    }

    @Test
    void createPOS_devuelve201YLocationConId() {
        // Arrange
        CreatePOSRequest req = mock(CreatePOSRequest.class);
        PointOfSale created = mock(PointOfSale.class);
        when(created.id()).thenReturn(String.valueOf(123L));
        when(posService.create(any(PointOfSale.class))).thenReturn(created);

        // Act
        ResponseEntity<POSResponse> response = controller.createPOS(req, uriBuilder);

        // Assert
        assertEquals(CREATED, response.getStatusCode());
        URI location = response.getHeaders().getLocation();
        assertNotNull(location);
        // La URL esperada es /v1/point-of-sale/{id}
        assertTrue(location.toString().endsWith("/v1/point-of-sale/123"),
                "Location debería terminar en /v1/point-of-sale/123 pero fue: " + location);
        assertNull(response.getBody(), "El body debe ser null según la implementación actual");
        verify(posService).create(any(PointOfSale.class));
        verifyNoMoreInteractions(posService);
    }

    @Test
    void put_actualizaNombre_devuelve200ConBody() {
        // Arrange
        UpdatePOSRequest body = mock(UpdatePOSRequest.class);
        when(body.name()).thenReturn("Nuevo Nombre");
        when(posService.updateNamePointOfSale("5", "Nuevo Nombre")).thenReturn(mock(PointOfSale.class));

        // Act
        ResponseEntity<UpdatePOSRequest> response = controller.put(5L, body);

        // Assert
        assertEquals(OK, response.getStatusCode());
        assertSame(body, response.getBody(), "El body devuelto debe ser el mismo UpdatePOSRequest");
        verify(posService).updateNamePointOfSale("5", "Nuevo Nombre");
        verifyNoMoreInteractions(posService);
    }

    @Test
    void put_cuandoNoExiste_devuelve404() {
        // Arrange
        UpdatePOSRequest body = mock(UpdatePOSRequest.class);
        when(body.name()).thenReturn("Nombre");
        when(posService.updateNamePointOfSale(eq("7"), any()))
                .thenThrow(new NoSuchElementException("No existe"));

        // Act
        ResponseEntity<UpdatePOSRequest> response = controller.put(7L, body);

        // Assert
        assertEquals(NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(posService).updateNamePointOfSale(eq("7"), any());
        verifyNoMoreInteractions(posService);
    }

    @Test
    void delete_elimina_devuelve200ConMensaje() {
        // Act
        ResponseEntity<String> response = controller.delete(9L);

        // Assert
        assertEquals(OK, response.getStatusCode());
        assertEquals("POS Deleted", response.getBody());
        verify(posService).delete("9");
        verifyNoMoreInteractions(posService);
    }
}

