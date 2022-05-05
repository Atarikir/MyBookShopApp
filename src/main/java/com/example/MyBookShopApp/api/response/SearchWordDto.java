package com.example.MyBookShopApp.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class SearchWordDto {

    private String example;

    public SearchWordDto(String example) {
        this.example = example;
    }

    public SearchWordDto() {
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getExample() {
        return example;
    }
}
