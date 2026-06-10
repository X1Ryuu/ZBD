package com.example.zbd.repositories;

import com.example.zbd.entities.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoryId(Long id);
    Product findProductById(Long id);

    public void deleteAllByCreatedAtBefore(LocalDateTime date);





    //--------------------------------- select
    @Query("""
SELECT AVG(p.price)
FROM Product p
""")
    BigDecimal getAvgPrice();

    @Query("""
SELECT DISTINCT p.brand
FROM Product p
""")
    List<String> getBrands();


    List<Product> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    //--------------------------------- update
    @Modifying
    @Transactional
    @Query("""
           UPDATE Product p
           SET p.price = p.price * (1+:increase/100)
           """)
    int increasePricesByXPercent(@Param("increase") BigDecimal increase);

    @Modifying
    @Transactional
    @Query("""
           UPDATE Product p
           SET p.isActive = false
           WHERE p.price < :price
           """)
    int deactivateCheapProducts(@Param("price") BigDecimal priceThreshold);

    //--------------------------------- delete
    void deleteAllByPriceGreaterThan(BigDecimal price);
    void deleteAllByIsActive(Boolean isActive);

    //--------------------------------- insert


    //--------------------------------- aggregation
    @Query("""
       SELECT MAX(p.price)
       FROM Product p
       """)
    BigDecimal getMaxPrice();

    @Query("""
       SELECT MIN(p.price)
       FROM Product p
       """)
    BigDecimal getMinPrice();

    @Query("""
       SELECT p.category.id,
              AVG(p.price)
       FROM Product p
       GROUP BY p.category.id
       """)
    List<Object[]> getAveragePricePerCategory();

    //--------------------------------- join
    @Query("""
           SELECT p.name, c.name
           FROM Product p
           JOIN p.category c
           """)
    List<Object[]> findProductsWithCategory();

    @Query("""
           SELECT p.name, AVG(r.rating)
           FROM Product p
           LEFT JOIN p.reviews r
           GROUP BY p.name
           """)
    List<Object[]> findAverageRatings();
}
