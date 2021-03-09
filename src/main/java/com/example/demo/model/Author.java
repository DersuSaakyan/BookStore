package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String country;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "date_of_died")
    private String dateOfDied;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "bookauthor",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    Set<Book> booksByAuthor=new HashSet<>();
}
