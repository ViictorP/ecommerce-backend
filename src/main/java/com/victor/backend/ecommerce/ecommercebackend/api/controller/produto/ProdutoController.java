package com.victor.backend.ecommerce.ecommercebackend.api.controller.produto;

import com.victor.backend.ecommerce.ecommercebackend.model.Produto;
import com.victor.backend.ecommerce.ecommercebackend.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Produto> getProdutos() {
        return produtoService.getProdutos();
    }
}
