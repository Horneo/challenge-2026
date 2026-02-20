package com.example.acreditaciones.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "fund_accreditation_pos")
public class FundAccreditationPOS {

    @Id
    @SequenceGenerator(
            name = "fund_pos_seq",
            sequenceName = "FUND_ACCREDITATION_POS_SEQ",
            allocationSize = 1 // importante en H2 para evitar saltos
    )
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "fund_pos_seq")
    private Long id;

    @Column(name="received_at")
    private Instant receivedAt;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "id_point_of_sale")
    private String idPointOfSale;

    @Column(name = "name_point_of_sale")
    private String namePointOfSale;

    public FundAccreditationPOS() {

    }

    public FundAccreditationPOS(Instant receivedAt, BigDecimal amount, String idPointOfSale, String namePointOfSale) {
        this.receivedAt = receivedAt;
        this.amount = amount;
        this.idPointOfSale = idPointOfSale;
        this.namePointOfSale = namePointOfSale;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Instant receivedAt) {
        this.receivedAt = receivedAt;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getIdPointOfSale() {
        return idPointOfSale;
    }

    public void setIdPointOfSale(String idPointOfSale) {
        this.idPointOfSale = idPointOfSale;
    }

    public String getNamePointOfSale() {
        return namePointOfSale;
    }

    public void setNamePointOfSale(String namePointOfSale) {
        this.namePointOfSale = namePointOfSale;
    }
}
