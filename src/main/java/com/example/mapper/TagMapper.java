package com.example.mapper;

import com.example.api.response.TagDto;
import com.example.data.tag.TagEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

  TagDto tagToTagDto(TagEntity tagEntity);

  List<TagDto> tagToTagDtoList(List<TagEntity> tagEntityList);
}
