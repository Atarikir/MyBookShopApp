package com.example.MyBookShopApp.data.book.file;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "book_file")
@Data
public class BookFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String hash;

    @Column(columnDefinition = "INT NOT NULL")
    private int typeId;

    @Column(columnDefinition = "VARCHAR NOT NULL")
    private String path;
}