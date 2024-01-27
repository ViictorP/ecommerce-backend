package com.victor.backend.ecommerce.ecommercebackend.api.controller.pedido;

import com.victor.backend.ecommerce.ecommercebackend.model.Pedido;
import com.victor.backend.ecommerce.ecommercebackend.model.UsuarioLocal;
import com.victor.backend.ecommerce.ecommercebackend.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> getPedidos(@AuthenticationPrincipal UsuarioLocal usuario) {
        return pedidoService.getPedidos(usuario);
    }
}
