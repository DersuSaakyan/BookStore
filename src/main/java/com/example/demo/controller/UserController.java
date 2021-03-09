package com.example.demo.controller;


import com.example.demo.model.Book;
import com.example.demo.model.User;
import com.example.demo.service.dto.LoginRequests;
import com.example.demo.service.GenreService;
import com.example.demo.model.Genre;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.LoginResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final GenreService genreService;
    private final UserService userService;

    @Autowired
    public UserController(GenreService genreService, UserService userService) {
        this.genreService = genreService;
        this.userService = userService;
    }

    @GetMapping("/")
    public List<User> users(){
        return userService.users();
    }

    @PostMapping("/genres")
    public ResponseEntity<Genre> addGenre(@RequestBody Genre genre) {
        genreService.addGenre(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(genre);
    }

    @PostMapping("/")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    @PostMapping("/admin/users")
    public ResponseEntity<User> addAdminUser(@RequestBody User user) throws Exception {
        userService.saveAdmin(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/auth")
    public ResponseEntity<LoginResponses> auth(@RequestBody LoginRequests loginRequests) throws Exception {
        LoginResponses loginResponses = userService.auth(loginRequests);
        if (loginResponses == null) {
            throw new Exception("User not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponses);
    }


}
