package com.femcoders.repository;

import com.femcoders.config.DBManager;
import com.femcoders.model.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AuthorRepositoryImpl implements AuthorRepository {

    Connection connection;
    PreparedStatement statement;

    @Override
    public Author createAuthor(Author author) {
        String sql = "INSERT INTO authors (name) VALUES (?)";

        try {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, author.getName());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()) {
                author.setId(generatedKeys.getInt(1));
            }

            System.out.println("Author created successfully.");

        } catch (Exception e) {
            System.out.println("Author creation failed.");
            System.out.println(e.getMessage());

        } finally {
            DBManager.closeConnection();
        }
        return author;
    }

    @Override
    public Author readAuthorByName(String name) {
        return null;
    }
}
