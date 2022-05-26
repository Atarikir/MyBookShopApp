package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.api.response.TagDto;
import com.example.MyBookShopApp.data.tag.TagEntity;
import com.example.MyBookShopApp.mapper.TagMapper;
import com.example.MyBookShopApp.repository.TagRepository;
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

    public List<TagDto> getAllTags() {
        return tagMapper.tagToTagDtoList(tageEntityList());
    }

    public List<TagEntity> tageEntityList() {
        return tagRepository.findAll();
    }
}
