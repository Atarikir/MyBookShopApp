package com.example.mapper;

import com.example.api.response.AuthorDto;
import com.example.data.author.AuthorEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

  AuthorDto authorToAuthorDto(AuthorEntity authorEntity);

  List<AuthorDto> listEntityToDtoList(List<AuthorEntity> authorEntityList);
}
