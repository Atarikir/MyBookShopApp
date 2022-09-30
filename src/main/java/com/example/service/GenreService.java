package com.example.service;

import com.example.api.response.GenreDto;
import com.example.data.genre.GenreEntity;
import com.example.mapper.GenreMapper;
import com.example.repository.GenreRepository;
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
