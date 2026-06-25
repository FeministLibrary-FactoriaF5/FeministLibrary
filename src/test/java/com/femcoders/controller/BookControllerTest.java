package com.femcoders.controller;

import com.femcoders.model.Author;
import com.femcoders.model.Book;
import com.femcoders.model.Genre;
import com.femcoders.repository.AuthorRepository;
import com.femcoders.repository.BookRepository;
import com.femcoders.repository.GenreRepository;
import com.femcoders.repository.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BookControllerTest {
    @Test
    void createBook_callsRepositoriesCorrectly() {
        BookRepository mockBookRepository = Mockito.mock(BookRepository.class);
        AuthorRepository mockAuthorRepository = Mockito.mock(AuthorRepository.class);
        GenreRepository mockGenreRepository = Mockito.mock(GenreRepository.class);
        PublisherRepository mockPublisherRepository = Mockito.mock(PublisherRepository.class);

        BookController controller = new BookController(
                mockBookRepository,
                mockAuthorRepository,
                mockPublisherRepository,
                mockGenreRepository
        );

        Book book = new Book();
        book.setIsbn("9780306406157");

        Author inputAuthor = new Author();
        inputAuthor.setName("John Doe");
        book.setAuthor(inputAuthor);

        Author validatedAuthor = new Author();
        validatedAuthor.setId(1);
        validatedAuthor.setName("John Doe");

        Mockito.when(mockAuthorRepository.validateExistingAuthor("John Doe"))
                .thenReturn(validatedAuthor);

        controller.createBook(book);

        Mockito.verify(mockAuthorRepository).validateExistingAuthor("John Doe");
        Mockito.verify(mockBookRepository).createBook(book);

        assert book.getAuthor() == validatedAuthor;
    }

}
