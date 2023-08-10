package com.zerobase.cms.user.domain.seller;

import com.zerobase.cms.user.domain.model.Seller;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerDto {
    private Long id;
    private String email;

    public static SellerDto from(Seller seller) {
        return SellerDto.builder()
                .id(seller.getId())
                .email(seller.getEmail())
                .build();
    }
}
