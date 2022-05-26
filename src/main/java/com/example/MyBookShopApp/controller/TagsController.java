package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.api.response.BooksPageResponse;
import com.example.MyBookShopApp.repository.TagRepository;
import com.example.MyBookShopApp.service.BookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TagsController {

    @Value("${value.offset}")
    private int offset;
    @Value("${value.limit}")
    private int limit;
    private final BookService bookService;
    private final TagRepository tagRepository;


    public TagsController(BookService bookService, TagRepository tagRepository) {
        this.bookService = bookService;
        this.tagRepository = tagRepository;
    }

    @GetMapping(value = "/tags/{slug}")
    public String getTagPage(@PathVariable("slug") String slug, Model model) {
        model.addAttribute("tagBySlug", tagRepository.findBySlugContaining(slug));
        model.addAttribute("booksByTagSlug", bookService.getBooksByTagSlugList(slug, offset, limit));
        return "tags/index";
    }

    @GetMapping(value = "/books/tag/{id}")
    @ResponseBody
    public ResponseEntity<BooksPageResponse> getBooksByTagId(@PathVariable(value = "id", required = false) String id,
                                                             @RequestParam(value = "offset", required = false) Integer offset,
                                                             @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.ok(bookService.getPageOfBooksByTagId(id, offset, limit));
    }
}
