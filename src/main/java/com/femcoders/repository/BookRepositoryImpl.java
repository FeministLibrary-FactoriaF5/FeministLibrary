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
        Book book = new Book();

        String sql = "SELECT * FROM books WHERE id = ?";

        System.out.println("Searching: " + id);

        try {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {     
                System.out.println("Encontrado: " + resultSet.getString("title"));

                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setPublishedYear(resultSet.getInt("published_year"));
                book.setSummary(resultSet.getString("summary"));
                book.setFormat(Format.valueOf(resultSet.getString("format").toUpperCase()));

                book.setAuthorId(resultSet.getInt("author_id"));
                book.setPublisherId(resultSet.getInt("publisher_id"));

                return book;
            }

        } catch (Exception e) {
            System.out.println("Error reading book by Title.");
            //System.out.println(e.getMessage());

            e.printStackTrace();

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
                //book.setSummary(resultSet.getString("summary"));
                book.setFormat(Format.valueOf(resultSet.getString("format").toUpperCase()));

                book.setAuthorId(resultSet.getInt("author_id"));
                book.setPublisherId(resultSet.getInt("publisher_id"));
                
                booksList.add(book);
            }

        } catch (Exception e) {
            System.out.println("Error reading list books.");
            //System.out.println(e.getMessage());

            e.printStackTrace();

        } finally {
            DBManager.closeConnection();
        }
        
        return booksList;
    }

    @Override
    public List<Book> readBooksByTitle(String title) {
        List<Book> booksList = new ArrayList<>();
            
        String sql = "SELECT * FROM books WHERE LOWER(title) = LOWER(?)";

        System.out.println("Searching: " + title);

        try {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, title);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println("Found: " + resultSet.getString("title"));

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
            System.out.println("Error reading book by Title.");
            //System.out.println(e.getMessage());

            e.printStackTrace();

        } finally {
            DBManager.closeConnection();
        }
        
        return booksList;
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