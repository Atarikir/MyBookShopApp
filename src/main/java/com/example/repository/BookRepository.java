package com.example.repository;

import com.example.data.book.BookEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {

  Page<BookEntity> findByOrderByPubDateDesc(Pageable pageable);

  Page<BookEntity> findByIdNotInOrderByPubDateDesc(List<Integer> bookIdList, Pageable pageable);

  Page<BookEntity> findByPubDateBeforeOrderByPubDateDesc(@Param("to") LocalDate to,
      Pageable pageable);

  Page<BookEntity> findByIdNotInAndPubDateBeforeOrderByPubDateDesc(List<Integer> bookIdList,
      @Param("to") LocalDate to, Pageable pageable);

  Page<BookEntity> findByPubDateAfterOrderByPubDateDesc(@Param("from") LocalDate from,
      Pageable pageable);

  Page<BookEntity> findByIdNotInAndPubDateAfterOrderByPubDateDesc(List<Integer> bookIdList,
      @Param("from") LocalDate from, Pageable pageable);

  Page<BookEntity> findByPubDateBetweenOrderByPubDateDesc(@Param("from") LocalDate from,
      @Param("to") LocalDate to, Pageable pageable);

  Page<BookEntity> findByIdNotInAndPubDateBetweenOrderByPubDateDesc(List<Integer> bookIdList,
      @Param("from") LocalDate from, @Param("to") LocalDate to, Pageable pageable);

  Page<BookEntity> findByIdNotInOrderByBookPopularityDesc(List<Integer> bookIdList,
      Pageable pageable);

  Page<BookEntity> findByOrderByBookPopularityDesc(Pageable pageable);

  Page<BookEntity> findByOrderByBookRatingDescPubDateDesc(Pageable pageable);

  @Query("select b from BookEntity b join Book2TagEntity b2t on b2t.bookId = b.id join TagEntity t on b2t.tagId = t.id where t.slug = ?1")
  Page<BookEntity> getBooksByTagSlugPage(String slug, Pageable pageable);

  @Query("select b from BookEntity b join Book2TagEntity b2t on b2t.bookId = b.id join TagEntity t on b2t.tagId = t.id where t.id = ?1 order by b.pubDate desc")
  Page<BookEntity> getBooksByTagIdPage(Integer id, Pageable pageable);

  @Query("select b from BookEntity b join Book2AuthorEntity b2a on b2a.bookId = b.id join AuthorEntity a on b2a.authorId = a.id where a.id = ?1 order by b.pubDate desc")
  Page<BookEntity> getBooksByAuthorIdPage(Integer id, Pageable pageable);

  @Query("select b from BookEntity b join Book2GenreEntity b2g on b2g.bookId = b.id join GenreEntity g on b2g.genreId = g.id where g.id = ?1 order by b.pubDate desc")
  Page<BookEntity> getBooksByGenreIdPage(Integer id, Pageable pageable);

  @Query("select b from BookEntity b join Book2GenreEntity b2g on b2g.bookId = b.id join GenreEntity g on b2g.genreId = g.id where g.slug = ?1")
  Page<BookEntity> getBooksByGenreSlugPage(String slug, Pageable pageable);

  @Query("select b from BookEntity b join Book2AuthorEntity b2a on b2a.bookId = b.id join AuthorEntity a on b2a.authorId = a.id where a.slug = ?1 order by b.pubDate desc")
  Page<BookEntity> getBooksByAuthorSlugPage(String slug, Pageable pageable);

  BookEntity findBySlug(String slug);

  List<BookEntity> findBookEntitiesBySlugIn(String[] slugs);

  @Query("select b from BookEntity b join Book2UserEntity b2u on b2u.bookId = b.id where b2u.userId =?1 and b2u.typeId = ?2")
  List<BookEntity> getUserBooksByStatus(Integer userId, Integer typeId);

  Page<BookEntity> findByIdNotInAndIdInOrderByPubDateDesc(List<Integer> bookIdNotList, List<Integer> bookIdList, Pageable pageable);
}
