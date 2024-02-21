package com.victor.backend.ecommerce.ecommercebackend.api.controller.usuario;

import com.victor.backend.ecommerce.ecommercebackend.model.Endereco;
import com.victor.backend.ecommerce.ecommercebackend.model.UsuarioLocal;
import com.victor.backend.ecommerce.ecommercebackend.model.dao.EnderecoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    @Autowired
    private EnderecoDAO enderecoDAO;

    @GetMapping("/{userId}/address")
    public ResponseEntity<List<Endereco>> getAddress(@AuthenticationPrincipal UsuarioLocal usuario, @PathVariable Long userId) {
        if (!userHasPermission(usuario, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(enderecoDAO.findByUsuario_Id(userId));
    }

    @PutMapping("/{userId}/address")
    public ResponseEntity<Endereco> putAddress(@AuthenticationPrincipal UsuarioLocal usuario
            , @PathVariable Long userId, @RequestBody Endereco endereco) {
        if (!userHasPermission(usuario, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        endereco.setId(null);
        UsuarioLocal refUser = new UsuarioLocal();
        refUser.setId(userId);
        endereco.setUsuario(refUser);
        return ResponseEntity.ok(enderecoDAO.save(endereco));
    }

    @PatchMapping("/{userId}/address/{addressId}")
    public ResponseEntity<Endereco> patchAddress(
            @AuthenticationPrincipal UsuarioLocal user, @PathVariable Long userId,
            @PathVariable Long addressId, @RequestBody Endereco address) {
        if (!userHasPermission(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (address.getId() == addressId) {
            Optional<Endereco> opOriginalAddress = enderecoDAO.findById(addressId);
            if (opOriginalAddress.isPresent()) {
                UsuarioLocal originalUser = opOriginalAddress.get().getUsuario();
                if (originalUser.getId() == userId) {
                    address.setUsuario(originalUser);
                    return ResponseEntity.ok(enderecoDAO.save(address));
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }

    private boolean userHasPermission(UsuarioLocal usuario, Long id) {
        return  usuario.getId() == id;
    }
}
