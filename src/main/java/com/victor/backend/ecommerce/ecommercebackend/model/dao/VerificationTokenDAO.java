package com.victor.backend.ecommerce.ecommercebackend.model.dao;

import com.victor.backend.ecommerce.ecommercebackend.model.UsuarioLocal;
import com.victor.backend.ecommerce.ecommercebackend.model.VerificationToken;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface VerificationTokenDAO extends ListCrudRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);

    void deleteByUsuario(UsuarioLocal usuario);

    List<VerificationToken> findByUsuario_IdOrderByUsuario_IdDesc(Long id);

}
