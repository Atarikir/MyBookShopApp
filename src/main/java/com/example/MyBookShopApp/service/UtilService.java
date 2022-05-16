package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.data.book.BookGradeEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilService {

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
