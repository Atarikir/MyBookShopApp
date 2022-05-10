//package com.example.MyBookShopApp.data.book;
//
//import lombok.Data;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "book_rating")
//@Data
//public class BookRatingEntity {
//
//    @Id
//    @Column(name = "book_id")
//    private int id;
//
//    @Column(columnDefinition = "SMALLINT NOT NULL DEFAULT 0")
//    private Short value;
//
//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "book_id", columnDefinition = "INT NOT NULL")
//    private BookEntity bookId;
//}
