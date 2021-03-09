package com.example.demo.repository;


import com.example.demo.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Genre findOneById(int id);
}
