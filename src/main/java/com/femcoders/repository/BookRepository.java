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

    Boolean validateExistingIsbn(String isbn);

    //UPDATE
    void updateBookById(int id, Book updatedBook);

    //DELETE
    void deleteBook(int id);
}