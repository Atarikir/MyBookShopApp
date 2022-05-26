package com.example.MyBookShopApp.mapper;

import com.example.MyBookShopApp.api.response.BookDto;
import com.example.MyBookShopApp.data.author.AuthorEntity;
import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.repository.*;
import com.example.MyBookShopApp.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

@Slf4j
@Mapper(componentModel = "spring")
public abstract class BookMapper {

    @Autowired
    protected AuthorService authorService;
    @Autowired
    protected Book2AuthorRepository book2AuthorRepository;
    @Autowired
    protected AuthorRepository authorRepository;

    public BookDto bookEntityToBookDTO(BookEntity book) {
        if (book == null) {
            return null;
        }

        BookDto bookDTO = new BookDto();
        bookDTO.setId(book.getId());
        bookDTO.setSlug(book.getSlug());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setImage(book.getImage());
        bookDTO.setAuthors(getAuthors(book));
        bookDTO.setDiscount(Integer.valueOf(book.getDiscount()));
        bookDTO.setIsBestseller(book.getIsBestseller());
        bookDTO.setRating(Integer.valueOf(book.getBookRating()));
        bookDTO.setStatus(getStatusBook(book));
        bookDTO.setPrice(book.getPrice());
        bookDTO.setDiscountPrice(getDiscountPrice(book));

        return bookDTO;
    }

    private Integer getDiscountPrice(BookEntity book) {
        int newPrice = (book.getDiscount() * book.getPrice()) / 100;
        return book.getPrice() - newPrice;
    }

    private String getAuthors(BookEntity book) {
        String authorString = "";
        Integer bookId = book2AuthorRepository.getBookId(book.getId());
        if (bookId != null) {
            int authorId = book2AuthorRepository.getAuthorIdBySortIndex(bookId);
            AuthorEntity author = authorRepository.findById(authorId);
            List<AuthorEntity> authorList = book.getAuthorEntityList();
            if (authorList.size() == 1) {
                authorString = authorList.get(0).getName();
            }
            if (authorList.size() > 1) {
                authorString = author.getName() + " и другие";
            }
        }

        return authorString;
    }

    //TODO: переделать поиск текущего пользователя, когда будет реализована аутентификация
    private String getStatusBook(BookEntity book) {
        return String.valueOf(false);
    }
}
