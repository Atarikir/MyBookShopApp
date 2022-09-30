package com.example.data.book.review;

import com.example.data.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Getter
@Setter
public class MessageEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(columnDefinition = "TIMESTAMP NOT NULL")
  private LocalDateTime time;

  @ManyToOne
  @JoinColumn(name = "user_id", columnDefinition = "INT")
  private UserEntity userId;

  @Column(columnDefinition = "VARCHAR(255)")
  private String email;

  @Column(columnDefinition = "VARCHAR(255)")
  private String name;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String subject;

  @Column(columnDefinition = "TEXT NOT NULL")
  private String text;
}
