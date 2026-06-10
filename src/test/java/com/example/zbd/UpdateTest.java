package com.example.zbd;

import com.example.zbd.repositories.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

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

    @BeforeEach
    void separator(TestInfo testInfo, RepetitionInfo repetitionInfo) throws IOException {
        int current = repetitionInfo.getCurrentRepetition();
        int total = repetitionInfo.getTotalRepetitions();

        if(current == total){
            Files.writeString(Path.of("query_metrics.csv"),"\n", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }

    }

    @RepeatedTest(80)
    void testUpdateCustomerFirstName() {
        customerRepository.updateCustomerName("Jane", 1L);
    }

    @RepeatedTest(80)
    void testIncreaseProductPrices() {
        productRepository.increasePricesByXPercent(new BigDecimal(10));
    }

    @RepeatedTest(80)
    void testUpdateOrderStatus() {
        orderRepository.markOrderAsShipped(1L);
    }

    @RepeatedTest(80)
    void testRemoveParentCategory() {
        categoryRepository.removeParentCategory(1L);
    }

    @RepeatedTest(80)
    void testUpdateReviewRating() {
        reviewRepository.updateRating(4, 1L);
    }

    @RepeatedTest(80)
    void testUpdateCustomerTimestamp() {
        customerRepository.updateCustomerTimestamp(1L);
    }

    @RepeatedTest(80)
    void testDeactivateCheapProducts() {
        productRepository.deactivateCheapProducts(new BigDecimal(10));
    }

    @RepeatedTest(80)
    void testUpdateShippedDate() {
        orderRepository.updateShippedDate();
    }

    @RepeatedTest(80)
    void testRecalculateOrderItemTotalPrice() {
        orderItemRepository.recalculateTotalPrices();
    }

    @RepeatedTest(80)
    void testUpdateCustomerPhoneByEmail() {
        customerRepository.updatePhoneByEmail("123456789", "john@example.com");
    }

}
