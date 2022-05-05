package com.example.MyBookShopApp.mapper;

import com.example.MyBookShopApp.api.response.BookDto;
import com.example.MyBookShopApp.data.author.AuthorEntity;
import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.data.book.links.Book2AuthorEntity;
import com.example.MyBookShopApp.repository.AuthorRepository;
import com.example.MyBookShopApp.repository.Book2AuthorRepository;
import com.example.MyBookShopApp.service.AuthorService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    @Autowired
    protected AuthorService authorService;
    @Autowired
    protected Book2AuthorRepository book2AuthorRepository;
    @Autowired
    protected AuthorRepository authorRepository;


//    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    public BookDto bookEntityToBookDTO(BookEntity book) {
        if (book == null) {
            return null;
        }

        String authorString = null;
//        int authorId = book2AuthorRepository.getAuthorBySortIndex(book);
//        AuthorEntity author = authorRepository.findById(authorId);
//        List<String> authors = new ArrayList<>();
        List<AuthorEntity> authorList = book.getAuthorEntityList();
//        authorList.stream().forEach(a -> authors.add(a.getName()));
        if (authorList.size() == 1) {
            authorString = authorList.get(0).getName();
        }
        if (authorList.size() > 1) {
            authorString = " и другие";
        }

        BookDto bookDTO = new BookDto();
        bookDTO.setId(book.getId());
        bookDTO.setSlug(book.getSlug());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setImage(book.getImage());
        bookDTO.setAuthors(authorString);
        bookDTO.setDiscount(Integer.valueOf(book.getDiscount()));
        bookDTO.setIsBestseller(book.getIsBestseller());
        bookDTO.setRating(0);
        bookDTO.setStatus(String.valueOf(false));
        bookDTO.setPrice(book.getPrice());
        bookDTO.setDiscountPrice(book.getPrice() - book.getDiscount() * book.getPrice() / 100);

        return bookDTO;
    }
}
