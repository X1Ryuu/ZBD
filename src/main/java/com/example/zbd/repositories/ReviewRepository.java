package com.example.zbd.repositories;

import com.example.zbd.entities.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewsByProductId(Long productId);
    public void deleteAllByRatingLessThanEqual(Integer rating);
    public void deleteAllByCreatedAtBefore(LocalDateTime createdAt);

    List<Review> findAllByRatingGreaterThanEqual(Integer rating);


    //--------------------------------- select


    //--------------------------------- update
    @Modifying
    @Transactional
    @Query("""
           UPDATE Review r
           SET r.rating = :rating
           WHERE r.id = :id
           """)
    int updateRating(@Param("rating") int rating, @Param("id") Long id);

    //--------------------------------- delete


    //--------------------------------- insert


    //--------------------------------- aggregation
    @Query("""
       SELECT AVG(r.rating)
       FROM Review r
       """)
    Double getAverageRating();


    //--------------------------------- join
    @Query("""
           SELECT r.rating, p.name
           FROM Review r
           JOIN r.product p
           """)
    List<Object[]> findRatingsWithProductName();

    @Query("""
           SELECT r, c.email
           FROM Review r
           JOIN r.customer c
           """)
    List<Object[]> findReviewsWithCustomerEmail();
}
