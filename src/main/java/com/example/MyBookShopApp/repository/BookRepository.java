package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.data.book.BookGradeEntity;
import com.example.MyBookShopApp.data.book.BookRatingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    Page<BookEntity> findByOrderByPubDateDesc(Pageable pageable);

    @Query("select b from BookEntity b join BookRatingEntity br on b.id = br.bookId order by br.value desc, b.pubDate desc")
    Page<BookEntity> getBooksSortedByPubDateAndBookRatingValue(Pageable pageable);

    Page<BookEntity> findByPubDateBetweenOrderByPubDateDesc(@Param("from") String from,
                                                            @Param("to") String to,
                                                            Pageable pageable);

    @Query("select b from BookEntity b join BookPopularityEntity bp on b.id = bp.bookId order by bp.value desc")
    Page<BookEntity> getBooksSortedByBookPopularityValue(Pageable pageable);
}
