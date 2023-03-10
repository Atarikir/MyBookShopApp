package com.example.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.BaseTest;
import com.example.data.book.BookEntity;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
class BookRepositoryTest extends BaseTest {

  private final BookRepository bookRepository;

  @Autowired
  BookRepositoryTest(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  //@Test
  void getBooksByAuthorIdPage() {
  }

  //@Test
  void findBookEntitiesBySlugIn() {
    String[] slugArray = {"book-tcj-254"};
    List<BookEntity>  bookListBySlug = bookRepository.findBookEntitiesBySlugIn(slugArray);

    assertNotNull(bookListBySlug);
    assertFalse(bookListBySlug.isEmpty());

    for (BookEntity book :bookListBySlug) {
      log.info(book.toString(), this.getClass().getSimpleName());
      assertThat(book.getSlug()).contains(slugArray);
    }
  }
}