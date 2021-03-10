package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.model.User;
import com.example.demo.security.CurrentUser;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public List<Book> books() {
        return bookService.books();
    }


    @GetMapping("/title")
    public List<Book> getBooksByTitle(@RequestParam(name = "title") String title) {
        return bookService.getBookByTitle(title);
    }

    @PostMapping("/genre/{id}/author/{author_id}")
    public ResponseEntity<Book> addBook(@RequestBody Book book, @AuthenticationPrincipal UserDetails userDetails,
                                        @PathVariable("id") int id, @PathVariable("author_id") int author_id) throws Exception {
        User loginUser = ((CurrentUser) userDetails).getUser();
        bookService.save(book, loginUser, id, author_id);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @GetMapping("/genre")
    public Set<Book> getBookByGenre(@RequestParam(name = "id") int genreId) {
        return bookService.getBookByGenre(genreId);
    }



}
