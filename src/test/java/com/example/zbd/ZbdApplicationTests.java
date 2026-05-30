package com.example.zbd;

import com.example.zbd.entities.Customer;
import com.example.zbd.entities.Order;
import com.example.zbd.entities.OrderItem;
import com.example.zbd.entities.Product;
import com.example.zbd.repositories.CustomerRepository;
import com.example.zbd.repositories.OrderRepository;
import com.example.zbd.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class ZbdApplicationTests {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * SEARCH BY PRIMARY KEY
	 */
	@Test
	void findProductByIdTest() {

		long start = System.currentTimeMillis();

		Product product =
				productRepository.findById(1L)
						.orElseThrow();

		System.out.println(product.getName());

		long end = System.currentTimeMillis();

		System.out.println("==================================");
		System.out.println("FIND PRODUCT BY ID TEST");
		System.out.println("TIME: " + (end - start) + " ms");
		System.out.println("==================================");
	}

	/**
	 * INSERT TEST
	 */
	@Test
	void insertProductTest() {

		long start = System.currentTimeMillis();

		Product product = new Product();

		product.setName("Performance Test Product");
		product.setDescription("Test description");
		product.setSku("TEST-SKU-999");
		product.setBrand("Test Brand");
		product.setPrice(BigDecimal.valueOf(999.99));
		product.setWeight(BigDecimal.valueOf(2.5));
		product.setIsActive(true);
		product.setCreatedAt(LocalDateTime.now());
		product.setUpdatedAt(LocalDateTime.now());

		productRepository.save(product);

		long end = System.currentTimeMillis();

		System.out.println("==================================");
		System.out.println("INSERT PRODUCT TEST");
		System.out.println("TIME: " + (end - start) + " ms");
		System.out.println("==================================");
	}

	/**
	 * UPDATE TEST
	 */
	@Test
	void updateProductTest() {

		long start = System.currentTimeMillis();

		Product product =
				productRepository.findById(1L)
						.orElseThrow();

		product.setPrice(BigDecimal.valueOf(4999.99));
		product.setUpdatedAt(LocalDateTime.now());

		productRepository.save(product);

		long end = System.currentTimeMillis();

		System.out.println("==================================");
		System.out.println("UPDATE PRODUCT TEST");
		System.out.println("TIME: " + (end - start) + " ms");
		System.out.println("==================================");
	}

	/**
	 * 1:N RELATION
	 *
	 * Customer -> Orders
	 * Order -> OrderItems
	 */
	@Test
	void oneToManyRelationTest() {

		long start = System.currentTimeMillis();

		List<Order> orders = orderRepository.findAll();

		int itemsCounter = 0;

		for (Order order : orders) {

			System.out.println(
					"ORDER ID: " + order.getId()
			);

			System.out.println(
					"CUSTOMER: " +
							order.getCustomer().getEmail()
			);

			for (OrderItem item : order.getItems()) {

				System.out.println(
						"PRODUCT: " +
								item.getProduct().getName()
				);

				itemsCounter++;
			}
		}

		long end = System.currentTimeMillis();

		System.out.println("==================================");
		System.out.println("ONE TO MANY TEST");
		System.out.println("ORDERS: " + orders.size());
		System.out.println("ITEMS: " + itemsCounter);
		System.out.println("TIME: " + (end - start) + " ms");
		System.out.println("==================================");
	}

	/**
	 * N:M MANY RELATION
	 *
	 * Customer <-> Wishlist <-> Product
	 */
	@Test
	void manyToManyRelationTest() {

		long start = System.currentTimeMillis();

		List<Customer> customers =
				customerRepository.findAll();

		int wishlistCounter = 0;

		for (Customer customer : customers) {

			System.out.println(
					"CUSTOMER: " +
							customer.getEmail()
			);

			for (Product product : customer.getWishlist()) {

				System.out.println(
						"WISHLIST PRODUCT: " +
								product.getName()
				);

				wishlistCounter++;
			}
		}

		long end = System.currentTimeMillis();

		System.out.println("==================================");
		System.out.println("MANY TO MANY TEST");
		System.out.println("CUSTOMERS: " + customers.size());
		System.out.println("WISHLIST ITEMS: " + wishlistCounter);
		System.out.println("TIME: " + (end - start) + " ms");
		System.out.println("==================================");
	}

}
