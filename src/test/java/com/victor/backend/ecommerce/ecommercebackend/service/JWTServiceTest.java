package com.victor.backend.ecommerce.ecommercebackend.service;

import com.victor.backend.ecommerce.ecommercebackend.model.UsuarioLocal;
import com.victor.backend.ecommerce.ecommercebackend.model.dao.UsuarioLocalDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JWTServiceTest {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UsuarioLocalDAO usuarioLocalDAO;

    @Test
    public void testVerificationTokenNotUsableForLogin() {
        UsuarioLocal usuario = usuarioLocalDAO.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generateVerificationJWT(usuario);
        Assertions.assertNull(jwtService.getUsername(token), "Token de verificação não deve conter username.");
    }

    @Test
    public void testAuthTokenReturnsUsername() {
        UsuarioLocal usuario = usuarioLocalDAO.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generateJWT(usuario);
        Assertions.assertEquals(usuario.getUsername(), jwtService.getUsername(token), "Token de autenticação deve conter username.");
    }
}
