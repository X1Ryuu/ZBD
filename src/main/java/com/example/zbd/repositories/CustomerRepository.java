package com.example.zbd.repositories;

import com.example.zbd.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findCustomerByEmail(String email);
    Customer findCustomerById(Long id);
    void deleteAllById(Long id);
    void deleteAllByEmailContaining(String email);
    @Query("""
SELECT c.id, c.email
FROM Customer c
WHERE c.email LIKE %:domain
""")
    List<Object[]> getAllWhereEmailLike(@Param("domain") String domain);


    List<Customer> findAllByCreatedAtAfter(LocalDateTime after);

}
