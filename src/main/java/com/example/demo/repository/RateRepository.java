package com.example.demo.repository;

import com.example.demo.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RateRepository extends JpaRepository<Rate, Integer> {
    @Query(value = "SELECT MAX(rate.rate) FROM rate WHERE rate.book_id=?1", nativeQuery = true)
    double findMaxRatingByBookId(int id);
}
