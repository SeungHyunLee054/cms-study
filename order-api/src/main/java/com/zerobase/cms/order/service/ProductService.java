package com.zerobase.cms.order.service;

import com.zerobase.cms.order.domain.model.Product;
import com.zerobase.cms.order.domain.product.AddProductForm;
import com.zerobase.cms.order.domain.product.UpdateProductForm;
import org.springframework.transaction.annotation.Transactional;

public interface ProductService {
    @Transactional
    Product addProduct(Long sellerId, AddProductForm form);

    Product updateProduct(Long sellerId, UpdateProductForm form);

    @Transactional
    void deleteProduct(Long sellerId, Long productId);
}
