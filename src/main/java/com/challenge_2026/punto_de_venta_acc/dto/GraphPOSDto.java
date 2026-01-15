package com.challenge_2026.punto_de_venta_acc.dto;

public class GraphPOSDto {
    private String originPOS;
    private String destinationPOS;
    private Integer cost;

    public GraphPOSDto() {}

    public GraphPOSDto(String originPOS, String destinationPOS, Integer cost) {
        this.originPOS = originPOS;
        this.destinationPOS = destinationPOS;
        this.cost = cost;
    }

    public String getOriginPOS() {
        return originPOS;
    }

    public void setOriginPOS(String originPOS) {
        this.originPOS = originPOS;
    }

    public String getDestinationPOS() {
        return destinationPOS;
    }

    public void setDestinationPOS(String destinationPOS) {
        this.destinationPOS = destinationPOS;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
