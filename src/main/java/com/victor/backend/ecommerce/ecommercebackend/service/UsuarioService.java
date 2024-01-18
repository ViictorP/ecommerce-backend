package com.victor.backend.ecommerce.ecommercebackend.service;

import com.victor.backend.ecommerce.ecommercebackend.api.model.RegistrationBody;
import com.victor.backend.ecommerce.ecommercebackend.exception.UserAlreadyExistsException;
import com.victor.backend.ecommerce.ecommercebackend.model.UsuarioLocal;
import com.victor.backend.ecommerce.ecommercebackend.model.dao.UsuarioLocalDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioLocalDAO usuarioLocalDAO;

    public UsuarioLocal registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {

        if (usuarioLocalDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
        || usuarioLocalDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        UsuarioLocal usuarioLocal = new UsuarioLocal();
        usuarioLocal.setUsername(registrationBody.getUsername());
        usuarioLocal.setEmail(registrationBody.getEmail());
        usuarioLocal.setFirstName(registrationBody.getFirstName());
        usuarioLocal.setLastName(registrationBody.getLastName());
        // TODO: Encrypt password
        usuarioLocal.setPassword(registrationBody.getPassword());
        
        return usuarioLocalDAO.save(usuarioLocal);
    }
}
