package com.femcoders.controller;

import com.femcoders.model.Author;
import com.femcoders.model.Book;
import com.femcoders.model.Publisher;
import com.femcoders.repository.AuthorRepository;
import com.femcoders.repository.BookRepository;
import com.femcoders.repository.PublisherRepository;

public class BookController {
     private BookRepository bookRepository;
     private AuthorRepository authorRepository;
     private PublisherRepository publisherRepository;

     public BookController(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
     }

    public void createBook(Book book){
        Author author = authorRepository.validateExistingAuthor(book.getAuthor().getName());
        Publisher publisher = publisherRepository.createPublisher(book.getPublisher());

        book.setAuthor(author);
        book.setPublisher(publisher);

        bookRepository.createBook(book);
    }  
}
