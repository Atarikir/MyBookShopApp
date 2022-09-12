package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.api.response.GenreDto;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.example.MyBookShopApp.mapper.GenreMapper;
import com.example.MyBookShopApp.repository.GenreRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GenreService {

  private final GenreMapper genreMapper;
  private final GenreRepository genreRepository;

  public GenreService(GenreMapper genreMapper, GenreRepository genreRepository) {
    this.genreMapper = genreMapper;
    this.genreRepository = genreRepository;
  }

  public GenreDto getGenreDtoBySlug(String slug) {
    return genreMapper.genreToGenreDto(getGenreBySlug(slug));
  }

  private GenreEntity getGenreBySlug(String slug) {
    return genreRepository.findBySlugContaining(slug);
  }

  public List<GenreDto> getGenreParentIdNullList() {
    return genreMapper.genreToGenreDtoList(getGenreEntityParentIdNullList());
  }

  private List<GenreEntity> getGenreEntityParentIdNullList() {
    return genreRepository.findByParentIdIsNull();
  }
}
