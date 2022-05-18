package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.data.book.BookGradeEntity;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class BooksRatingAndPopularityService {

    public Integer bookRatingCalculation(BookEntity book) {
        int rating;
        List<BookGradeEntity> bookGradeEntityList = book.getBookGradeList();
        if (bookGradeEntityList.isEmpty()) {
            rating = 0;
        } else {
            int count = bookGradeEntityList.size();
            int sumValue = bookGradeEntityList.stream().mapToInt(BookGradeEntity::getValue).sum();
            rating = Math.round((float) sumValue / count);
        }

        return rating;
    }
}
