package com.challenge_2026.punto_de_venta_acc.dto;

public class MinimumGraphPOSDto {
    private String originPOS;
    private String destionationPOS;

    private Integer minimumCost;

    public MinimumGraphPOSDto() {}

    public MinimumGraphPOSDto(String originPOS, String destinationPOS, Integer minimumCost) {
        this.originPOS = originPOS;
        this.destionationPOS = destinationPOS;
        this.minimumCost = minimumCost;
    }

    public String getOriginPOS() {
        return originPOS;
    }

    public void setOriginPOS(String originPOS) {
        this.originPOS = originPOS;
    }

    public String getDestionationPOS() {
        return destionationPOS;
    }

    public void setDestionationPOS(String destionationPOS) {
        this.destionationPOS = destionationPOS;
    }

    public Integer getMinimumCost() {
        return minimumCost;
    }

    public void setMinimumCost(Integer minimumCost) {
        this.minimumCost = minimumCost;
    }

}
