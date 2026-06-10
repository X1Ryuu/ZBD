package com.example.zbd.repositories;

import com.example.zbd.entities.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findOrdersByCustomerId(Long customerId);
    Order findOrderById(Long id);

    @Query("""
SELECT o
FROM Order o
JOIN FETCH o.items
WHERE o.customer.id = :customerId
""")
    public List<Order> findOrdersWithItems(Long customerId);



    //--------------------------------- select
    List<Order> findAllByStatus(String status);
    @Query("""
SELECT COUNT(*)
FROM Order o
""")
    List<Object[]> findCountOfOrders();


    //--------------------------------- update
    @Modifying
    @Transactional
    @Query("""
           UPDATE Order o
           SET o.status = 'SHIPPED'
           WHERE o.id = :id
           """)
    int markOrderAsShipped(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("""
           UPDATE Order o
           SET o.shippedDate = CURRENT_TIMESTAMP
           WHERE o.status = 'SHIPPED'
           """)
    int updateShippedDate();

    //--------------------------------- delete
    void deleteAllByStatus(String status);
    void deleteAllByCreatedAtBefore(LocalDateTime createdAt);

    //--------------------------------- insert


    //--------------------------------- aggregation
    @Query("""
       SELECT SUM(o.totalAmount)
       FROM Order o
       """)
    BigDecimal getTotalRevenue();

    @Query("""
       SELECT o.customer.id,
              COUNT(o)
       FROM Order o
       GROUP BY o.customer.id
       """)
    List<Object[]> getOrderCountPerCustomer();

    @Query("""
       SELECT o.status,
              COUNT(o)
       FROM Order o
       GROUP BY o.status
       """)
    List<Object[]> getOrdersByStatus();


    @Query(value = """
       SELECT DATE(order_date),
              COUNT(*)
       FROM orders
       GROUP BY DATE(order_date)
       ORDER BY DATE(order_date)
       """, nativeQuery = true)
    List<Object[]> getOrdersPerDay();

    //--------------------------------- join
    @Query("""
           SELECT o.id, c.email
           FROM Order o
           JOIN o.customer c
           """)
    List<Object[]> findOrderIdAndCustomerEmail();

    @Query("""
           SELECT o.id, SUM(oi.totalPrice)
           FROM Order o
           JOIN o.items oi
           GROUP BY o.id
           """)
    List<Object[]> findOrderIdAndTotals();

    @Query("""
           SELECT o.id, p.name, oi.quantity
           FROM Order o
           JOIN o.items oi
           JOIN oi.product p
           """)
    List<Object[]> findOrderIdAndProductNameAndQuantities();
}
