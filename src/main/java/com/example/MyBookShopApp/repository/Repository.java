package com.example.MyBookShopApp.repository;

import java.util.List;

public interface Repository<T> {

    List<T> getAll();
}
