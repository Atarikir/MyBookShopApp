package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.author.AuthorEntity;
import com.example.MyBookShopApp.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    public Map<String, List<AuthorEntity>> getAuthorsMap() {
        List<AuthorEntity> authorEntities = authorRepository.findAll();
        return authorEntities.stream().collect(Collectors.groupingBy((AuthorEntity a) -> a.getName().substring(0, 1)));
    }
}
