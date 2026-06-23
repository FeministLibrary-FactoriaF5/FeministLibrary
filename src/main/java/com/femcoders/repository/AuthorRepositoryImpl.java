package com.femcoders.repository;

import com.femcoders.config.DBManager;
import com.femcoders.model.Author;
import com.femcoders.view.Colors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AuthorRepositoryImpl implements AuthorRepository {

    Connection connection;
    PreparedStatement statement;

    @Override
    public Author createAuthor(Author author) {
        String sql = "INSERT INTO authors (name) VALUES (LOWER(?))";

        try {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, author.getName());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                author.setId(generatedKeys.getInt(1));
            }

            // System.out.println(Colors.GREEN + "✅ Author created successfully." + Colors.RESET);

        } catch (Exception e) {
            System.out.println(Colors.RED + "❌ Author creation failed: " + e.getMessage() + Colors.RESET);

        } finally {
            DBManager.closeConnection();
        }
        return author;
    }

    @Override
    public Author readAuthorByName(String name) {
        String sql = "SELECT id, name FROM authors WHERE LOWER(name) = LOWER(?)";

        try {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);

            ResultSet resultSetAuthors = statement.executeQuery();

            if (resultSetAuthors.next()) {
                Author author = new Author(
                        resultSetAuthors.getInt("id"),
                        resultSetAuthors.getString("name"));

                return author;
            }

        } catch (Exception e) {
            System.out.println(Colors.RED + "❌ Error reading author by name: " + e.getMessage() + Colors.RESET);

        } finally {
            DBManager.closeConnection();
        }

        return null;
    }

    @Override
    public Author validateExistingAuthor(String name) {
        Author existingAuthor = readAuthorByName(name);

        if (existingAuthor == null) {
            Author newAuthor = new Author();
            newAuthor.setName(name);
            Author savedAuthor = createAuthor(newAuthor);
            System.out.println(Colors.GREEN + "✅ Author not found. A new author has been created." + Colors.RESET);
            return savedAuthor;
        }

        System.out.println(Colors.GREEN + "✅ Author found: " + existingAuthor.getName() + Colors.RESET);
        return existingAuthor;
    }
    
    @Override
    public Author findById(int id) {

        String sql = "SELECT * FROM authors WHERE id = ?";

        try {
            Connection connection = DBManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                Author author = new Author();
                author.setId(rs.getInt("id"));
                author.setName(rs.getString("name"));

                return author;
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
}