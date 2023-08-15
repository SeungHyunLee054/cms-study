package com.zerobase.cms.order.service;

import com.zerobase.cms.order.domain.model.Product;

import java.util.List;

public interface ProductSearchService {
    List<Product> searchByName(String name);

    Product getByProductId(Long productId);

    List<Product> getListByProductIds(List<Long> productIds);
}
