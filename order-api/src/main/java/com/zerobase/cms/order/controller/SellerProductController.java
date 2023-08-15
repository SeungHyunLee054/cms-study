package com.zerobase.cms.order.controller;

import com.zerobase.cms.order.domain.product.*;
import com.zerobase.cms.order.service.ProductItemServiceImpl;
import com.zerobase.cms.order.service.ProductServiceImpl;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller/product")
public class SellerProductController {
    private final ProductServiceImpl productService;
    private final ProductItemServiceImpl productItemServiceImpl;
    private final JwtAuthenticationProvider provider;

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestHeader(name = "X-AUTH-TOKEN") String token
            , @RequestBody AddProductForm form) {
        return ResponseEntity.ok(ProductDto.from(
                productService.addProduct(
                        provider.getUserVo(token).getId(), form)));
    }

    @PostMapping("/item")
    public ResponseEntity<ProductDto> addProductItem(@RequestHeader(name = "X-AUTH-TOKEN") String token
            , @RequestBody AddProductItemForm form) {
        return ResponseEntity.ok(ProductDto.from(
                productItemServiceImpl.addProductItem(
                        provider.getUserVo(token).getId(), form)));
    }

    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@RequestHeader(name = "X-AUTH-TOKEN") String token
            , @RequestBody UpdateProductForm form) {
        return ResponseEntity.ok(ProductDto.from(
                productService.updateProduct(
                        provider.getUserVo(token).getId(), form)));
    }

    @PutMapping("/item")
    public ResponseEntity<ProductItemDto> updateProductItem(@RequestHeader(name = "X-AUTH-TOKEN") String token
            , @RequestBody UpdateProductItemForm form) {
        return ResponseEntity.ok(ProductItemDto.from(
                productItemServiceImpl.updateProductItem(
                        provider.getUserVo(token).getId(), form)));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@RequestHeader(name = "X-AUTH-TOKEN") String token
            , @RequestParam Long id) {
        productService.deleteProduct(provider.getUserVo(token).getId(), id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/item")
    public ResponseEntity<Void> deleteProductItem(@RequestHeader(name = "X-AUTH-TOKEN") String token
            , @RequestParam Long id) {
        productItemServiceImpl.deleteProductItem(provider.getUserVo(token).getId(), id);
        return ResponseEntity.ok().build();
    }
}
