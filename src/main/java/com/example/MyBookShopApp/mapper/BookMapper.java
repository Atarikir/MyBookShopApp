package com.example.MyBookShopApp.mapper;

import com.example.MyBookShopApp.api.response.BookDto;
import com.example.MyBookShopApp.data.author.AuthorEntity;
import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.data.book.BookGradeEntity;
import com.example.MyBookShopApp.repository.*;
import com.example.MyBookShopApp.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

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
    @Autowired
    private Book2UserRepository book2UserRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Book2UserTypeRepository book2UserTypeRepository;

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
        bookDTO.setAuthors(getAuthors(book));
        bookDTO.setDiscount(Integer.valueOf(book.getDiscount()));
        bookDTO.setIsBestseller(book.getIsBestseller());
        bookDTO.setRating(book.getBookRating());
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
        String authorString = null;
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

    private Integer getRating(BookEntity book) {
        int rating;
        List<BookGradeEntity> bookGradeEntityList = book.getBookGradeList();
        if (bookGradeEntityList.isEmpty()) {
            rating = 0;
        } else {
            int count = bookGradeEntityList.size();
            int sumValue = bookGradeEntityList.stream().mapToInt(BookGradeEntity::getValue).sum();
            rating = Math.round((float) sumValue / count);
        }

        return rating;
    }

    private String getStatusBook(BookEntity book) {
        String statusBook = String.valueOf(false);
        //TODO: переделать поиск текущего пользователя, когда будет реализована аутентификация
//        Integer currentUser = book2UserRepository.getUserId(20);
//        Integer bookId = book2UserRepository.getBookId(book.getId());
//        if (bookId != null && currentUser != null) {
//            int typeId = book2UserRepository.getTypeIdByBookIdAndUserId(currentUser, bookId);
//                Book2UserTypeEntity book2UserType = book2UserTypeRepository.findById(typeId);
//                statusBook = book2UserType.getCode();
//        }

        return statusBook;
    }
}
