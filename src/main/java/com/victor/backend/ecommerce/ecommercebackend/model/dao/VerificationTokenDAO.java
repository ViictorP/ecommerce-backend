package com.victor.backend.ecommerce.ecommercebackend.model.dao;

import com.victor.backend.ecommerce.ecommercebackend.model.VerificationToken;
import org.springframework.data.repository.ListCrudRepository;

public interface VerificationTokenDAO extends ListCrudRepository<VerificationToken, Long> {
}
