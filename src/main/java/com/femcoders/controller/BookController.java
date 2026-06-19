package com.femcoders.controller;

import com.femcoders.model.Book;
import com.femcoders.repository.BookRepository;

public class BookController {
     private BookRepository bookRepository;
     
     public BookController(BookRepository bookRepository){
        this.bookRepository = bookRepository;
     }

    public void createBook(Book book){
        bookRepository.createBook(book);
    }  
}
