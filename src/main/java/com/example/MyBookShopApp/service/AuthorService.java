package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.repository.AuthorJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorJdbcRepository authorJdbcRepository;

    @Autowired
    public AuthorService(AuthorJdbcRepository authorJdbcRepository) {
        this.authorJdbcRepository = authorJdbcRepository;
    }

    public Map<String, List<Author>> getAuthorsMap() {
        List<Author> authors = authorJdbcRepository.getAll();
        return authors.stream().collect(Collectors.groupingBy((Author a) -> {
            return a.getLastName().substring(0, 1);
        }));
    }
}
