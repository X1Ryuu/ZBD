package com.example.zbd.repositories;

import com.example.zbd.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewsByProductId(Long productId);
    public void deleteAllByRatingLessThanEqual(Integer rating);
    public void deleteAllByCreatedAtBefore(LocalDateTime createdAt);

    List<Review> findAllByRatingGreaterThanEqual(Integer rating);
}
