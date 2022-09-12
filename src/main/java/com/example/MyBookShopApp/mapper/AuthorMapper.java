package com.example.MyBookShopApp.mapper;

import com.example.MyBookShopApp.api.response.AuthorDto;
import com.example.MyBookShopApp.data.author.AuthorEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

  AuthorDto authorToAuthorDto(AuthorEntity authorEntity);

  List<AuthorDto> listEntityToDtoList(List<AuthorEntity> authorEntityList);
}
