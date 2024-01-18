package com.victor.backend.ecommerce.ecommercebackend.model.dao;

import com.victor.backend.ecommerce.ecommercebackend.model.UsuarioLocal;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioLocalDAO extends CrudRepository<UsuarioLocal, Long> {
    Optional<UsuarioLocal> findByUsernameIgnoreCase(String username);

    Optional<UsuarioLocal> findByEmailIgnoreCase(String email);
}
