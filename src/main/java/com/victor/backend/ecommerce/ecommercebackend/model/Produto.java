package com.victor.backend.ecommerce.ecommercebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Column(name = "descricao_curta", nullable = false)
    private String descricaoCurta;

    @Column(name = "descricao_longa")
    private String descricaoLonga;

    @Column(name = "preco", nullable = false)
    private Double preco;

    @OneToOne(mappedBy = "produto", cascade = CascadeType.REMOVE, optional = false, orphanRemoval = true)
    private Inventario inventario;

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDescricaoLonga() {
        return descricaoLonga;
    }

    public void setDescricaoLonga(String descricaoLonga) {
        this.descricaoLonga = descricaoLonga;
    }

    public String getDescricaoCurta() {
        return descricaoCurta;
    }

    public void setDescricaoCurta(String descricaoCurta) {
        this.descricaoCurta = descricaoCurta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}