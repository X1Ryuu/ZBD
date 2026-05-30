package com.example.zbd;

import com.example.zbd.repositories.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
@Transactional
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
        this.customerRepository.findAll();
    }

    @Test
    public void testIdAndEmailOfCustomerSelect(){
        this.customerRepository.getAllWhereEmailLike("@gmail.com");
    }

    @Test
    public void testProductByPriceSelect(){
        this.productRepository.findAllByPriceGreaterThanOrderByPriceDesc(new BigDecimal(100));
    }

    @Test
    public void testNameAndDescriptionOfCategorySelect(){
        this.categoryRepository.findRootCategories();
    }

    @Test
    public void testOrdersByStatusSelect(){
        this.orderRepository.findAllByStatus("SHIPPED");
    }

    @Test
    public void testReviewsByRatingSelect(){
        this.reviewRepository.findAllByRatingGreaterThanEqual(4);
    }

    @Test
    public void testCountOfOrdersSelect(){
        this.orderItemRepository.count();
    }

    @Test
    public void testAvgPriceOfProductSelect(){
        this.productRepository.getAvgPrice();
    }

    @Test
    public void testCustomerCreatedAtSelect(){
        this.customerRepository.findAllByCreatedAtAfter(LocalDateTime.now().minusDays(30));
    }

    @Test
    public void testDistinctBrandOfProductsSelect(){
        this.productRepository.getBrand();
    }
}
