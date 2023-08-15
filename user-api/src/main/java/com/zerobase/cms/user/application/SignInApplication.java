package com.zerobase.cms.user.application;

import com.zerobase.cms.user.domain.SignInForm;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.domain.model.Seller;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.service.customer.CustomerServiceImpl;
import com.zerobase.cms.user.service.seller.SellerServiceImpl;
import com.zerobase.domain.common.UserType;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.zerobase.cms.user.exception.ErrorCode.LOGIN_CHECK_FAIL;

@Service
@RequiredArgsConstructor
public class SignInApplication {
    private final CustomerServiceImpl customerService;
    private final JwtAuthenticationProvider provider;
    private final SellerServiceImpl sellerServiceImpl;

    public String customerLoginToken(SignInForm form) {
        Customer customer =
                customerService.findValidCustomer(form.getEmail(), form.getPassword())
                        .orElseThrow(() -> new CustomException(LOGIN_CHECK_FAIL));
        return provider.createToken(customer.getEmail(), customer.getId(), UserType.CUSTOMER);
    }

    public String sellerLoginToken(SignInForm form) {
        Seller seller =
                sellerServiceImpl.findValidSeller(form.getEmail(), form.getPassword())
                        .orElseThrow(() -> new CustomException(LOGIN_CHECK_FAIL));
        return provider.createToken(seller.getEmail(), seller.getId(), UserType.SELLER);
    }
}
