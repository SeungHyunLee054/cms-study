package com.zerobase.cms.order.domain.repository;

import com.zerobase.cms.order.domain.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.LOAD;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    @EntityGraph(attributePaths = "productItems", type = LOAD)
    Optional<Product> findWithProductItemsById(Long id);

    @EntityGraph(attributePaths = "productItems", type = LOAD)
    Optional<Product> findBySellerIdAndId(Long sellerId, Long id);

    @EntityGraph(attributePaths = "productItems", type = LOAD)
    List<Product> findAllByIdIn(List<Long> ids);
}
