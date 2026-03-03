package com.challenge_2026.punto_de_venta_acc.advice;


import com.challenge_2026.punto_de_venta_acc.exception.PuntoDeVentaNotFoundException;
import com.challenge_2026.punto_de_venta_acc.exception.UpdateNameAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.challenge_2026.punto_de_venta_acc.dto.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PuntoDeVentaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePDVNotFound(PuntoDeVentaNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("NOT_FOUND", "El POS no existe"));
    }

    @ExceptionHandler(UpdateNameAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUpdateAlreadyExist(UpdateNameAlreadyExistException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("NOT_FOUND", "Existe un POS con el mismo nombre"));
    }
}