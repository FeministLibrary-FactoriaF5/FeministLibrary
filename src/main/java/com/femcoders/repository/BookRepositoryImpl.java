package com.femcoders.repository;

import com.femcoders.config.DBManager;
import com.femcoders.model.Book;
import com.femcoders.model.Format;
import com.femcoders.model.Genre;
import com.femcoders.view.Colors;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    Connection connection;
    PreparedStatement statement;

    @Override
    public void createBook(Book book) {
        String sql = "INSERT INTO books (title, author_id, publisher_id, isbn, published_year, summary, format) " + "VALUES (?, ?, ?, ?, ?, ?, ?::book_format)";

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

        } catch (Exception e) {
            System.out.println(Colors.RED + "❌ Book creation failed: " + e.getMessage() + Colors.RESET);

        } finally {
            DBManager.closeConnection();
        }
    }

    protected void insertGenresForBook(Book book) throws SQLException {
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
        Book book = new Book();

        String sql = "SELECT * FROM books WHERE id = ?";

        try {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setPublishedYear((Integer) resultSet.getObject("published_year"));
                book.setSummary(resultSet.getString("summary"));
                book.setFormat(Format.valueOf(resultSet.getString("format").toUpperCase()));

                book.setAuthorId(resultSet.getInt("author_id"));

                Integer publisherId = (Integer) resultSet.getObject("publisher_id");
                book.setPublisherId(publisherId);

                return book;
            }

        } catch (Exception e) {
            System.out.println(Colors.RED + "❌ Error reading book by ID: " + e.getMessage() + Colors.RESET);

        } finally {
            DBManager.closeConnection();
        }

        return null;
    }

    @Override
    public List<Book> readAllBooks() {
        List<Book> booksList = new ArrayList<>();
            
        String sql = "SELECT * FROM books";

        try {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(sql);
            
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setPublishedYear(resultSet.getInt("published_year"));
                book.setFormat(Format.valueOf(resultSet.getString("format").toUpperCase()));

                book.setAuthorId(resultSet.getInt("author_id"));
                book.setPublisherId(resultSet.getInt("publisher_id"));
                
                booksList.add(book);
            }

        } catch (Exception e) {
            System.out.println(Colors.RED + "❌ Error reading books: " + e.getMessage() + Colors.RESET);

        } finally {
            DBManager.closeConnection();
        }
        
        return booksList;
    }

    @Override
    public List<Book> readBooksByTitle(String title) {
        List<Book> booksList = new ArrayList<>();
            
        String sql = "SELECT * FROM books WHERE LOWER(title) = LOWER(?)";

        try {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, title);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setPublishedYear(resultSet.getInt("published_year"));
                book.setSummary(resultSet.getString("summary"));
                book.setFormat(Format.valueOf(resultSet.getString("format").toUpperCase()));

                book.setAuthorId(resultSet.getInt("author_id"));
                book.setPublisherId(resultSet.getInt("publisher_id"));
                
                booksList.add(book);
            }

        } catch (Exception e) {
            System.out.println(Colors.RED + "❌ Error reading books by title: " + e.getMessage() + Colors.RESET);

        } finally {
            DBManager.closeConnection();
        }
        
        return booksList;
    }

    @Override
    public void updateBookById(int id, Book updatedBook) {
        Book existingBook = readBookById(id);

        if (existingBook == null) {
            System.out.println(Colors.RED + "❌ No book found with ID: " + id + Colors.RESET);
            return;
        }

        String oldIsbn = existingBook.getIsbn();
        String newIsbn = updatedBook.getIsbn();

        if (newIsbn != null && !newIsbn.equals(oldIsbn)) {
            boolean isbnExists = validateExistingIsbn(newIsbn);

            if (isbnExists) {
                System.out.println(Colors.RED + "❌ Cannot update: ISBN '" + newIsbn + "' already exists." + Colors.RESET);
                return;
            }
        }

        String sql = "UPDATE books SET title = ?, author_id = ?, publisher_id = ?, isbn = ?, published_year = ?, " +
                "summary = ?, format = ?::book_format WHERE id = ?";
        try {
            connection = DBManager.getConnection();

            // --- UPDATE BOOK ---
            statement = connection.prepareStatement(sql);

            statement.setString(1, updatedBook.getTitle());
            statement.setInt(2,updatedBook.getAuthor().getId());

            if (updatedBook.getPublisher() != null) {
                statement.setInt(3, updatedBook.getPublisher().getId());
            } else {
                statement.setNull(3, Types.INTEGER);
            }

            statement.setString(4,updatedBook.getIsbn());

            if (updatedBook.getPublishedYear() != null) {
                statement.setInt(5, updatedBook.getPublishedYear());
            } else {
                statement.setNull(5, Types.SMALLINT);
            }

            statement.setString(6,updatedBook.getSummary());
            statement.setString(7,updatedBook.getFormat().name().toLowerCase());

            statement.setInt(8,id);

            statement.executeUpdate();

            String deleteSql = "DELETE FROM genre_book WHERE book_id = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();

            String insertSql = "INSERT INTO genre_book (book_id, genre_id) VALUES (?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertSql);

            for (Genre genre : updatedBook.getGenres()) {
                insertStatement.setInt(1, id);
                insertStatement.setInt(2, genre.getId());
                insertStatement.executeUpdate();
            }

            System.out.println(Colors.GREEN + "✅ Book updated successfully." + Colors.RESET);

        } catch (Exception e) {
            System.out.println(Colors.RED + "❌ Book update failed: " + e.getMessage() + Colors.RESET);

        } finally {
            DBManager.closeConnection();
        }
    }

    @Override
    public void deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(Colors.RED + "❌ Book deletion failed: " + e.getMessage() + Colors.RESET);
        } finally {
            DBManager.closeConnection();
        }
    }
}