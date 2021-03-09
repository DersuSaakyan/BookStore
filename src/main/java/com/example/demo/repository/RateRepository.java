package com.example.demo.repository;

import com.example.demo.model.Book;
import com.example.demo.model.Rate;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RateRepository extends JpaRepository<Rate, Integer> {
    @Query(value = "SELECT MAX(rate.rate) FROM rate WHERE rate.book_id=?1", nativeQuery = true)
    double findMaxRatingByBookId(int id);

    Rate getOneByBookAndUser(Book book, User user);

    @Query(value = "SELECT AVG(rate) AS average FROM rate WHERE book_id=?1 ",nativeQuery = true)
    double getAverageBookRating(int bookId);

//    @Query(value = "Update rate set user_id=?1,book_id=?2 and rate=?3", nativeQuery = true)
//    void updateRate(int usId, int bookId, double rate);
}
