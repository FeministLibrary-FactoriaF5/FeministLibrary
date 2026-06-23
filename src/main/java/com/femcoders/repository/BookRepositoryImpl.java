package com.femcoders.repository;

import com.femcoders.config.DBManager;
import com.femcoders.model.Book;
import com.femcoders.model.Genre;
import com.femcoders.view.Colors;

import java.sql.*;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    Connection connection;
    PreparedStatement statement;

    @Override
    public void createBook(Book book) {
        String sql = "INSERT INTO books (title, author_id, publisher_id, isbn, published_year, summary, format) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?::book_format)";

        try {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getAuthor().getId());

            if (book.getPublisher() != null) {
                statement.setInt(3, book.getPublisher().getId());
            } else {
                statement.setNull(3, Types.INTEGER);
            }

            statement.setString(4, book.getIsbn());

            if (book.getPublishedYear() != null) {
                statement.setInt(5, book.getPublishedYear());
            } else {
                statement.setNull(5, Types.SMALLINT);
            }

            statement.setString(6, book.getSummary());
            statement.setString(7, book.getFormat().name().toLowerCase());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newId = generatedKeys.getInt(1);
                book.setId(newId);
            }

            insertGenresForBook(book);

            System.out.println(Colors.GREEN + "✅ Book created successfully." + Colors.RESET);

        } catch (Exception e) {
            System.out.println(Colors.RED + "❌ Book creation failed.: " + e.getMessage() + Colors.RESET);

        } finally {
            DBManager.closeConnection();
        }
    }

    private void insertGenresForBook(Book book) throws SQLException {
        String genreSql = "INSERT INTO genre_book (book_id, genre_id) VALUES (?, ?)";
        PreparedStatement genreStatement = connection.prepareStatement(genreSql);

        for (Genre genre : book.getGenres()) {
            genreStatement.setInt(1, book.getId());
            genreStatement.setInt(2, genre.getId());
            genreStatement.executeUpdate();
        }
    }
    
    @Override
    public Boolean validateExistingIsbn(String isbn) {
        Book existingIsbn = readBookByIsbn(isbn);

        if (existingIsbn == null) {
            return false;
        }
        return true;
    }

    private Book readBookByIsbn(String isbn) {
        String sql = "SELECT id, isbn FROM books WHERE isbn = ?";

        try {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, isbn);

            ResultSet resultSetIsbn = statement.executeQuery();

            if (resultSetIsbn.next()) {
                Book book = new Book(
                        resultSetIsbn.getInt("id"),
                        resultSetIsbn.getString("isbn"));

                return book;
            }

        } catch (Exception e) {
            System.out.println(Colors.RED + "❌ Error reading book by ISBN: " + e.getMessage() + Colors.RESET);

        } finally {
            DBManager.closeConnection();
        }

        return null;
    }

    @Override
    public Book readBookById(int id) {
        return null;
    }

    @Override
    public List<Book> readAllBooks() {
        return List.of();
    }

    @Override
    public List<Book> readBooksByTitle(String title) {
        return List.of();
    }

    @Override
    public List<Book> readBooksByAuthor(String authorName) {
        return List.of();
    }

    @Override
    public List<Book> readBooksByGenre(String genre) {
        return List.of();
    }

    @Override
    public void updateBook(Book book) {
    }

    @Override
    public void deleteBook(int id) {
    }
}