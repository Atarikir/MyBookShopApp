package com.example.service;

import com.example.api.response.TagDto;
import com.example.data.tag.TagEntity;
import com.example.mapper.TagMapper;
import com.example.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

  private final TagMapper tagMapper;
  private final TagRepository tagRepository;

  public TagService(TagMapper tagMapper, TagRepository tagRepository) {
    this.tagMapper = tagMapper;
    this.tagRepository = tagRepository;
  }

  public TagDto getTagDtoBySlug(String slug) {
    return tagMapper.tagToTagDto(getTagBySlug(slug));
  }

  public List<TagDto> getAllTags() {
    return tagMapper.tagToTagDtoList(tageEntityList());
  }

  private TagEntity getTagBySlug(String slug) {
    return tagRepository.findBySlugContaining(slug);
  }

  private List<TagEntity> tageEntityList() {
    return tagRepository.findAll();
  }
}
