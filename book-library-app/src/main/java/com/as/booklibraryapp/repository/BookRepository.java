package com.as.booklibraryapp.repository;

import com.as.booklibraryapp.domain.Book;

import java.util.Optional;
import java.util.Set;

public interface BookRepository {

    Set<Book> findAllBooks();

    Optional<Book> findBook(Long id);
}
