package com.example.acreditaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record FundAccreditationDTO(
        @NotNull(message = "Debe ingresar un monto obligatoriamente y no puede estar vacío.")
        BigDecimal amount,
        @NotBlank(message = "Debe ingresar un id obligatoriamente y no puede estar vacío.")
        String idPointOfSale) {
}