package com.zerobase.cms.user.service.customer;

import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SignUpCustomerServiceTest {
    @Autowired
    private SignUpCustomerServiceImpl signUpCustomerServiceImpl;

    @Test
    void signUp() {
        //given
        SignUpForm form = SignUpForm.builder()
                .name("name")
                .birth(LocalDate.now())
                .email("abc@gmail.com")
                .password("1")
                .phone("01012345678")
                .build();

        Customer c = signUpCustomerServiceImpl.signUp(form);
        //when
        //then
        assertNotNull(c.getId());
        assertEquals(1, c.getId());
    }
}