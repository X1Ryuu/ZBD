package com.example.zbd.repositories;

import com.example.zbd.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("""
        SELECT oi.product.id,
               oi.product.name,
               SUM(oi.quantity)
        FROM OrderItem oi
        GROUP BY oi.product.id, oi.product.name
        ORDER BY SUM(oi.quantity) DESC
    """)
    List<Object[]> findTopSellingProducts();

    public void deleteAllById(Long id);
}
