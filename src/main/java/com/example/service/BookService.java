package com.example.service;

import com.example.api.response.BookDto;
import com.example.api.response.BookResponseDto;
import com.example.api.response.BooksListPageResponse;
import com.example.data.author.AuthorEntity;
import com.example.data.book.BookEntity;
import com.example.data.book.links.Book2AuthorEntity;
import com.example.data.book.links.Book2GenreEntity;
import com.example.data.book.links.Book2TagEntity;
import com.example.data.book.links.Book2UserEntity;
import com.example.data.genre.GenreEntity;
import com.example.data.tag.TagEntity;
import com.example.data.user.UserEntity;
import com.example.mapper.BookMapper;
import com.example.repository.Book2AuthorRepository;
import com.example.repository.Book2GenreRepository;
import com.example.repository.Book2TagRepository;
import com.example.repository.Book2UserRepository;
import com.example.repository.BookRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

  private final BookMapper bookMapper;
  private final BookRepository bookRepository;
  private final ResourceStorage storage;
  private final UserRegisterService userRegisterService;
  private final Book2UserRepository book2UserRepository;
  private final Book2TagRepository book2TagRepository;
  private final Book2GenreRepository book2GenreRepository;
  private final Book2AuthorRepository book2AuthorRepository;

  public List<BookResponseDto> getBooksByGenreSlugList(String slug, int offset, int limit,
      HttpServletRequest request) {
    return bookMapper.pageEntityToResponseDtoList(getBooksByGenreSlug(slug, offset, limit),
        request);
  }

  public List<BookResponseDto> getBooksByTagSlugList(String slug, Integer offset, Integer limit,
      HttpServletRequest request) {
    return bookMapper.pageEntityToResponseDtoList(getBooksByTagSlug(slug, offset, limit), request);
  }

  public List<BookResponseDto> getBooksByAuthorSlugList(String slug, int offset, int limit,
      HttpServletRequest request) {
    return bookMapper.pageEntityToResponseDtoList(getBooksByAuthorSlug(slug, offset, limit),
        request);
  }

  public BookDto getBookDtoBySlug(String slug) {
    return bookMapper.bookEntityToBookDto(getBookBySlug(slug));
  }

  private BookEntity getBookBySlug(String slug) {
    return bookRepository.findBySlug(slug);
  }

  public List<BookResponseDto> getRecommendedBooksList(Integer offset, Integer limit,
      HttpServletRequest request) {
    return bookMapper.pageEntityToResponseDtoList(getRecommendedBooksByUser(offset, limit, request),
        request);
  }

  public List<BookResponseDto> getRecentBooksList(String from, String to, Integer offset,
      Integer limit, HttpServletRequest request) {
    return bookMapper.pageEntityToResponseDtoList(getRecentBooks(from, to, offset, limit, request),
        request);
  }

  public List<BookResponseDto> getPopularBooksList(Integer offset, Integer limit,
      HttpServletRequest request) {
    return bookMapper.pageEntityToResponseDtoList(getPopularBooks(offset, limit, request), request);
  }

  public BooksListPageResponse getPageOfRecommendedBooks(Integer offset, Integer limit,
      HttpServletRequest request) {
    return bookMapper.toListResponse(getRecommendedBooksByUser(offset, limit, request), request);
  }

  public BooksListPageResponse getPageOfRecentBooks(String from, String to, Integer offset,
      Integer limit, HttpServletRequest request) {
    return bookMapper.toListResponse(getRecentBooks(from, to, offset, limit, request), request);
  }

  public BooksListPageResponse getPageOfPopularBooks(Integer offset, Integer limit,
      HttpServletRequest request) {
    return bookMapper.toListResponse(getPopularBooks(offset, limit, request), request);
  }

  public BooksListPageResponse getPageOfBooksByTagId(Integer id, Integer offset, Integer limit,
      HttpServletRequest request) {
    return bookMapper.toListResponse(getBooksByTagId(id, offset, limit), request);
  }

  public BooksListPageResponse getPageOfBooksByAuthorId(Integer id, Integer offset, Integer limit,
      HttpServletRequest request) {
    return bookMapper.toListResponse(getBooksByAuthorId(id, offset, limit), request);
  }

  public BooksListPageResponse getPageOfBooksByGenreId(Integer id, Integer offset, Integer limit,
      HttpServletRequest request) {
    return bookMapper.toListResponse(getBooksByGenreId(id, offset, limit), request);
  }

  public void saveImageBook(MultipartFile file, String slug) throws IOException {
    log.info("slug in BookService - " + slug);
    String savePath = storage.saveNewBookImage(file, slug);
    BookEntity bookToUpdate = bookRepository.findBySlug(slug);
    bookToUpdate.setImage(savePath);
    bookRepository.save(bookToUpdate); //save new path in db here
  }

  public Page<BookEntity> sortingAndFilteringRecommendedBooks(UserEntity user, Integer offset,
      Integer limit) {
    List<BookEntity> bookList = user.getBookEntityList();
    List<Integer> bookIdNotList = new ArrayList<>();
    List<Integer> tagIdList = new ArrayList<>();
    List<Integer> genreIdList = new ArrayList<>();
    List<Integer> authorIdList = new ArrayList<>();
    bookList.forEach(book -> {
      bookIdNotList.add(book.getId());
      tagIdList.addAll(book.getTagEntityList().stream().map(TagEntity::getId).toList());
      genreIdList.addAll(book.getGenreEntityList().stream().map(GenreEntity::getId).toList());
      authorIdList.addAll(book.getAuthorEntityList().stream().map(AuthorEntity::getId).toList());
    });
    List<Integer> bookIdByTagList = book2TagRepository.findByTagIdIn(tagIdList).stream()
        .map(Book2TagEntity::getBookId).toList();
    List<Integer> bookIdByGenreList = book2GenreRepository.findByGenreIdIn(genreIdList).stream()
        .map(Book2GenreEntity::getBookId).toList();
    List<Integer> bookIdByAuthorList = book2AuthorRepository.findByAuthorIdIn(authorIdList)
        .stream().map(
            Book2AuthorEntity::getBookId).toList();
    List<Integer> bookIdList = new ArrayList<>(bookIdByTagList);
    bookIdList.addAll(bookIdByGenreList);
    bookIdList.addAll(bookIdByAuthorList);
    return bookRepository.findByIdNotInAndIdInOrderByPubDateDesc(bookIdNotList, bookIdList,
        getPageable(offset, limit));
  }

  private List<Book2UserEntity> book2UserEntityList(UserEntity user) {
    List<Book2UserEntity> book2UserList = null;
    if (user != null) {
      book2UserList = book2UserRepository.findByUserId(user.getId());
    }
    return book2UserList;
  }

  private Page<BookEntity> getRecommendedBooksByUser(Integer offset, Integer limit,
      HttpServletRequest request) {
    if (request.getCookies() != null) {
      UserEntity user = userRegisterService.getUser(request);
      List<Book2UserEntity> book2UserList = this.book2UserEntityList(user);
      return book2UserList != null && !book2UserList.isEmpty()
          ? this.sortingAndFilteringRecommendedBooks(user, offset, limit)
          : bookRepository.findByOrderByBookRatingDescPubDateDesc(getPageable(offset, limit));
    }
    return bookRepository.findByOrderByBookRatingDescPubDateDesc(getPageable(offset, limit));
  }

  private Page<BookEntity> getRecentBooks(String from, String to, Integer offset, Integer limit,
      HttpServletRequest request) {
    UserEntity user = userRegisterService.getUser(request);
    List<Book2UserEntity> book2UserList = this.book2UserEntityList(user);
    Page<BookEntity> books = null;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    if (book2UserList != null && !book2UserList.isEmpty()) {
      List<BookEntity> bookList = user.getBookEntityList();
      List<Integer> bookIdList = new ArrayList<>();
      bookList.forEach(book -> bookIdList.add(book.getId()));
      if (this.isFromToNull(from, to)) {
        books = bookRepository.findByIdNotInOrderByPubDateDesc(bookIdList,
            getPageable(offset, limit));
      } else if (this.isFromNull(from, to)) {
        LocalDate dateEnd = LocalDate.parse(to, formatter);
        books = bookRepository.findByIdNotInAndPubDateBeforeOrderByPubDateDesc(bookIdList, dateEnd,
            getPageable(offset, limit));
      } else if (this.isToNull(from, to)) {
        LocalDate dateStart = LocalDate.parse(Objects.requireNonNull(from), formatter);
        books = bookRepository.findByIdNotInAndPubDateAfterOrderByPubDateDesc(bookIdList, dateStart,
            getPageable(offset, limit));
      } else if (this.isFromToNotNull(from, to)) {
        LocalDate dateStart = LocalDate.parse(Objects.requireNonNull(from), formatter);
        LocalDate dateEnd = LocalDate.parse(Objects.requireNonNull(to), formatter);
        books = bookRepository.findByIdNotInAndPubDateBetweenOrderByPubDateDesc(bookIdList,
            dateStart, dateEnd, getPageable(offset, limit));
      }
    } else {
      if (this.isFromToNull(from, to)) {
        books = bookRepository.findByOrderByPubDateDesc(getPageable(offset, limit));
      } else if (this.isFromNull(from, to)) {
        LocalDate dateEnd = LocalDate.parse(to, formatter);
        books = bookRepository.findByPubDateBeforeOrderByPubDateDesc(dateEnd,
            getPageable(offset, limit));
      } else if (this.isToNull(from, to)) {
        LocalDate dateStart = LocalDate.parse(Objects.requireNonNull(from), formatter);
        books = bookRepository.findByPubDateAfterOrderByPubDateDesc(dateStart,
            getPageable(offset, limit));
      } else if (this.isFromToNotNull(from, to)) {
        LocalDate dateStart = LocalDate.parse(Objects.requireNonNull(from), formatter);
        LocalDate dateEnd = LocalDate.parse(Objects.requireNonNull(to), formatter);
        books = bookRepository.findByPubDateBetweenOrderByPubDateDesc(dateStart, dateEnd,
            getPageable(offset, limit));
      }
    }
    return books;
  }

  private Page<BookEntity> getPopularBooks(Integer offset, Integer limit,
      HttpServletRequest request) {
    UserEntity user = userRegisterService.getUser(request);
    List<Book2UserEntity> book2UserList = this.book2UserEntityList(user);
    if (book2UserList != null && !book2UserList.isEmpty()) {
      List<BookEntity> bookList = user.getBookEntityList();
      List<Integer> bookIdList = new ArrayList<>();
      bookList.forEach(book -> bookIdList.add(book.getId()));
      return bookRepository.findByIdNotInOrderByBookPopularityDesc(bookIdList,
          getPageable(offset, limit));
    }
    return bookRepository.findByOrderByBookPopularityDesc(getPageable(offset, limit));
  }

  private Page<BookEntity> getBooksByTagId(Integer id, Integer offset, Integer limit) {
    return bookRepository.getBooksByTagIdPage(id, getPageable(offset, limit));
  }

  private Page<BookEntity> getBooksByAuthorId(Integer id, Integer offset, Integer limit) {
    return bookRepository.getBooksByAuthorIdPage(id, getPageable(offset, limit));
  }

  private Page<BookEntity> getBooksByGenreId(Integer id, Integer offset, Integer limit) {
    return bookRepository.getBooksByGenreIdPage(id, getPageable(offset, limit));
  }

  private Page<BookEntity> getBooksByTagSlug(String slug, Integer offset, Integer limit) {
    return bookRepository.getBooksByTagSlugPage(slug, getPageable(offset, limit));
  }

  private Page<BookEntity> getBooksByGenreSlug(String slug, Integer offset, Integer limit) {
    return bookRepository.getBooksByGenreSlugPage(slug, getPageable(offset, limit));
  }

  private Page<BookEntity> getBooksByAuthorSlug(String slug, int offset, int limit) {
    return bookRepository.getBooksByAuthorSlugPage(slug, getPageable(offset, limit));
  }

  private Pageable getPageable(Integer offset, Integer limit) {
    return PageRequest.of(offset, limit);
  }

  private boolean isFromToNull(String from, String to) {
    return Objects.equals(from, "") && Objects.equals(to, "") || from == null && to == null;
  }

  private boolean isFromNull(String from, String to) {
    return Objects.equals(from, "") && !to.equals("") || from == null;
  }

  private boolean isToNull(String from, String to) {
    return Objects.equals(to, "") && !Objects.equals(from, "") || to == null;
  }

  private boolean isFromToNotNull(String from, String to) {
    return !Objects.equals(from, "") && !Objects.equals(to, "");
  }
}
