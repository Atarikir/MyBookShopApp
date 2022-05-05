package com.example.MyBookShopApp.data.book;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "book_rating")
@Data
public class BookRatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "SMALLINT NOT NULL DEFAULT 0")
    private Short value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", columnDefinition = "INT NOT NULL")
    private BookEntity bookId;
}
