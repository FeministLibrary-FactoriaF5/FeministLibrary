package com.femcoders.repository;

import com.femcoders.model.Genre;

import java.util.List;

public interface GenreRepository {
    //CREATE
    Genre createGenre(Genre genre);

    //READ
    Genre readGenreByName(String name);
    Genre validateExistingGenre(String name);
    List<Genre> readGenresForBook(int bookId);
}