package com.example.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponseDto {

  private int id;
  private String slug;
  private String image;
  private String authors;
  private String title;
  private Integer discount;
  private Boolean isBestseller;
  private Integer rating;
  private String status;
  private Integer price;
  private Integer discountPrice;

  public void setIsBestseller(Short isBestseller) {
    this.isBestseller = isBestseller == 1;
  }
}
