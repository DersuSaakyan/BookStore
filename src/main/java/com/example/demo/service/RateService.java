package com.example.demo.service;


import com.example.demo.model.Book;
import com.example.demo.model.Genre;
import com.example.demo.model.Rate;
import com.example.demo.model.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.GenreRepository;
import com.example.demo.repository.RateRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RateService {
    private final RateRepository rateRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    public RateService(RateRepository rateRepository, BookRepository bookRepository, GenreRepository genreRepository) {
        this.rateRepository = rateRepository;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
    }

    public List<Rate> rateList() {
        return rateRepository.findAll();
    }

    public void addRatingBooks(int id, User user, Rate rating) {

        Book book = bookRepository.findOneById(id);
        rating.setBook(book);
        rating.setUser(user);
        rateRepository.save(rating);
    }

    public Book getBookByMaxGenre(int id) {
        Genre genre = genreRepository.findOneById(id);
        Set<Book> set = genre.getBooksByGenre();
        double max = 0.0;
        Book resultBook = null;
        for (Book book : set) {
            double d=rateRepository.findMaxRatingByBookId(book.getId());
            if(max<d){
                max=d;
                resultBook=book;
            }
        }
        return resultBook;
    }
}
