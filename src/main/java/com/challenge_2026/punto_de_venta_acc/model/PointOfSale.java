package com.challenge_2026.punto_de_venta_acc.model;

import com.challenge_2026.punto_de_venta_acc.dto.CreatePOSRequest;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "pos")
public class PointOfSale {

    @Id
    private String id;

    private String name;

    // Constructor sin argumentos (requerido por algunos frameworks)
    public PointOfSale() {
    }

    // Constructor con todos los campos
    public PointOfSale(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Factory method equivalente al record
    public static PointOfSale from(CreatePOSRequest dto) {
        return new PointOfSale(dto.id(), dto.name());
    }

    /**
     * "Wither": crea una nueva instancia con name actualizado.
     * Mantiene la semántica inmutable de tu record original para esta operación.
     */
    public PointOfSale withName(String newName) {
        return new PointOfSale(this.id, newName);
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {  // si preferís inmutabilidad, podés remover este setter
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { // el wither ya crea una copia; este setter es opcional
        this.name = name;
    }

    // equals y hashCode: si tu identidad es solo por id, podés simplificarlo
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PointOfSale)) return false;
        PointOfSale that = (PointOfSale) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "PointOfSale{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

