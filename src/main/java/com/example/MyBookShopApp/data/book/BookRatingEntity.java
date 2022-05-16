package com.example.MyBookShopApp.data.book;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "book_rating")
@Getter
@Setter
public class BookRatingEntity {

    @Id
    private int id;

    @Column(columnDefinition = "SMALLINT NOT NULL DEFAULT 0")
    private Short value;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "book_id", columnDefinition = "INT NOT NULL")
    private BookEntity bookId;
}
