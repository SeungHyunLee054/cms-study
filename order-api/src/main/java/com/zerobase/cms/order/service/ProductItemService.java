package com.zerobase.cms.order.service;

import com.zerobase.cms.order.domain.model.Product;
import com.zerobase.cms.order.domain.model.ProductItem;
import com.zerobase.cms.order.domain.product.AddProductItemForm;
import com.zerobase.cms.order.domain.product.UpdateProductItemForm;
import org.springframework.transaction.annotation.Transactional;

public interface ProductItemService {
    @Transactional
    Product addProductItem(Long sellerId, AddProductItemForm form);

    @Transactional
    ProductItem updateProductItem(Long sellerId, UpdateProductItemForm form);

    @Transactional
    void deleteProductItem(Long sellerId, Long productItemId);

    @Transactional
    ProductItem getProductItem(Long id);
}
