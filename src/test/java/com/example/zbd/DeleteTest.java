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
import java.time.LocalDateTime;

@SpringBootTest
@Transactional
@Sql(
        scripts = "/small.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class DeleteTest {
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
    public void testDeleteCustomerById(){
        customerRepository.deleteAllById(10L);
    }

    @RepeatedTest(80)
    public void testDeleteOrderByStatus(){
        orderRepository.deleteAllByStatus("CANCELED");
    }
    @RepeatedTest(80)
    public void testDeleteProductByActive(){
        productRepository.deleteAllByIsActive(Boolean.FALSE);
    }

    @RepeatedTest(80)
    public void testDeleteReviewByRating(){
        reviewRepository.deleteAllByRatingLessThanEqual(2);
    }

    @RepeatedTest(80)
    public void testDeleteCategoryById(){
        categoryRepository.deleteAllById(5L);
    }
    @RepeatedTest(80)
    public void testDeleteOrderItemById(){
        orderItemRepository.deleteAllById(1L);
    }
    @RepeatedTest(80)
    public void testDeleteCustomerByEmailFragment(){
        customerRepository.deleteAllByEmailContaining("spam");
    }
    @RepeatedTest(80)
    public void testDeleteOrderByCreationInterval(){
        orderRepository.deleteAllByCreatedAtBefore(LocalDateTime.now().minusYears(1));
    }
    @RepeatedTest(80)
    public void testDeleteProductByPriceGT(){
        productRepository.deleteAllByPriceGreaterThan(new BigDecimal(10000));
    }
    @RepeatedTest(80)
    public void testDeleteReviewsByCreationInterval(){
        reviewRepository.deleteAllByCreatedAtBefore(LocalDateTime.now().minusMonths(6));
    }
}
