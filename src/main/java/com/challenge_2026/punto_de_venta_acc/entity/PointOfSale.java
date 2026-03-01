package com.challenge_2026.punto_de_venta_acc.entity;

import com.challenge_2026.punto_de_venta_acc.dto.CreatePOSRequest;
import jakarta.persistence.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "point_of_sale")
public class PointOfSale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    // Constructor sin argumentos (requerido por algunos frameworks)
    public PointOfSale() {
    }

    // Constructor con todos los campos
    public PointOfSale(Long id, String name) {
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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {  // si preferís inmutabilidad, podés remover este setter
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

