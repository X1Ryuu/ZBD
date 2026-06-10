package com.example.zbd;

import com.example.zbd.repositories.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Transactional
@Sql(
        scripts = "/small.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class JoinTest {
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
    void testFindOrdersWithCustomerEmail() {
        orderRepository.findOrderIdAndCustomerEmail();
    }

    @Test
    void testFindOrderItemsWithProductName() {
        orderItemRepository.findItemsWithProductName();
    }

    @Test
    void testFindReviewRatingsWithProductName() {
        reviewRepository.findRatingsWithProductName();
    }

    @Test
    void testFindReviewsWithCustomerEmail() {
        reviewRepository.findReviewsWithCustomerEmail();
    }

    @Test
    void testCalculateOrderItemsTotalPerOrder() {
        orderRepository.findOrderIdAndTotals();
    }

    @Test
    void testFindProductsWithCategoryName() {
        productRepository.findProductsWithCategory();
    }

    @Test
    void testFindCategoriesWithParentCategory() {
        categoryRepository.findCategoriesWithParent();
    }

    @Test
    void testFindOrderProductsAndQuantities() {
        orderRepository.findOrderIdAndProductNameAndQuantities();
    }

    @Test
    void testCountOrdersPerCustomerEmail() {
        customerRepository.findCustomersWithOrderCount();
    }

    @Test
    void testCalculateAverageRatingPerProduct() {
        productRepository.findAverageRatings();
    }
}
