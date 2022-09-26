package com.example.MyBookShopApp.data.book.links;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book2user_type")
@Getter
@Setter
public class Book2UserTypeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String code;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String name;
}
