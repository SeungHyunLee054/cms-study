package com.zerobase.cms.order.service;

import com.zerobase.cms.order.domain.model.Product;
import com.zerobase.cms.order.domain.model.ProductItem;
import com.zerobase.cms.order.domain.product.AddProductForm;
import com.zerobase.cms.order.domain.product.UpdateProductForm;
import com.zerobase.cms.order.domain.product.UpdateProductItemForm;
import com.zerobase.cms.order.domain.repository.ProductRepository;
import com.zerobase.cms.order.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.zerobase.cms.order.exception.ErrorCode.NO_EXIST_ITEM;
import static com.zerobase.cms.order.exception.ErrorCode.NO_EXIST_PRODUCT;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Product addProduct(Long sellerId, AddProductForm form) {
        return productRepository.save(Product.of(sellerId, form));
    }

    @Override
    public Product updateProduct(Long sellerId, UpdateProductForm form) {
        Product product = productRepository.findBySellerIdAndId(sellerId, form.getId())
                .orElseThrow(() -> new CustomException(NO_EXIST_PRODUCT));
        product.setName(form.getName());
        product.setDescription(form.getDescription());

        for (UpdateProductItemForm itemForm : form.getItems()) {
            ProductItem item = product.getProductItems().stream()
                    .filter(pi -> pi.getId().equals(itemForm.getId()))
                    .findFirst()
                    .orElseThrow(() -> new CustomException(NO_EXIST_ITEM));
            item.setName(itemForm.getName());
            item.setPrice(itemForm.getPrice());
            item.setCount(itemForm.getCount());
        }
        return product;
    }

    @Override
    @Transactional
    public void deleteProduct(Long sellerId, Long productId){
        Product product = productRepository.findBySellerIdAndId(sellerId, productId)
                .orElseThrow(() -> new CustomException(NO_EXIST_PRODUCT));
        productRepository.delete(product);
    }
}
