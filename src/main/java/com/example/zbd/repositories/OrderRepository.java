package com.example.zbd.repositories;

import com.example.zbd.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findOrdersByCustomerId(Long customerId);

    @Query("""
SELECT o
FROM Order o
JOIN FETCH o.items
WHERE o.customer.id = :customerId
""")
    public List<Order> findOrdersWithItems(Long customerId);
    public void deleteAllByStatus(String status);
    public void deleteAllByCreatedAtBefore(LocalDateTime createdAt);

    List<Order> findAllByStatus(String status);
    @Query("""
SELECT COUNT(*)
FROM Order o
""")
    List<Object[]> findCountOfOrders();

}
