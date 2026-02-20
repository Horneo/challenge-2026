package com.challenge_2026.punto_de_venta_acc.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Objects;

@Document(collection = "GraphPointOfSale")
public class GraphPointOfSale {

    @Id
    private ObjectId id;

    private String originPointOfSale;
    private String destinationPointOfSale;
    private Integer cost;

    // Constructor sin argumentos (necesario para Spring Data / frameworks)
    public GraphPointOfSale() {
    }

    // Constructor con todos los campos
    public GraphPointOfSale(ObjectId id, String originPointOfSale, String destinationPointOfSale, Integer cost) {
        this.id = id;
        this.originPointOfSale = originPointOfSale;
        this.destinationPointOfSale = destinationPointOfSale;
        this.cost = cost;
    }

    // Getters y Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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
