package com.example.zbd.repositories;

import com.example.zbd.entities.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findCustomerByEmail(String email);
    Customer findCustomerById(Long id);






    //--------------------------------- select
    @Query("""
SELECT c.id, c.email
FROM Customer c
WHERE c.email LIKE %:domain
""")
    List<Object[]> getAllWhereEmailLike(@Param("domain") String domain);


    List<Customer> findAllByCreatedAtAfter(LocalDateTime after);

    //--------------------------------- update
    @Modifying
    @Transactional
    @Query("""
           UPDATE Customer c
           SET c.firstName = :name
           WHERE c.id = :id
           """)
    int updateCustomerName(@Param("name") String newName, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query("""
           UPDATE Customer c
           SET c.updatedAt = CURRENT_TIMESTAMP
           WHERE c.id = :id
           """)
    int updateCustomerTimestamp(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("""
           UPDATE Customer c
           SET c.phone = :phone
           WHERE c.email = :email
           """)
    int updatePhoneByEmail(@Param("phone") String newPhone, @Param("email") String givenEmail);

    //--------------------------------- delete
    void deleteAllById(Long id);
    void deleteAllByEmailContaining(String email);

    //--------------------------------- insert


    //--------------------------------- aggregation
    //count


    //--------------------------------- join
    @Query("""
           SELECT c.email, COUNT(o.id)
           FROM Customer c
           LEFT JOIN c.orders o
           GROUP BY c.email
           """)
    List<Object[]> findCustomersWithOrderCount();
}
