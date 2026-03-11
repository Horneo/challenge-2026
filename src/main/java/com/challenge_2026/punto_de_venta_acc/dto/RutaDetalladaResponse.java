package com.challenge_2026.punto_de_venta_acc.dto;

import java.io.Serializable;
import java.util.List;

public record RutaDetalladaResponse(
        String inicio,
        String fin,
        List<String> caminoIda,
        List<String> caminoVuelta,
        double costoTotal,
        String mensaje
) implements Serializable {
}
