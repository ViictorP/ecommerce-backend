package com.victor.backend.ecommerce.ecommercebackend.service;

import com.victor.backend.ecommerce.ecommercebackend.api.model.LoginBody;
import com.victor.backend.ecommerce.ecommercebackend.api.model.RegistrationBody;
import com.victor.backend.ecommerce.ecommercebackend.exception.EmailFailureException;
import com.victor.backend.ecommerce.ecommercebackend.exception.UserAlreadyExistsException;
import com.victor.backend.ecommerce.ecommercebackend.exception.UserNotVerifiedException;
import com.victor.backend.ecommerce.ecommercebackend.model.UsuarioLocal;
import com.victor.backend.ecommerce.ecommercebackend.model.VerificationToken;
import com.victor.backend.ecommerce.ecommercebackend.model.dao.UsuarioLocalDAO;
import com.victor.backend.ecommerce.ecommercebackend.model.dao.VerificationTokenDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
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

    public String loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFailureException {
        Optional<UsuarioLocal> usuarioLocalOp = usuarioLocalDAO.findByUsernameIgnoreCase(loginBody.getUsername());
        if (usuarioLocalOp.isPresent()) {
            UsuarioLocal usuario = usuarioLocalOp.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), usuario.getPassword())) {
                if (usuario.isEmailVerified()) {
                    return jwtService.generateJWT(usuario);
                } else {
                    List<VerificationToken> verificationTokens = usuario.getVerificationTokens();
                    boolean resend = verificationTokens.size() == 0 ||
                            verificationTokens.get(0).getCreatedTimestamp()
                                    .before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000)));
                    if (resend) {
                        VerificationToken verificationToken = createVerificationToken(usuario);
                        verificationTokenDAO.save(verificationToken);
                        emailService.sendVerificationEmail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }
            }
        }
        return null;
    }

    @Transactional
    public boolean verifyUser(String token) {
        Optional<VerificationToken> opToken = verificationTokenDAO.findByToken(token);
        if (opToken.isPresent()) {
            VerificationToken verificationToken = opToken.get();
            UsuarioLocal usuario = verificationToken.getUsuario();
            if (!usuario.isEmailVerified()) {
                usuario.setEmailVerified(true);
                usuarioLocalDAO.save(usuario);
                verificationTokenDAO.deleteByUsuario(usuario);
                return true;
            }
        }
        return false;
    }
}
