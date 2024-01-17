package com.victor.backend.ecommerce.ecommercebackend;

import jakarta.persistence.*;

@Entity
@Table(name = "teste")
public class Teste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}