package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.model.Rate;
import com.example.demo.model.User;
import com.example.demo.security.CurrentUser;
import com.example.demo.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RateController {
    private final RateService rateService;

    @Autowired
    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @PostMapping("/{id}/rate")
    public ResponseEntity<String> addRateBook(@PathVariable("id") int id, @RequestBody Rate rating
            , @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        User loginUser = ((CurrentUser) userDetails).getUser();
        rateService.addRatingBooks(id, loginUser, rating);
        return ResponseEntity.ok("Rate is success");
    }

    @GetMapping()
    public List<Rate> getRatings() {
        return rateService.rateList();
    }

    @GetMapping("/genre")
    public Book getBookByMaxGenre(@RequestParam(name = "id") int id) {
        return rateService.getBookByMaxGenre(id);
    }

    @GetMapping("/get-avg-book-rating")
    public double getAvgBookRating(@RequestParam("id") int id) {
        return rateService.getBookAverageRating(id);
    }
}
