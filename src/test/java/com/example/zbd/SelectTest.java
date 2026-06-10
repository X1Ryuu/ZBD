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

    private String lastTestName = "";

    @BeforeEach
    void separator(TestInfo testInfo, RepetitionInfo repetitionInfo) throws IOException {
        int current = repetitionInfo.getCurrentRepetition();
        int total = repetitionInfo.getTotalRepetitions();

        if(current == total){
            Files.writeString(Path.of("query_metrics.csv"),"\n", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }

    }

    @RepeatedTest(80)
    public void testCustomerSelect(){
        customerRepository.findAll();
    }

    @RepeatedTest(80)
    public void testIdAndEmailOfCustomerSelect(){
        customerRepository.getAllWhereEmailLike("@gmail.com");
    }

    @RepeatedTest(80)
    public void testProductByPriceSelect(){
        productRepository.findAllByPriceGreaterThanOrderByPriceDesc(new BigDecimal(100));
    }

    @RepeatedTest(80)
    public void testNameAndDescriptionOfCategorySelect(){
        categoryRepository.findRootCategories();
    }

    @RepeatedTest(80)
    public void testOrdersByStatusSelect(){
        orderRepository.findAllByStatus("SHIPPED");
    }

    @RepeatedTest(80)
    public void testReviewsByRatingSelect(){
        reviewRepository.findAllByRatingGreaterThanEqual(4);
    }

    @RepeatedTest(80)
    public void testCountOfOrdersSelect(){
        orderItemRepository.count();
    }

    @RepeatedTest(80)
    public void testAvgPriceOfProductSelect(){
        productRepository.getAvgPrice();
    }

    @RepeatedTest(80)
    public void testCustomerCreatedAtSelect(){
        customerRepository.findAllByCreatedAtAfter(LocalDateTime.now().minusDays(30));
    }

    @RepeatedTest(80)
    public void testDistinctBrandOfProductsSelect(){
        productRepository.getBrands();
    }
}
