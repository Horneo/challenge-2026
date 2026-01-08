package com.challenge_2026.punto_de_venta_acc.dto;

public class POSDto {
    private String id;
    private String name;

    public POSDto() {}

    public POSDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
