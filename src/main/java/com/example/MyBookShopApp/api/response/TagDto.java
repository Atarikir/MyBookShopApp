package com.example.MyBookShopApp.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class TagDto {

    private int id;
    private String name;
    private String slug;
}
