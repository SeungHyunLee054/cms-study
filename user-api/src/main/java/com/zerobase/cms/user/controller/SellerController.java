package com.zerobase.cms.user.controller;

import com.zerobase.cms.user.domain.model.Seller;
import com.zerobase.cms.user.domain.seller.SellerDto;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.service.seller.SellerServiceImpl;
import com.zerobase.domain.common.UserVo;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.zerobase.cms.user.exception.ErrorCode.NO_EXIST_USER;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {
    private final JwtAuthenticationProvider provider;
    private final SellerServiceImpl sellerServiceImpl;

    @GetMapping("/getInfo")
    public ResponseEntity<?> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo vo = provider.getUserVo(token);
        Seller seller = sellerServiceImpl.findByIdAndEmail(vo.getId(), vo.getEmail())
                .orElseThrow(() -> new CustomException(NO_EXIST_USER));
        return ResponseEntity.ok(SellerDto.from(seller));
    }

}
