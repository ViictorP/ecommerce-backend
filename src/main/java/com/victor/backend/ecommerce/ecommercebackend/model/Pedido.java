package com.victor.backend.ecommerce.ecommercebackend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioLocal usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PedidoQuantidades> pedidoQuantidades = new ArrayList<>();

    public List<PedidoQuantidades> getPedidoQuantidades() {
        return pedidoQuantidades;
    }

    public void setPedidoQuantidades(List<PedidoQuantidades> pedidoQuantidades) {
        this.pedidoQuantidades = pedidoQuantidades;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public UsuarioLocal getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioLocal usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}