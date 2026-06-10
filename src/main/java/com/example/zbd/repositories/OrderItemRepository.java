package com.example.zbd.repositories;

import com.example.zbd.entities.OrderItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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



    //--------------------------------- select


    //--------------------------------- update
    @Modifying
    @Transactional
    @Query("""
           UPDATE OrderItem oi
           SET oi.totalPrice = oi.quantity * oi.unitPrice
           """)
    int recalculateTotalPrices();

    //--------------------------------- delete
    void deleteAllById(Long id);

    //--------------------------------- insert


    //--------------------------------- aggregation
    @Query("""
       SELECT oi.product.id,
              SUM(oi.quantity)
       FROM OrderItem oi
       GROUP BY oi.product.id
       """)
    List<Object[]> getTotalSoldPerProduct();

    //--------------------------------- join
    @Query("""
           SELECT oi, p.name
           FROM OrderItem oi
           JOIN oi.product p
           """)
    List<Object[]> findItemsWithProductName();
}
