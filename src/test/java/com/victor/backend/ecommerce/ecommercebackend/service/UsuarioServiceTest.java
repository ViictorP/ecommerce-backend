package com.victor.backend.ecommerce.ecommercebackend.service;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.victor.backend.ecommerce.ecommercebackend.api.model.LoginBody;
import com.victor.backend.ecommerce.ecommercebackend.api.model.RegistrationBody;
import com.victor.backend.ecommerce.ecommercebackend.exception.EmailFailureException;
import com.victor.backend.ecommerce.ecommercebackend.exception.UserAlreadyExistsException;
import com.victor.backend.ecommerce.ecommercebackend.exception.UserNotVerifiedException;
import com.victor.backend.ecommerce.ecommercebackend.model.VerificationToken;
import com.victor.backend.ecommerce.ecommercebackend.model.dao.VerificationTokenDAO;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioServiceTest {

    @Autowired
    private VerificationTokenDAO verificationTokenDAO;

    @RegisterExtension
    private static GreenMailExtension greenMailExtension = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("springboot", "secret"))
            .withPerMethodLifecycle(true);

    @Autowired
    private UsuarioService usuarioService;

    @Test
    @Transactional
    public void testRegisterUser() throws MessagingException {
        RegistrationBody body = new RegistrationBody();
        body.setUsername("UserA");
        body.setEmail("UserServiceTest$testRegisterUser@junit.com");
        body.setFirstName("FirstName");
        body.setLastName("LastName");
        body.setPassword("MySecretPassword123");
        Assertions.assertThrows(UserAlreadyExistsException.class,
                () -> usuarioService.registerUser(body), "Username já existe.");
        body.setUsername("UserServiceTest$testRegisterUser");
        body.setEmail("UserA@junit.com");
        Assertions.assertThrows(UserAlreadyExistsException.class,
                () -> usuarioService.registerUser(body), "Email já esta cadastrado");
        body.setEmail("UserServiceTest$testRegisterUser@junit.com");
        Assertions.assertDoesNotThrow(() -> usuarioService.registerUser(body),
                "Usuario deve ser registrado com sucesso.");
        Assertions.assertEquals(body.getEmail(), greenMailExtension.getReceivedMessages()[0]
                .getRecipients(Message.RecipientType.TO)[0].toString());
    }

    @Test
    @Transactional
    public void testLoginUser() throws EmailFailureException, UserNotVerifiedException {
        LoginBody body = new LoginBody();
        body.setUsername("UserA-NotExists");
        body.setPassword("PasswordA123-BadPassword");
        Assertions.assertNull(usuarioService.loginUser(body), "Usuario não deve existir.");

        body.setUsername("UserA");
        Assertions.assertNull(usuarioService.loginUser(body), "Password incorreto");

        body.setPassword("PasswordA123");
        Assertions.assertNotNull(usuarioService.loginUser(body), "Login executado com sucesso.");

        body.setUsername("UserB");
        body.setPassword("PasswordB123");

        try {
            usuarioService.loginUser(body);
            Assertions.assertTrue(false, "Usuario não tem email verificado.");
        } catch (UserNotVerifiedException ex) {
            Assertions.assertTrue(ex.isNewEmailSent(), "Email de verificação deve ser enviado.");
            Assertions.assertEquals(1, greenMailExtension.getReceivedMessages().length);
        }

        try {
            usuarioService.loginUser(body);
            Assertions.assertTrue(false, "Usuario não tem email verificado.");
        } catch (UserNotVerifiedException ex) {
            Assertions.assertFalse(ex.isNewEmailSent(), "Email de verificação não deve ser reenviado.");
            Assertions.assertEquals(1, greenMailExtension.getReceivedMessages().length);
        }
    }

    @Test
    @Transactional
    public void testVerifyUser() throws EmailFailureException {
        Assertions.assertFalse(usuarioService.verifyUser("Bad Token"),
                "Token não existe ou não é valido.");

        LoginBody body = new LoginBody();
        body.setUsername("UserB");
        body.setPassword("PasswordB123");

        try {
            usuarioService.loginUser(body);
            Assertions.assertTrue(false, "Usuario não tem email verificado.");
        } catch (UserNotVerifiedException ex) {
            List<VerificationToken> tokens = verificationTokenDAO.findByUsuario_IdOrderByUsuario_IdDesc(2L);
            String token = tokens.get(0).getToken();
            Assertions.assertTrue(usuarioService.verifyUser(token), "Token deve ser valido.");
            Assertions.assertNotNull(body, "Usuario deve ser verificado.");
        }
    }
}
