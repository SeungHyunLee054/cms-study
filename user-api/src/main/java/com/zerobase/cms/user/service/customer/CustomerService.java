package com.zerobase.cms.user.service.customer;

import com.zerobase.cms.user.domain.model.Customer;

import java.util.Optional;

public interface CustomerService {
    Optional<Customer> findByIdAndEmail(Long id, String email);

    Optional<Customer> findValidCustomer(String email, String password);
}
