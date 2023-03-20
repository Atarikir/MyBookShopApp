package com.example.mapper;

import com.example.api.response.BookDto;
import com.example.api.response.BookResponseDto;
import com.example.api.response.BooksListPageResponse;
import com.example.data.author.AuthorEntity;
import com.example.data.book.BookEntity;
import com.example.data.book.links.Book2UserEntity;
import com.example.data.enums.Book2UserType;
import com.example.data.tag.TagEntity;
import com.example.data.user.UserEntity;
import com.example.repository.AuthorRepository;
import com.example.repository.Book2AuthorRepository;
import com.example.repository.Book2UserRepository;
import com.example.service.AuthorService;
import com.example.service.UserRegisterService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

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
  private UserRegisterService userRegisterService;
  @Autowired
  private Book2UserRepository book2UserRepository;

  public BookResponseDto bookEntityToBookResponseDto(BookEntity book, HttpServletRequest request) {
    if (book == null) {
      return null;
    }
    BookResponseDto bookResponseDTO = new BookResponseDto();
    bookResponseDTO.setId(book.getId());
    bookResponseDTO.setSlug(book.getSlug());
    bookResponseDTO.setTitle(book.getTitle());
    bookResponseDTO.setImage(book.getImage());
    bookResponseDTO.setAuthors(getAuthors(book));
    bookResponseDTO.setDiscount(Integer.valueOf(book.getDiscount()));
    bookResponseDTO.setIsBestseller(book.getIsBestseller());
    bookResponseDTO.setRating(Integer.valueOf(book.getBookRating()));
    bookResponseDTO.setStatus(getStatusBook(book, request));
    bookResponseDTO.setPrice(book.getPrice());
    bookResponseDTO.setDiscountPrice(getDiscountPrice(book));

    return bookResponseDTO;
  }

//  public abstract BookDto bookEntityToBookDto(BookEntity book);

  public BookDto bookEntityToBookDto(BookEntity book) {
    if (book == null) {
      return null;
    }
    BookDto bookDto = new BookDto();

    bookDto.setId(book.getId());
    bookDto.setPubDate(book.getPubDate());
    bookDto.setIsBestseller(book.getIsBestseller());
    bookDto.setSlug(book.getSlug());
    bookDto.setTitle(book.getTitle());
    bookDto.setImage(book.getImage());
    bookDto.setDescription(book.getDescription());
    bookDto.setPrice(book.getPrice());
    if (book.getBookReviewEntityList() != null) {
      bookDto.setBookReviewEntityList(book.getBookReviewEntityList());
    }
    if (book.getDiscount() != null) {
      bookDto.setDiscount(book.getDiscount().intValue());
    }
    bookDto.setBookRating(book.getBookRating());
    bookDto.setBookPopularity(book.getBookPopularity());
    List<AuthorEntity> list = book.getAuthorEntityList();
    if (list != null) {
      bookDto.setAuthorEntityList(new ArrayList<>(list));
    }
    List<TagEntity> list1 = book.getTagEntityList();
    if (list1 != null) {
      bookDto.setTagEntityList(new ArrayList<>(list1));
    }

    bookDto.setDiscountPrice(getDiscountPrice(book));

    return bookDto;
  }

  //    public abstract List<BookDto> pageDtoToListDto(Page<BookDto> var1);
//  public abstract List<BookEntity> pageBookToBookList(Page<BookEntity> books);

  public abstract List<BookDto> listEntityToDtoList(List<BookEntity> var2);

//  public abstract List<BookResponseDto> pageEntityToResponseDtoList(Page<BookEntity> var1);

  public List<BookResponseDto> pageEntityToResponseDtoList(Page<BookEntity> var1,
      HttpServletRequest request) {
    if (var1 == null) {
      return Collections.emptyList();
    }
    List<BookResponseDto> list = new ArrayList<>();
    for (BookEntity bookEntity : var1) {
      list.add(bookEntityToBookResponseDto(bookEntity, request));
    }
    return list;
  }

  public BooksListPageResponse toListResponse(Page<BookEntity> entityPage,
      HttpServletRequest request) {
    if (entityPage == null) {
      return null;
    }
    return new BooksListPageResponse(entityPage.getTotalElements(),
        this.pageEntityToResponseDtoList(entityPage, request));
  }

  private Integer getDiscountPrice(BookEntity book) {
    int newPrice = (book.getDiscount() * book.getPrice()) / 100;
    return book.getPrice() - newPrice;
  }

  private String getAuthors(BookEntity book) {
    String authorString = "";
    Integer bookId = book2AuthorRepository.getBookId(book.getId());
    if (bookId != null) {
      List<AuthorEntity> authorList = book.getAuthorEntityList();
      if (authorList.size() == 1) {
        authorString = authorList.get(0).getName();
      }
      if (authorList.size() > 1) {
        Integer authorId = book2AuthorRepository.getAuthorIdBySortIndex(bookId);
        AuthorEntity author = authorRepository.findById(authorId).orElseThrow();
        authorString = author.getName() + " и другие";
      }
    }
    return authorString;
  }

  private String getStatusBook(BookEntity book, HttpServletRequest request) {
    if (request.getCookies() != null) {
      UserEntity currentUser = userRegisterService.getUser(request);
      if (currentUser != null) {
        Book2UserEntity book2User = book2UserRepository.findByBookIdAndUserId(book.getId(),
            currentUser.getId());
        return book2User != null ? Book2UserType.getBindingTypeCodeByTypeID(book2User.getTypeId())
            : String.valueOf(false);
      }
    }
    return String.valueOf(false);
  }
}
