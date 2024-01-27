package com.victor.backend.ecommerce.ecommercebackend.service;

import com.victor.backend.ecommerce.ecommercebackend.model.Pedido;
import com.victor.backend.ecommerce.ecommercebackend.model.UsuarioLocal;
import com.victor.backend.ecommerce.ecommercebackend.model.dao.PedidoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoDAO pedidoDAO;

    public List<Pedido> getPedidos(UsuarioLocal usuario) {
        return pedidoDAO.findByUsuario(usuario);
    }
}
