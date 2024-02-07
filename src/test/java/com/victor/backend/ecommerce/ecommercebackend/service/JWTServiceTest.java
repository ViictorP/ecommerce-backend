package com.victor.backend.ecommerce.ecommercebackend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.MissingClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.victor.backend.ecommerce.ecommercebackend.model.UsuarioLocal;
import com.victor.backend.ecommerce.ecommercebackend.model.dao.UsuarioLocalDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class JWTServiceTest {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

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

    @Test
    public void testJWTNotGeneratedByUs() {
        String token = JWT.create().withClaim("USERNAME", "UserA").sign(Algorithm.HMAC256("FakeSecretKey"));
        Assertions.assertThrows(SignatureVerificationException.class, () -> jwtService.getUsername(token));
    }

    @Test
    public void testJWTCorrectlySignedNoIssuer() {
        String token = JWT.create().withClaim("USERNAME", "UserA").sign(Algorithm.HMAC256(algorithmKey));
        Assertions.assertThrows(MissingClaimException.class, () -> jwtService.getUsername(token));
    }
}
