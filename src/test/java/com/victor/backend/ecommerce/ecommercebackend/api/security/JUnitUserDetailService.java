package com.victor.backend.ecommerce.ecommercebackend.api.security;

import com.victor.backend.ecommerce.ecommercebackend.model.UsuarioLocal;
import com.victor.backend.ecommerce.ecommercebackend.model.dao.UsuarioLocalDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class JUnitUserDetailService implements UserDetailsService {

    @Autowired
    private UsuarioLocalDAO usuarioLocalDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsuarioLocal> opUser = usuarioLocalDAO.findByUsernameIgnoreCase(username);
        if (opUser.isPresent()) {
            return opUser.get();
        }
        return null;
    }
}
