package com.example.MyBookShopApp.data.book;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book_popularity")
@Getter
@Setter
public class BookPopularityEntity {

    @Id
    private int id;

    @Column(columnDefinition = "FLOAT NOT NULL DEFAULT 0")
    private Float value;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "book_id", columnDefinition = "INT NOT NULL")
    private BookEntity bookId;
}
