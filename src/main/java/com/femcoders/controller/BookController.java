package com.femcoders.controller;

import com.femcoders.model.Author;
import com.femcoders.model.Book;
import com.femcoders.repository.AuthorRepository;
import com.femcoders.repository.BookRepository;

public class BookController {
     private BookRepository bookRepository;
     private AuthorRepository authorRepository;
     
     public BookController(BookRepository bookRepository){
        this.bookRepository = bookRepository;
     }

    public void createBook(Book book){
        Author author = new Author();

        author.setName(book.getAuthor().getName());

        Author savedAuthor = authorRepository.createAuthor(author);

        book.setAuthor(savedAuthor);

        bookRepository.createBook(book);
    }  
}
