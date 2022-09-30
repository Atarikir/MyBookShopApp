package com.example.service;

import com.example.api.response.AuthorDto;
import com.example.data.author.AuthorEntity;
import com.example.mapper.AuthorMapper;
import com.example.repository.AuthorRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

  private final AuthorRepository authorRepository;
  private final AuthorMapper authorMapper;

  public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
    this.authorRepository = authorRepository;
    this.authorMapper = authorMapper;
  }

  public Map<String, List<AuthorEntity>> getAuthorsMap() {
    List<AuthorEntity> authorEntities = authorRepository.findAll();
    return authorEntities.stream()
        .collect(Collectors.groupingBy((AuthorEntity a) -> a.getName().substring(0, 1)));
  }


  public AuthorDto getAuthorDtoBySlug(String slug) {
    return authorMapper.authorToAuthorDto(getAuthorBySlug(slug));
  }

  private AuthorEntity getAuthorBySlug(String slug) {
    return authorRepository.findBySlugContaining(slug);
  }

  public List<AuthorDto> getAuthorListByBookSlug(String bookSlug) {
    List<AuthorEntity> authorEntities = authorRepository.getAuthorsByBookSlug(bookSlug);
    return authorMapper.listEntityToDtoList(authorEntities);
  }
}
