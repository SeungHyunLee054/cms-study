package com.zerobase.cms.user.service.customer;

import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Customer;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface SignUpCustomerService {
    Customer signUp(SignUpForm form);

    boolean isEmailExist(String email);

    @Transactional
    void verifyEmail(String email, String code);

    @Transactional
    LocalDateTime changeCustomerValidateEmail(Long customerId, String verificationCode);
}
