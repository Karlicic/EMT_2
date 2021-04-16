package emt.finki.ukim.mk.demo.service;


import emt.finki.ukim.mk.demo.model.Author;
import emt.finki.ukim.mk.demo.model.Book;
import emt.finki.ukim.mk.demo.model.dto.BookDto;
import emt.finki.ukim.mk.demo.model.enumerations.Category;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    Optional<Book> findById(Long id);

    Optional<Book> save(String name, Category category, Author author, Integer availableCopies);

    Optional<Book> save(BookDto bookDto);

    Optional<Book> edit(Long id, String name, Category category,  Author author, Integer availableCopies);

    Optional<Book> edit(Long id, BookDto bookDto);

    void deleteById(Long id);

    Optional<Book> markAsTaken(Long id);


}
