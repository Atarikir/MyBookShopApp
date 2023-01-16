package com.example.data;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jwt_black_list")
//@Getter
//@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class JwtBlackList {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(columnDefinition = "VARCHAR(255)")
  private String token;

  @Column(name = "expiration_time")
  private Date expirationTime;

  public JwtBlackList(String token, Date expirationTime) {
    this.token = token;
    this.expirationTime = expirationTime;
  }
}
