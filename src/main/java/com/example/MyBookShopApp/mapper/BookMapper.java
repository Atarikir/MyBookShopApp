package com.example.MyBookShopApp.mapper;

import com.example.MyBookShopApp.api.response.BookDto;
import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.service.AuthorService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    @Autowired
    protected AuthorService authorService;

//    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    public BookDto bookEntityToBookDTO(BookEntity book) {
        if (book == null) {
            return null;
        }
        BookDto bookDTO = new BookDto();
        bookDTO.setId(book.getId());
        bookDTO.setSlug(book.getSlug());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setImage(book.getImage());
        bookDTO.setAuthors(authorService.getAuthorsByBook(book));
        bookDTO.setDiscount(Integer.valueOf(book.getDiscount()));
        bookDTO.setIsBestseller(book.getIsBestseller());
        bookDTO.setRating(null);
        bookDTO.setStatus(null);
        bookDTO.setPrice(book.getPrice());
        bookDTO.setDiscountPrice(book.getPrice() - book.getDiscount() * book.getPrice() / 100);

        return bookDTO;
    }
}
