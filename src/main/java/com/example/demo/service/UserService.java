package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.model.User;

import com.example.demo.model.UserType;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CurrentUser;
import com.example.demo.security.JwtTokenUtil;
import com.example.demo.service.dto.LoginRequests;
import com.example.demo.service.dto.LoginResponses;
import com.example.demo.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final BookRepository bookRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.bookRepository = bookRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findOneByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", s));
        } else
            return new CurrentUser(user);
    }

    public LoginResponses auth(LoginRequests loginRequests) {
        User user = userRepository.findOneByEmail(loginRequests.getEmail());
        if (user != null && passwordEncoder.matches(loginRequests.getPassword(), user.getPassword())) {
            String token = jwtTokenUtil.generateToken(user.getEmail());
            return LoginResponses.builder()
                    .token(token)
                    .userDto(UserDto.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .surname(user.getSurname())
                            .email(user.getEmail())
                            .type(user.getType())
                            .build())
                    .build();
        }
        return null;
    }

    public void save(User user) {
        if (userRepository.findOneByEmail(user.getEmail()) != null) {
            try {
                throw new Exception("Email is busy");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setType(UserType.USER);
            userRepository.save(user);
        }
    }

    public void saveAdmin(User user) throws Exception {
        if (user.getType() == null) {
            throw new Exception("Please check type ADMIN or EDITOR");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<User> users() {
        return userRepository.findAll();
    }

    public void addBookMyFavoriteList(User user, int bookId) {
        Book book = bookRepository.findOneById(bookId);
        user.getMyFavoriteBook().add(book);
        userRepository.save(user);
    }

    public Set<Book> getMyFavoriteBook(User user) {
        return user.getMyFavoriteBook();
    }
}
