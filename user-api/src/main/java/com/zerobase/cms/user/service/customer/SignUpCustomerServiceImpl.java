package com.zerobase.cms.user.service.customer;

import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.domain.repository.CustomerRepository;
import com.zerobase.cms.user.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.zerobase.cms.user.exception.ErrorCode.*;
import static java.util.Locale.ROOT;

@Service
@RequiredArgsConstructor
public class SignUpCustomerServiceImpl implements SignUpCustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer signUp(SignUpForm form) {
        return customerRepository.save(Customer.from(form));
    }

    @Override
    public boolean isEmailExist(String email) {
        return customerRepository.findByEmail(email.toLowerCase(ROOT))
                .isPresent();
    }

    @Override
    @Transactional
    public void verifyEmail(String email, String code) {
        Customer customer = customerRepository.findByEmail(email.toLowerCase(ROOT))
                .orElseThrow(() -> new CustomException(NO_EXIST_USER));

        validateVerifyEmail(code, customer);

        customer.setVerify(true);
    }

    private void validateVerifyEmail(String code, Customer customer) {
        if (customer.isVerify()) {
            throw new CustomException(ALREADY_VERIFY);
        }

        if (!customer.getVerificationCode().equals(code)) {
            throw new CustomException(WRONG_VERIFICATION);
        }

        if (customer.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(EXPIRE_CODE);
        }
    }

    @Override
    @Transactional
    public LocalDateTime changeCustomerValidateEmail(Long customerId, String verificationCode) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setVerificationCode(verificationCode);
            customer.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
            return customer.getVerifyExpiredAt();
        } else {
            throw new CustomException(NO_EXIST_USER);
        }
    }
}
