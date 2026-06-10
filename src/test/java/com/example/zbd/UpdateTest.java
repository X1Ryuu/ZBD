package com.example.zbd;

import com.example.zbd.repositories.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

@SpringBootTest
@Transactional
@Sql(
        scripts = "/small.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class UpdateTest {
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
    void testUpdateCustomerFirstName() {
        customerRepository.updateCustomerName("Jane", 1L);
    }

    @Test
    void testIncreaseProductPrices() {
        productRepository.increasePricesByXPercent(new BigDecimal(10));
    }

    @Test
    void testUpdateOrderStatus() {
        orderRepository.markOrderAsShipped(1L);
    }

    @Test
    void testRemoveParentCategory() {
        categoryRepository.removeParentCategory(1L);
    }

    @Test
    void testUpdateReviewRating() {
        reviewRepository.updateRating(4, 1L);
    }

    @Test
    void testUpdateCustomerTimestamp() {
        customerRepository.updateCustomerTimestamp(1L);
    }

    @Test
    void testDeactivateCheapProducts() {
        productRepository.deactivateCheapProducts(new BigDecimal(10));
    }

    @Test
    void testUpdateShippedDate() {
        orderRepository.updateShippedDate();
    }

    @Test
    void testRecalculateOrderItemTotalPrice() {
        orderItemRepository.recalculateTotalPrices();
    }

    @Test
    void testUpdateCustomerPhoneByEmail() {
        customerRepository.updatePhoneByEmail("123456789", "john@example.com");
    }

}
