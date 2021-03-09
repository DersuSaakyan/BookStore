package com.example.demo.service;


import com.example.demo.model.*;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.GenreRepository;
import com.example.demo.repository.RateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final RateRepository rateRepository;

    public BookService(BookRepository bookRepository, GenreRepository genreRepository, AuthorRepository authorRepository, RateRepository rateRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.rateRepository = rateRepository;
    }

    public List<Book> books() {
        return bookRepository.findAll();
    }

    public void save(Book book, User user, int id, int authorId) throws Exception {
        if (user == null) {
            throw new Exception("Please login");
        }
        Genre genre = genreRepository.findOneById(id);
        Author author = authorRepository.findOneById(authorId);
        author.getBooksByAuthor().add(book);
        genre.getBooksByGenre().add(book);
        bookRepository.save(book);
    }

    public Set<Book> getBookByGenre(int genreId) {
        Genre genre = genreRepository.findOneById(genreId);
        Set<Book> set = genre.getBooksByGenre();
        return set;
    }


    public List<Book> getBookByTitle(String title){
        return bookRepository.findAllByTitle(title);
    }
}
