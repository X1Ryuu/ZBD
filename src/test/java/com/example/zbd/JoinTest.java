package com.example.zbd;

import com.example.zbd.repositories.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

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

    @BeforeEach
    void separator(RepetitionInfo repetitionInfo) throws IOException {
        int current = repetitionInfo.getCurrentRepetition();
        int total = repetitionInfo.getTotalRepetitions();

        if(current == total){
            Files.writeString(Path.of("query_metrics.csv"),"\n", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }

    }

    @RepeatedTest(80)
    void testFindOrdersWithCustomerEmail() {
        orderRepository.findOrderIdAndCustomerEmail();
    }

    @RepeatedTest(80)
    void testFindOrderItemsWithProductName() {
        orderItemRepository.findItemsWithProductName();
    }

    @RepeatedTest(80)
    void testFindReviewRatingsWithProductName() {
        reviewRepository.findRatingsWithProductName();
    }

    @RepeatedTest(80)
    void testFindReviewsWithCustomerEmail() {
        reviewRepository.findReviewsWithCustomerEmail();
    }

    @RepeatedTest(80)
    void testCalculateOrderItemsTotalPerOrder() {
        orderRepository.findOrderIdAndTotals();
    }

    @RepeatedTest(80)
    void testFindProductsWithCategoryName() {
        productRepository.findProductsWithCategory();
    }

    @RepeatedTest(80)
    void testFindCategoriesWithParentCategory() {
        categoryRepository.findCategoriesWithParent();
    }

    @RepeatedTest(80)
    void testFindOrderProductsAndQuantities() {
        orderRepository.findOrderIdAndProductNameAndQuantities();
    }

    @RepeatedTest(80)
    void testCountOrdersPerCustomerEmail() {
        customerRepository.findCustomersWithOrderCount();
    }

    @RepeatedTest(80)
    void testCalculateAverageRatingPerProduct() {
        productRepository.findAverageRatings();
    }
}
