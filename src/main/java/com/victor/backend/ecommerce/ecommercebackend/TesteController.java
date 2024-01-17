package com.victor.backend.ecommerce.ecommercebackend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {

    @GetMapping("/test")
    public String test() {
        return "Test";
    }

}
