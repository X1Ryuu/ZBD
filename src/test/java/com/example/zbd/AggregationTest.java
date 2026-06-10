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

import static org.aspectj.lang.reflect.DeclareAnnotation.Kind.Method;

@SpringBootTest
@Transactional
@Sql(
        scripts = "/small.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
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


    @BeforeEach
    void separator(TestInfo testInfo, RepetitionInfo repetitionInfo) throws IOException {
        int current = repetitionInfo.getCurrentRepetition();
        int total = repetitionInfo.getTotalRepetitions();

        if(current == total){
            Files.writeString(Path.of("query_metrics.csv"),"\n", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }

    }

    @RepeatedTest(80)
    void testCountCustomers() {
        customerRepository.count();
    }

    @RepeatedTest(80)
    void testSumTotalAmount() {
        orderRepository.getTotalRevenue();
    }

    @RepeatedTest(80)
    void testAverageRating() {
        reviewRepository.getAverageRating();
    }

    @RepeatedTest(80)
    void testMaxPrice() {
        productRepository.getMaxPrice();
    }

    @RepeatedTest(80)
    void testMinPrice() {
        productRepository.getMinPrice();
    }

    @RepeatedTest(80)
    void testOrderCountPerCustomer() {
        orderRepository.getOrderCountPerCustomer();
    }

    @RepeatedTest(80)
    void testTotalInventoryPerProduct() {
        orderItemRepository.getTotalSoldPerProduct();
    }

    @RepeatedTest(80)
    void testOrderCountByStatus() {
        orderRepository.getOrdersByStatus();
    }

    @RepeatedTest(80)
    void testAveragePricePerCategory() {
        productRepository.getAveragePricePerCategory();
    }

    @RepeatedTest(80)
    void testOrdersPerDay() {
        orderRepository.getOrdersPerDay();
    }


}
