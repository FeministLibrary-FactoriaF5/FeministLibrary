package com.femcoders.repository;

import java.util.List;

import com.femcoders.model.Book;

public interface BookRepository {
    //CREATE
    void createBook(Book book);
    //READ
    Book readBookById(int id);
    List<Book> readAllBooks();
    List<Book> readBooksByTitle(String title);
    List<Book> readBooksByAuthor(String authorName);
    List<Book> readBooksByGenre(String genre);
    //UPDATE
    void updateBook(Book book);
    //DELETE
    void deleteBook(int id);
}
