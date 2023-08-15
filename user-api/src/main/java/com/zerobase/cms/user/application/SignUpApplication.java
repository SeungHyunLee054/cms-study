package com.zerobase.cms.user.application;

import com.zerobase.cms.user.client.MailgunClient;
import com.zerobase.cms.user.client.mailgun.SendMailForm;
import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.domain.model.Seller;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.exception.ErrorCode;
import com.zerobase.cms.user.service.customer.SignUpCustomerServiceImpl;
import com.zerobase.cms.user.service.seller.SellerServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignUpApplication {
    private final MailgunClient mailgunClient;
    private final SignUpCustomerServiceImpl signUpCustomerServiceImpl;
    private final SellerServiceImpl sellerServiceImpl;

    public String customerSignUp(SignUpForm form) {
        if (signUpCustomerServiceImpl.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            Customer customer = signUpCustomerServiceImpl.signUp(form);

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("zerobase-test@mytesst.com")
                    .to(form.getEmail())
                    .subject("Verification Email!")
                    .text(getVerificationEmailBody(customer.getEmail(), customer.getName(), "customer", code))
                    .build();
            log.info("Send email result : " + mailgunClient.sendEmail(sendMailForm).body());
            signUpCustomerServiceImpl.changeCustomerValidateEmail(customer.getId(), code);
            return "회원 가입 성공";
        }
    }

    public String sellerSignUp(SignUpForm form) {
        if (sellerServiceImpl.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            Seller seller = sellerServiceImpl.signUp(form);

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("zerobase=test@mytesst.com")
                    .to(form.getEmail())
                    .subject("Verification Email!")
                    .text(getVerificationEmailBody(seller.getEmail(), seller.getName(), "seller", code))
                    .build();
            log.info("Send email result : " + mailgunClient.sendEmail(sendMailForm).body());
            sellerServiceImpl.changeSellerValidateEmail(seller.getId(), code);
            return "회원 가입 성공";
        }
    }

    public void sellerVerify(String email, String code) {
        sellerServiceImpl.verifyEmail(email, code);
    }

    public void customerVerify(String email, String code) {
        signUpCustomerServiceImpl.verifyEmail(email, code);
    }

    private String getRandomCode() {
        return RandomStringUtils.random(10, true, true);
    }

    private String getVerificationEmailBody(String email, String name, String type, String code) {
        StringBuilder sb = new StringBuilder();
        return sb.append("Hello ")
                .append(name)
                .append("! Please Click Link for verification\n\n")
                .append("http://localhost:8081/signUp/" + type + "/verify?email=")
                .append(email)
                .append("&code=")
                .append(code)
                .toString();
    }
}
