package com.victor.backend.ecommerce.ecommercebackend.service;

import com.victor.backend.ecommerce.ecommercebackend.exception.EmailFailureException;
import com.victor.backend.ecommerce.ecommercebackend.model.UsuarioLocal;
import com.victor.backend.ecommerce.ecommercebackend.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${email.from}")
    private String fromAddress;

    @Value("${app.frontend.url}")
    private String url;

    @Autowired
    private JavaMailSender javaMailSender;

    private SimpleMailMessage makeMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAddress);
        return simpleMailMessage;
    }

    public void sendVerificationEmail(VerificationToken verificationToken) throws EmailFailureException {
        SimpleMailMessage simpleMailMessage = makeMailMessage();
        simpleMailMessage.setTo(verificationToken.getUsuario().getEmail());
        simpleMailMessage.setSubject("Verifique o e-mail para ativar sua conta.");
        simpleMailMessage.setText("Clique no link abaixo para verificar seu e-mail e ativar sua conta."
                + url + "/auth/verify?token=" + verificationToken.getToken());
        try {
            javaMailSender.send(simpleMailMessage);
        } catch (MailException  e) {
            throw new EmailFailureException();
        }
    }

    public void sendPasswordResetEamil(UsuarioLocal usuarui, String token) throws EmailFailureException {
        SimpleMailMessage simpleMailMessage = makeMailMessage();
        simpleMailMessage.setTo(usuarui.getEmail());
        simpleMailMessage.setSubject("Solicitação de Troca de Senha");
        simpleMailMessage.setText("Clique no link para escolher uma nova senha."
                + url + "/auth/reset?token=" + token);
        try {
            javaMailSender.send(simpleMailMessage);
        } catch (MailException  e) {
            throw new EmailFailureException();
        }
    }
}
