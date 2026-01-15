package com.challenge_2026.punto_de_venta_acc.dto;

public record GraphPOSResponse(String message) {

    public static GraphPOSResponse createMessage(String pointA, String pointB, int cost) {
        return new GraphPOSResponse("Se creó el camino entre " + pointA + " y " + pointB + " con un costo de: " + cost);
    }

    public static GraphPOSResponse removeMessage(String pointA, String pointB) {
        return new GraphPOSResponse("Se removió con éxito el camino entre " + pointA + " y " + pointB);
    }
}
