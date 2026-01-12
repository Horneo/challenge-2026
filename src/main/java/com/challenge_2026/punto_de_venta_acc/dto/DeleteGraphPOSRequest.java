package com.challenge_2026.punto_de_venta_acc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DeleteGraphPOSRequest(
        @NotBlank(message = "pointA es obligatorio y no puede estar vacío.")
        String pointA,

        @NotBlank(message = "pointB es obligatorio y no puede estar vacío.")
        String pointB
) {}
