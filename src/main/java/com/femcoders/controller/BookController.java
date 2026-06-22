package com.femcoders.controller;

import com.femcoders.model.Author;
import com.femcoders.model.Book;
import com.femcoders.repository.AuthorRepository;
import com.femcoders.repository.BookRepository;

public class BookController {
     private BookRepository bookRepository;
     private AuthorRepository authorRepository;

     public BookController(BookRepository bookRepository, AuthorRepository authorRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
     }

    public void createBook(Book book){
        Author author = authorRepository.validateExistingAuthor(book.getAuthor().getName());

        book.setAuthor(author);

        bookRepository.createBook(book);
    }  
}
