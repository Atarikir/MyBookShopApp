package com.example.data.user;

import com.example.data.enums.ContactType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_contact")
@Getter
@Setter
public class UserContactEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Enumerated(EnumType.STRING)
  private ContactType type;

  @Column(columnDefinition = "SMALLINT NOT NULL")
  private short approved;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String code;

  @Column(name = "code_trails", columnDefinition = "INT")
  private int codeTrails;

  @Column(name = "code_time", columnDefinition = "TIMESTAMP")
  private LocalDateTime codeTime;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String contact;

  @ManyToOne
  @JoinColumn(name = "user_id", columnDefinition = "INT NOT NULL")
  private UserEntity userId;
}
