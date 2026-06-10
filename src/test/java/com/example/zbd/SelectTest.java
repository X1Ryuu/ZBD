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
public class SelectTest {
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
    public void testCustomerSelect(){
        customerRepository.findAll();
    }

    @Test
    public void testIdAndEmailOfCustomerSelect(){
        customerRepository.getAllWhereEmailLike("@gmail.com");
    }

    @Test
    public void testProductByPriceSelect(){
        productRepository.findAllByPriceGreaterThanOrderByPriceDesc(new BigDecimal(100));
    }

    @Test
    public void testNameAndDescriptionOfCategorySelect(){
        categoryRepository.findRootCategories();
    }

    @Test
    public void testOrdersByStatusSelect(){
        orderRepository.findAllByStatus("SHIPPED");
    }

    @Test
    public void testReviewsByRatingSelect(){
        reviewRepository.findAllByRatingGreaterThanEqual(4);
    }

    @Test
    public void testCountOfOrdersSelect(){
        orderItemRepository.count();
    }

    @Test
    public void testAvgPriceOfProductSelect(){
        productRepository.getAvgPrice();
    }

    @Test
    public void testCustomerCreatedAtSelect(){
        customerRepository.findAllByCreatedAtAfter(LocalDateTime.now().minusDays(30));
    }

    @Test
    public void testDistinctBrandOfProductsSelect(){
        productRepository.getBrand();
    }
}
