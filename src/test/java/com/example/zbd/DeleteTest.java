package com.example.zbd;

import com.example.zbd.repositories.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
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

    @Test
    public void testDeleteCustomerById(){
        customerRepository.deleteAllById(10L);
    }

    @Test
    public void testDeleteOrderByStatus(){
        orderRepository.deleteAllByStatus("CANCELED");
    }
    @Test
    public void testDeleteProductByActive(){
        productRepository.deleteAllByIsActive(Boolean.FALSE);
    }

    @Test
    public void testDeleteReviewByRating(){
        reviewRepository.deleteAllByRatingLessThanEqual(2);
    }

    @Test
    public void testDeleteCategoryById(){
        categoryRepository.deleteAllById(5L);
    }
    @Test
    public void testDeleteOrderItemById(){
        orderItemRepository.deleteAllById(1L);
    }
    @Test
    public void testDeleteCustomerByEmailFragment(){
        customerRepository.deleteAllByEmailContaining("spam");
    }
    @Test
    public void testDeleteOrderByCreationInterval(){
        orderRepository.deleteAllByCreatedAtBefore(LocalDateTime.now().minusYears(1));
    }
    @Test
    public void testDeleteProductByPriceGT(){
        productRepository.deleteAllByPriceGreaterThan(new BigDecimal(10000));
    }
    @Test
    public void testDeleteReviewsByCreationInterval(){
        reviewRepository.deleteAllByCreatedAtBefore(LocalDateTime.now().minusMonths(6));
    }
}
