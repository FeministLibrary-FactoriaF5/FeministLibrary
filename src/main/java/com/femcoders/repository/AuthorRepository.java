package com.femcoders.repository;

import com.femcoders.model.Author;

public interface AuthorRepository {
    //CREATE
    Author createAuthor(Author author);

    // READ
    Author readAuthorByName(String name);
    Author validateExistingAuthor(String name);

    Author findById(int id);

}