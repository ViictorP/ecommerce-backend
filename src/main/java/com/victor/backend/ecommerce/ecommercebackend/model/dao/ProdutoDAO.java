package com.victor.backend.ecommerce.ecommercebackend.model.dao;

import com.victor.backend.ecommerce.ecommercebackend.model.Produto;
import org.springframework.data.repository.ListCrudRepository;

public interface ProdutoDAO extends ListCrudRepository<Produto, Long> {
}
