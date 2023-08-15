package com.zerobase.cms.order.service;

import com.zerobase.cms.order.domain.product.AddProductCartForm;
import com.zerobase.cms.order.domain.redis.Cart;

public interface CartService {
    Cart getCart(Long customerId);

    Cart addCart(Long customerId, AddProductCartForm form);

    Cart putCart(Long customerId, Cart cart);
}
