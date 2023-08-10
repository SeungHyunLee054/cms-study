package com.zerobase.cms.user.domain.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerBalanceHistory extends BaseEntity{
    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY)
    private Customer customer;
    private Integer changeMoney;
    private Integer currentMoney;
    private String fromMessage;
    private String description;
}
