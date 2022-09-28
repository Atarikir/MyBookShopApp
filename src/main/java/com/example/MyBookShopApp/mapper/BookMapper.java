package com.example.MyBookShopApp.mapper;

import com.example.MyBookShopApp.api.response.BookDto;
import com.example.MyBookShopApp.api.response.BookResponseDto;
import com.example.MyBookShopApp.api.response.BooksListPageResponse;
import com.example.MyBookShopApp.data.author.AuthorEntity;
import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.data.tag.TagEntity;
import com.example.MyBookShopApp.repository.AuthorRepository;
import com.example.MyBookShopApp.repository.Book2AuthorRepository;
import com.example.MyBookShopApp.service.AuthorService;
import java.util.ArrayList;
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

//  public abstract BookDto bookEntityToBookDto(BookEntity book);

  public BookDto bookEntityToBookDto(BookEntity book) {
    if (book == null) {
      return null;
    }
    BookDto bookDto = new BookDto();

    bookDto.setId( book.getId() );
    bookDto.setPubDate( book.getPubDate() );
    bookDto.setIsBestseller( book.getIsBestseller() );
    bookDto.setSlug( book.getSlug() );
    bookDto.setTitle( book.getTitle() );
    bookDto.setImage( book.getImage() );
    bookDto.setDescription( book.getDescription() );
    bookDto.setPrice( book.getPrice() );
    if ( book.getDiscount() != null ) {
      bookDto.setDiscount( book.getDiscount().intValue() );
    }
    bookDto.setBookRating( book.getBookRating() );
    bookDto.setBookPopularity( book.getBookPopularity() );
    List<AuthorEntity> list = book.getAuthorEntityList();
    if ( list != null ) {
      bookDto.setAuthorEntityList( new ArrayList<>( list ) );
    }
    List<TagEntity> list1 = book.getTagEntityList();
    if ( list1 != null ) {
      bookDto.setTagEntityList( new ArrayList<>( list1 ) );
    }

    bookDto.setDiscountPrice(getDiscountPrice(book));

    return bookDto;
  }

  //    public abstract List<BookDto> pageDtoToListDto(Page<BookDto> var1);
//  public abstract List<BookEntity> pageBookToBookList(Page<BookEntity> books);

  public abstract List<BookDto> listEntityToDtoList(List<BookEntity> var2);

  public abstract List<BookResponseDto> pageEntityToResponseDtoList(Page<BookEntity> var1);

  public BooksListPageResponse toListResponse(Page<BookEntity> entityPage) {
    if (entityPage == null) {
      return null;
    }
    return new BooksListPageResponse(entityPage.getTotalElements(),
        this.pageEntityToResponseDtoList(entityPage));
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
