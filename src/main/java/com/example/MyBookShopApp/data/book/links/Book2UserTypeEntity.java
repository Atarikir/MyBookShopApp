package com.example.MyBookShopApp.data.book.links;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "book2user_type")
@Data
public class Book2UserTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String code;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String name;
}
