package com.example.mapper;

import com.example.api.response.GenreDto;
import com.example.data.genre.GenreEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {

  GenreDto genreToGenreDto(GenreEntity genreEntity);

  List<GenreDto> genreToGenreDtoList(List<GenreEntity> genreEntityList);
}
