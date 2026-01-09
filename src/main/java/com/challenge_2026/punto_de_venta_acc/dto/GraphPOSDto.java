package com.challenge_2026.punto_de_venta_acc.dto;

public class GraphPOSDto {
    private String originPOS;
    private String destionationPOS;
    private Integer cost;

    public GraphPOSDto() {}

    public GraphPOSDto(String originPOS, String destinationPOS, Integer cost) {
        this.originPOS = originPOS;
        this.destionationPOS = destinationPOS;
        this.cost = cost;
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

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
