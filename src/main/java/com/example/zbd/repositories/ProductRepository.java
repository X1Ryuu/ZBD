package com.example.zbd.repositories;

import com.example.zbd.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoryId(Long id);
    public void deleteAllByIsActive(Boolean isActive);
    public void deleteAllByCreatedAtBefore(LocalDateTime date);
    public void deleteAllByPriceGreaterThan(BigDecimal price);

    List<Product> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    @Query("""
SELECT AVG(p.price)
FROM Product p
""")
    BigDecimal getAvgPrice();

    @Query("""
SELECT DISTINCT p.brand
FROM Product p
""")
    String getBrand();

}
