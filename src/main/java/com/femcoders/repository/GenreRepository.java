package com.femcoders.repository;

import com.femcoders.model.Genre;

public interface GenreRepository {
    //CREATE
    Genre createGenre(Genre genre);

    //READ
    Genre readGenreByName(String name);
    Genre validadeExistingGenre(String name);
}
