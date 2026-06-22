package com.femcoders.controller;

import com.femcoders.model.Author;
import com.femcoders.repository.AuthorRepository;

public class AuthorController {
    private AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void createAuthor(Author author){
        authorRepository.createAuthor(author);
    }
}
