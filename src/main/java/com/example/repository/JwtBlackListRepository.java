package com.example.repository;

import com.example.data.JwtBlackList;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JwtBlackListRepository extends JpaRepository<JwtBlackList, Integer> {

  JwtBlackList findJwtBlackListByToken(String token);

  @Modifying
  @Query("delete from JwtBlackList j where j.expirationTime < ?1")
  int deleteJwtFromBlackList(Date time);
}
