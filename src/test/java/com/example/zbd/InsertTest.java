package com.example.zbd;

import com.example.zbd.entities.*;
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
public class InsertTest {
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
    void testCreateCategoryWithDescription() {
        Category cat = new Category();
        cat.setName("Electronics");
        cat.setDescription("Devices");
        cat.setCreatedAt(LocalDateTime.now());
        categoryRepository.save(cat);
    }

    @Test
    void testCreateCategoryWithParentCategory() {
        Category cat = new Category();
        cat.setName("Phones");
        cat.setParentCategory(categoryRepository.findCategoryById(1L));
        cat.setCreatedAt(LocalDateTime.now());
        categoryRepository.save(cat);
    }

    @Test
    void testCreateCustomer() {
        Customer customer = new Customer();
        LocalDateTime now = LocalDateTime.now();
        customer.setCreatedAt(now);
        customer.setUpdatedAt(now);
        customer.setEmail("john2@example.com");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerRepository.save(customer);
    }

    @Test
    void testCreateProduct() {
        Product prod = new Product();
        prod.setName("iPhone");
        prod.setPrice(BigDecimal.valueOf(999.99));
        prod.setSku("IP-NEW-001");
        prod.setIsActive(true);
        LocalDateTime now = LocalDateTime.now();
        prod.setCreatedAt(now);
        prod.setUpdatedAt(now);
        productRepository.save(prod);
    }

    @Test
    void testCreateOrder() {
        Customer customer = customerRepository.findCustomerById(1L);
        Order order = new Order();
        order.setStatus("PENDING");
        order.setTotalAmount(BigDecimal.valueOf(100));
        order.setCustomer(customer);
        LocalDateTime now = LocalDateTime.now();
        order.setCreatedAt(now);
        order.setUpdatedAt(now);
        orderRepository.save(order);
    }

    @Test
    void testCreateOrderItem() {
        Order order = orderRepository.findOrderById(1L);
        Product product = productRepository.findProductById(1L);

        OrderItem item = new OrderItem();
        item.setQuantity(2);
        item.setUnitPrice(BigDecimal.valueOf(50.00));
        item.setTotalPrice(BigDecimal.valueOf(100.00));
        item.setOrder(order);
        item.setProduct(product);

        orderItemRepository.save(item);
    }

    @Test
    void testCreateReview() {
        Customer customer = customerRepository.findCustomerById(1L);
        Product product = productRepository.findProductById(1L);

        Review review = new Review();
        review.setRating(5);
        review.setTitle("Great!");
        review.setCustomer(customer);
        review.setProduct(product);
        review.setCreatedAt(LocalDateTime.now());

        reviewRepository.save(review);
    }

    @Test
    void testCreateCustomerWithEmailOnly() {
        Customer customer = new Customer();
        customer.setEmail("unique2@test.com");
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        customerRepository.save(customer);
    }

    @Test
    void testCreateProductWithCategory() {
        Category category = categoryRepository.findCategoryById(1L);
        Product product = new Product();
        product.setName("Laptop");
        product.setSku("LP-NEW-001");
        product.setPrice(BigDecimal.valueOf(1500.00));
        product.setCategory(category);
        product.setIsActive(true);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        productRepository.save(product);
    }

    @Test
    void testCreateOrderWithSubtotalAndTax() {
        Order order = new Order();
        order.setStatus("NEW");
        order.setSubtotal(BigDecimal.valueOf(80.00));
        order.setTaxAmount(BigDecimal.valueOf(20.00));
        order.setTotalAmount(BigDecimal.valueOf(100.00));
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);
    }
}
