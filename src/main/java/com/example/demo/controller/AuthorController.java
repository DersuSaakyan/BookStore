package com.example.demo.controller;

import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService=authorService;
    }

    @GetMapping()
    public List<Author> getAuthors() {
        return authorService.getAuthors();
    }

    @PostMapping()
    public void addAuthor(Author author) {
        authorService.addAuthor(author);
    }
}
