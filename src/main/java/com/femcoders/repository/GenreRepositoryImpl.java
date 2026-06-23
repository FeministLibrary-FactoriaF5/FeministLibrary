package com.femcoders.repository;

import com.femcoders.config.DBManager;
import com.femcoders.model.Genre;
import com.femcoders.view.Colors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class GenreRepositoryImpl implements GenreRepository {

    Connection connection;
    PreparedStatement statement;

    @Override
    public Genre createGenre(Genre genre) {
        String sql = "INSERT INTO genres (name) VALUES (LOWER(?))";

        try {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, genre.getName());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()) {
                genre.setId(generatedKeys.getInt(1));
            }

            // System.out.println(Colors.GREEN + "✅ Genre created successfully." + Colors.RESET);

        } catch (Exception e) {
            System.out.println(Colors.RED + "❌ Genre creation failed: " + e.getMessage() + Colors.RESET);

        } finally {
            DBManager.closeConnection();
        }

        return genre;
    }

    @Override
    public Genre readGenreByName(String name) {
        String sql = "SELECT id, name FROM genres WHERE LOWER(name) = LOWER(?)";

        try {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);

            ResultSet resultSetGenre = statement.executeQuery();

            if(resultSetGenre.next()) {
                Genre genre = new Genre(
                        resultSetGenre.getInt("id"),
                        resultSetGenre.getString("name"));

                return genre;
            }

        } catch (Exception e) {
            System.out.println(Colors.RED + "❌ Error reading genre by name: " + e.getMessage() + Colors.RESET);

        } finally {
            DBManager.closeConnection();
        }

        return null;
    }

    @Override
    public Genre validateExistingGenre(String name) {
        Genre existingGenre = readGenreByName(name);

        if (existingGenre == null) {
            Genre newGenre = new Genre();
            newGenre.setName(name);
            Genre savedGenre = createGenre(newGenre);
            System.out.println(Colors.GREEN + "✅ Genre not found. A new genre has been created." + Colors.RESET);
            return savedGenre;
        }

        System.out.println(Colors.GREEN + "✅ Genre found: " + existingGenre.getName() + Colors.RESET);
        return existingGenre;
    }
}