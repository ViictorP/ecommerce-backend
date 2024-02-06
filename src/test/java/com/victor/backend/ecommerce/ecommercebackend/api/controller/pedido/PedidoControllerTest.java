package com.victor.backend.ecommerce.ecommercebackend.api.controller.pedido;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.victor.backend.ecommerce.ecommercebackend.model.Pedido;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PedidoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testUnauthenticatedOrderList() throws Exception {
        mvc.perform(get("/pedido")).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @WithUserDetails("UserA")
    public void testUserAAuthenticatedOrderList() throws Exception {
        testAuthenticatedListBelongsToUser("UserA");
    }

    @Test
    @WithUserDetails("UserB")
    public void testUserBAuthenticatedOrderList() throws Exception {
        testAuthenticatedListBelongsToUser("UserB");
    }

    private void testAuthenticatedListBelongsToUser(String username) throws Exception {
        mvc.perform(get("/pedido")).andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    List<Pedido> orders = new ObjectMapper().readValue(json, new TypeReference<>() {});
                    for (Pedido order : orders) {
                        Assertions.assertEquals(username, order.getUsuario().getUsername(),
                                "Lista de pedidos deve apenas retornar os pedidos referentes ao usuario");
                    }
                });
    }
}
