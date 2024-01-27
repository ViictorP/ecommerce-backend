package com.victor.backend.ecommerce.ecommercebackend.model.dao;

import com.victor.backend.ecommerce.ecommercebackend.model.Pedido;
import com.victor.backend.ecommerce.ecommercebackend.model.UsuarioLocal;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface PedidoDAO extends ListCrudRepository<Pedido, Long> {
    List<Pedido> findByUsuario(UsuarioLocal usuario);
}
