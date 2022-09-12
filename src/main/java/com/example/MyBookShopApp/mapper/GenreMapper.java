package com.example.MyBookShopApp.mapper;

import com.example.MyBookShopApp.api.response.GenreDto;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {

  GenreDto genreToGenreDto(GenreEntity genreEntity);

  List<GenreDto> genreToGenreDtoList(List<GenreEntity> genreEntityList);
}
