package com.challenge_2026.punto_de_venta_acc.dto;

/**
 * Respuesta al crear un Punto de Venta.
 * Incluye un mensaje y los datos del POS creado.
 */
public record POSResponse(
        Long id,
        String name,
        String location,
        String message
) {
    public static POSResponse created(Long id, String name, String location) {
        return new POSResponse(id, name, location, "Punto de venta creado exitosamente");
    }
}

