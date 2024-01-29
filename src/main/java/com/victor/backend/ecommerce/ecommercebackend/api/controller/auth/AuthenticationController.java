package com.victor.backend.ecommerce.ecommercebackend.api.controller.auth;

import com.victor.backend.ecommerce.ecommercebackend.api.model.LoginBody;
import com.victor.backend.ecommerce.ecommercebackend.api.model.LoginResponse;
import com.victor.backend.ecommerce.ecommercebackend.api.model.RegistrationBody;
import com.victor.backend.ecommerce.ecommercebackend.exception.EmailFailureException;
import com.victor.backend.ecommerce.ecommercebackend.exception.UserAlreadyExistsException;
import com.victor.backend.ecommerce.ecommercebackend.model.UsuarioLocal;
import com.victor.backend.ecommerce.ecommercebackend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody) {
        try {
            usuarioService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EmailFailureException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody) {
        String jwt = usuarioService.loginUser(loginBody);
        if (jwt == null) {
            return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build();
        } else {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setJwt(jwt);
            return ResponseEntity.ok(loginResponse);
        }
    }

    @GetMapping("/me")
    public UsuarioLocal getLoggedInUserProfile(@AuthenticationPrincipal UsuarioLocal usuario) {
        return usuario;
    }
}
