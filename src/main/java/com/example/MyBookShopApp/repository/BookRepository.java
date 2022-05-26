package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.book.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    Page<BookEntity> findByOrderByPubDateDesc(Pageable pageable);

    Page<BookEntity> findByOrderByBookRatingDescPubDateDesc(Pageable pageable);

    Page<BookEntity> findByPubDateBeforeOrderByPubDateDesc(@Param("to") LocalDate to, Pageable pageable);

    Page<BookEntity> findByPubDateAfterOrderByPubDateDesc(@Param("from") LocalDate from, Pageable pageable);

    Page<BookEntity> findByPubDateBetweenOrderByPubDateDesc(@Param("from") LocalDate from,
                                                            @Param("to") LocalDate to,
                                                            Pageable pageable);

    Page<BookEntity> findByOrderByBookPopularityDesc(Pageable pageable);

    @Query("select b from BookEntity b join Book2TagEntity b2t on b2t.bookId = b.id join TagEntity t on b2t.tagId = t.id" +
            " where t.slug = ?1")
    Page<BookEntity> getBooksByTagSlugPage(String slug, Pageable pageable);

    @Query("select b from BookEntity b join Book2TagEntity b2t on b2t.bookId = b.id join TagEntity t on b2t.tagId = t.id" +
            " where t.id = ?1 order by b.pubDate desc")
    Page<BookEntity> getBooksByTagIdPage(Integer id, Pageable pageable);
}
