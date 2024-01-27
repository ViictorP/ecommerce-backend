package com.victor.backend.ecommerce.ecommercebackend.service;

import com.victor.backend.ecommerce.ecommercebackend.model.Produto;
import com.victor.backend.ecommerce.ecommercebackend.model.dao.ProdutoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoDAO produtoDAO;

    public List<Produto> getProdutos() {
        return produtoDAO.findAll();
    }
}
