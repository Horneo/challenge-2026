package com.challenge_2026.punto_de_venta_acc.dto;

/**
 * Respuesta al crear un Punto de Venta.
 * Incluye un mensaje y los datos del POS creado.
 */
public record POSResponse(
        String id,
        String name
) {
    public static POSResponse created(String id, String name, String location) {
        return new POSResponse(id, name);
    }
}

