package com.example.api.response;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorDto {

  private int id;
  private String photo;
  private String slug;
  private String name;
  private String description;
  private List<BookResponseDto> bookList;

  public String getLittleText() {
    if (description.length() < 100) {
      return description;
    }
    return description.substring(0, 100);
  }

  public String[] getOtherPartText() {
    String newString = description.substring(100);
    return Iterables.toArray(Splitter.fixedLength(100).split(newString), String.class);
  }

  public boolean isBigText() {
    return description.length() >= 100;
  }
}
