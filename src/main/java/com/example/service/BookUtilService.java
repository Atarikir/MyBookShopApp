package com.example.service;

import static com.example.data.enums.Book2UserType.ARCHIVED;
import static com.example.data.enums.Book2UserType.CART;
import static com.example.data.enums.Book2UserType.KEPT;
import static com.example.data.enums.Book2UserType.PAID;

import com.example.data.book.BookEntity;
import com.example.data.enums.Book2UserType;
import com.example.repository.Book2UserRepository;
import com.example.repository.BookRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookUtilService {

  private static final double COEFFICIENT_OF_THE_NUMBER_OF_BOOKS_IN_THE_CART = 0.7;
  private static final double COEFFICIENT_OF_THE_NUMBER_OF_BOOKS_IN_THE_KEPT = 0.4;

  private final BookRepository bookRepository;
  private final Book2UserRepository book2UserRepository;

  @Transactional
  public void bookUpdateWhenPopularityChanges(Integer bookId) {
    Optional<BookEntity> bookOpt = bookRepository.findById(bookId);
    if (bookOpt.isPresent()) {
      long countPaid =
          this.countOfBooksWithBindingType(bookId, PAID) +
              this.countOfBooksWithBindingType(bookId, ARCHIVED);
      long countCart = this.countOfBooksWithBindingType(bookId, CART);
      long countPostponed = this.countOfBooksWithBindingType(bookId, KEPT);
      BookEntity book = bookOpt.get();
      book.setBookPopularity(this.popularityCalculation(countPaid, countCart, countPostponed));
      bookRepository.save(book);
    }
  }

  /***
   * Метод расчёта популярности книги - представляет собой неотрицательное число, которое можно
   * рассчитать по следующей формуле:
   * P = B + 0,7*C + 0,4*K,
   * где B — количество пользователей, купивших книгу,
   *     C — количество пользователей, у которых книга находится в корзине,
   *     K — количество пользователей, у которых книга отложена.
   *
   * @param countPaid - количество пользователей, купивших книгу
   * @param countCart - количество пользователей, у которых книга находится в корзине
   * @param countPostponed - количество пользователей, у которых книга отложена
   * @return P - популярность книги
   */
  public Float popularityCalculation(long countPaid, long countCart, long countPostponed) {
    return (float) (countPaid
        + COEFFICIENT_OF_THE_NUMBER_OF_BOOKS_IN_THE_CART * countCart
        + COEFFICIENT_OF_THE_NUMBER_OF_BOOKS_IN_THE_KEPT * countPostponed);
  }

  private Long countOfBooksWithBindingType(Integer bookId, Book2UserType type) {
    return book2UserRepository.countByBookIdAndTypeId(bookId, Book2UserType.getBindingTypeId(type));
  }
}
