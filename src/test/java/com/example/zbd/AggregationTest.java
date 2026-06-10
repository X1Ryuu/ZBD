package com.example.zbd;

import com.example.zbd.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AggregationTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testCountCustomers() {
        customerRepository.count();
    }

    @Test
    void testSumTotalAmount() {
        orderRepository.getTotalRevenue();
    }

    @Test
    void testAverageRating() {
        reviewRepository.getAverageRating();
    }

    @Test
    void testMaxPrice() {
        productRepository.getMaxPrice();
    }

    @Test
    void testMinPrice() {
        productRepository.getMinPrice();
    }

    @Test
    void testOrderCountPerCustomer() {
        orderRepository.getOrderCountPerCustomer();
    }

    @Test
    void testTotalInventoryPerProduct() {
        orderItemRepository.getTotalSoldPerProduct();
    }

    @Test
    void testOrderCountByStatus() {
        orderRepository.getOrdersByStatus();
    }

    @Test
    void testAveragePricePerCategory() {
        productRepository.getAveragePricePerCategory();
    }

    @Test
    void testOrdersPerDay() {
        orderRepository.getOrdersPerDay();
    }


}
