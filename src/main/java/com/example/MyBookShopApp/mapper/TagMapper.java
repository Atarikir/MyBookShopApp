package com.example.MyBookShopApp.mapper;

import com.example.MyBookShopApp.api.response.TagDto;
import com.example.MyBookShopApp.data.tag.TagEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDto tagToTagDto(TagEntity tagEntity);

    List<TagDto> tagToTagDtoList(List<TagEntity> tagEntityList);
}
