package com.challenge_2026.punto_de_venta_acc.dto;

public class MinimumGraphPOSDto {
    private String originPOS;
    private String destinationPOS;

    private Integer minimumCost;

    public MinimumGraphPOSDto() {}

    public MinimumGraphPOSDto(String originPOS, String destinationPOS, Integer minimumCost) {
        this.originPOS = originPOS;
        this.destinationPOS = destinationPOS;
        this.minimumCost = minimumCost;
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

    public Integer getMinimumCost() {
        return minimumCost;
    }

    public void setMinimumCost(Integer minimumCost) {
        this.minimumCost = minimumCost;
    }

}
