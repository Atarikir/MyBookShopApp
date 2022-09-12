package com.example.MyBookShopApp.mapper;

import com.example.MyBookShopApp.api.response.BookDto;
import com.example.MyBookShopApp.api.response.BookResponseDto;
import com.example.MyBookShopApp.api.response.BooksListPageResponse;
import com.example.MyBookShopApp.data.author.AuthorEntity;
import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.repository.AuthorRepository;
import com.example.MyBookShopApp.repository.Book2AuthorRepository;
import com.example.MyBookShopApp.service.AuthorService;
import java.util.List;
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

  public BookResponseDto bookEntityToBookResponseDto(BookEntity book) {
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
    bookResponseDTO.setStatus(getStatusBook(book));
    bookResponseDTO.setPrice(book.getPrice());
    bookResponseDTO.setDiscountPrice(getDiscountPrice(book));

    return bookResponseDTO;
  }

  public BookDto bookEntityToBookDto(BookEntity book) {
    if (book == null) {
      return null;
    }
    BookDto bookDto = new BookDto();
    bookDto.setDescription(book.getDescription());
    bookDto.setPrice(book.getPrice());
    bookDto.setDiscountPrice(getDiscountPrice(book));
    bookDto.setTitle(book.getTitle());
    bookDto.setAuthorEntityList(book.getAuthorEntityList());
    bookDto.setTagEntityList(book.getTagEntityList());
    bookDto.setImage(book.getImage());
    return bookDto;
  }

  //    public abstract List<BookDto> pageDtoToListDto(Page<BookDto> var1);
//  public abstract List<BookEntity> pageBookToBookList(Page<BookEntity> books);
//  public abstract Page<BookDto> pageEntityToPageDto(Page<BookEntity> var2);

  public abstract List<BookResponseDto> pageEntityToDtoList(Page<BookEntity> var1);

  public BooksListPageResponse toListResponse(Page<BookEntity> entityPage) {
    if (entityPage == null) {
      return null;
    }
    return new BooksListPageResponse(entityPage.getTotalElements(),
        this.pageEntityToDtoList(entityPage));
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
