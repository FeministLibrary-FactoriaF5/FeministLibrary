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
         Author existingAuthor = authorRepository.readAuthorByName(book.getAuthor().getName());

         if(existingAuthor == null){
             Author newAuthor = new Author();

             newAuthor.setName(book.getAuthor().getName());

             Author savedAuthor = authorRepository.createAuthor(newAuthor);

             book.setAuthor(savedAuthor);

             System.out.println("Author did not exist in the database. New author created.");
         } else {
             book.setAuthor(existingAuthor);
             System.out.println("Author already exists. Using existing author.");
         }

        bookRepository.createBook(book);
    }  
}
