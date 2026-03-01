package com.challenge_2026.punto_de_venta_acc.entity;


import jakarta.persistence.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "graph_pos", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"origin_pos", "destination_pos"})
})
public class GraphPointOfSale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin_pos")
    private String originPointOfSale;

    @Column(name = "destination_pos")
    private String destinationPointOfSale;

    @Column(name = "cost")
    private Integer cost;

    // Constructor sin argumentos (necesario para Spring Data / frameworks)
    public GraphPointOfSale() {
    }

    // Constructor con todos los campos
    public GraphPointOfSale(Long id, String originPointOfSale, String destinationPointOfSale, Integer cost) {
        this.id = id;
        this.originPointOfSale = originPointOfSale;
        this.destinationPointOfSale = destinationPointOfSale;
        this.cost = cost;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginPointOfSale() {
        return originPointOfSale;
    }

    public void setOriginPointOfSale(String originPointOfSale) {
        this.originPointOfSale = originPointOfSale;
    }

    public String getDestinationPointOfSale() {
        return destinationPointOfSale;
    }

    public void setDestinationPointOfSale(String destinationPointOfSale) {
        this.destinationPointOfSale = destinationPointOfSale;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    // equals y hashCode (con todos los campos, o solo id si prefieres identidad por clave)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GraphPointOfSale)) return false;
        GraphPointOfSale that = (GraphPointOfSale) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(originPointOfSale, that.originPointOfSale) &&
                Objects.equals(destinationPointOfSale, that.destinationPointOfSale) &&
                Objects.equals(cost, that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originPointOfSale, destinationPointOfSale, cost);
    }

    @Override
    public String toString() {
        return "GraphPointOfSale{" +
                "id=" + id +
                ", originPointOfSale='" + originPointOfSale + '\'' +
                ", destinationPointOfSale='" + destinationPointOfSale + '\'' +
                ", cost=" + cost +
                '}';
    }
}
