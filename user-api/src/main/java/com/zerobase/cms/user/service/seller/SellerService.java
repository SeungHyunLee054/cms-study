package com.zerobase.cms.user.service.seller;

import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Seller;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SellerService {
    Optional<Seller> findByIdAndEmail(Long id, String email);

    Optional<Seller> findValidSeller(String email, String password);

    Seller signUp(SignUpForm form);

    boolean isEmailExist(String email);

    @Transactional
    void verifyEmail(String email, String code);

    @Transactional
    LocalDateTime changeSellerValidateEmail(Long sellerId, String verificationCode);
}
