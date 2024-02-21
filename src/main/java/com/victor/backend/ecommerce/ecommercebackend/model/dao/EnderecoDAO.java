package com.victor.backend.ecommerce.ecommercebackend.model.dao;


import com.victor.backend.ecommerce.ecommercebackend.model.Endereco;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface EnderecoDAO extends ListCrudRepository<Endereco, Long> {
    List<Endereco> findByUsuario_Id(Long id);

}
