package com.victor.backend.ecommerce.ecommercebackend.service;

import com.victor.backend.ecommerce.ecommercebackend.api.model.LoginBody;
import com.victor.backend.ecommerce.ecommercebackend.api.model.RegistrationBody;
import com.victor.backend.ecommerce.ecommercebackend.exception.EmailFailureException;
import com.victor.backend.ecommerce.ecommercebackend.exception.UserAlreadyExistsException;
import com.victor.backend.ecommerce.ecommercebackend.model.UsuarioLocal;
import com.victor.backend.ecommerce.ecommercebackend.model.VerificationToken;
import com.victor.backend.ecommerce.ecommercebackend.model.dao.UsuarioLocalDAO;
import com.victor.backend.ecommerce.ecommercebackend.model.dao.VerificationTokenDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioLocalDAO usuarioLocalDAO;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private VerificationTokenDAO verificationTokenDAO;

    public UsuarioLocal registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException, EmailFailureException {
        if (usuarioLocalDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
        || usuarioLocalDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        UsuarioLocal usuarioLocal = new UsuarioLocal();
        usuarioLocal.setUsername(registrationBody.getUsername());
        usuarioLocal.setEmail(registrationBody.getEmail());
        usuarioLocal.setFirstName(registrationBody.getFirstName());
        usuarioLocal.setLastName(registrationBody.getLastName());
        usuarioLocal.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        VerificationToken verificationToken = createVerificationToken(usuarioLocal);
        emailService.sendVerificationEmail(verificationToken);
        return usuarioLocalDAO.save(usuarioLocal);
    }

    private VerificationToken createVerificationToken(UsuarioLocal usuario) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(usuario));
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUsuario(usuario);
        usuario.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }

    public String loginUser(LoginBody loginBody) {
        Optional<UsuarioLocal> usuarioLocalOp = usuarioLocalDAO.findByUsernameIgnoreCase(loginBody.getUsername());
        if (usuarioLocalOp.isPresent()) {
            UsuarioLocal usuario = usuarioLocalOp.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), usuario.getPassword())) {
                return jwtService.generateJWT(usuario);
            }
        }
        return null;
    }
}
