package com.femcoders.repository;

import com.femcoders.config.DBManager;
import com.femcoders.model.Author;
import com.femcoders.model.Book;
import com.femcoders.model.Format;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;

class BookRepositoryImplTest {

    @Test
    void createBook_executesInsertSQLCorrectly() throws Exception {

        try (MockedStatic<DBManager> dbMock = Mockito.mockStatic(DBManager.class)) {

            Connection mockConnection = Mockito.mock(Connection.class);
            PreparedStatement mockStatement = Mockito.mock(PreparedStatement.class);
            ResultSet mockKeys = Mockito.mock(ResultSet.class);

            dbMock.when(DBManager::getConnection).thenReturn(mockConnection);

            Mockito.when(mockConnection.prepareStatement(
                    Mockito.anyString(),
                    Mockito.eq(Statement.RETURN_GENERATED_KEYS)
            )).thenReturn(mockStatement);

            Mockito.when(mockStatement.getGeneratedKeys()).thenReturn(mockKeys);
            Mockito.when(mockKeys.next()).thenReturn(true);
            Mockito.when(mockKeys.getInt(1)).thenReturn(99);

            Book book = new Book();
            book.setTitle("Test Title");

            Author author = new Author();
            author.setId(1);
            book.setAuthor(author);

            book.setPublisher(null);
            book.setIsbn("9780306406157");
            book.setPublishedYear(2020);
            book.setSummary("Summary");
            book.setFormat(Format.EBOOK);

            BookRepositoryImpl repo = Mockito.spy(new BookRepositoryImpl());
            Mockito.doNothing().when(repo).insertGenresForBook(Mockito.any());

            repo.createBook(book);

            Mockito.verify(mockConnection).prepareStatement(
                    Mockito.contains("INSERT INTO books"),
                    Mockito.eq(Statement.RETURN_GENERATED_KEYS)
            );

            Mockito.verify(mockStatement).setString(1, "Test Title");
            Mockito.verify(mockStatement).setInt(2, 1);
            Mockito.verify(mockStatement).setNull(3, Types.INTEGER);
            Mockito.verify(mockStatement).setString(4, "9780306406157");
            Mockito.verify(mockStatement).setInt(5, 2020);
            Mockito.verify(mockStatement).setString(6, "Summary");
            Mockito.verify(mockStatement).setString(7, "ebook");

            Mockito.verify(mockStatement).executeUpdate();

            assert book.getId() == 99;
        }
    }
}