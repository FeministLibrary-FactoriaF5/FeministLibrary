package com.femcoders.controller;

import com.femcoders.model.Author;
import com.femcoders.model.Book;
import com.femcoders.model.Genre;
import com.femcoders.model.Publisher;
import com.femcoders.repository.AuthorRepository;
import com.femcoders.repository.BookRepository;
import com.femcoders.repository.GenreRepository;
import com.femcoders.repository.PublisherRepository;

import java.util.ArrayList;
import java.util.List;

public class BookController {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private PublisherRepository publisherRepository;
    private GenreRepository genreRepository;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.genreRepository = genreRepository;
    }

    public String createBook(Book book) {
        if (!book.getIsbn().matches("\\d{13}")) {
            return "Invalid ISBN. It must contain exactly 13 numeric digits.";
        }

        if (bookRepository.validateExistingIsbn(book.getIsbn())) {
            return "This ISBN already exists. This book cannot be added.";
        }

        Author author = authorRepository.validateExistingAuthor(book.getAuthor().getName());
        book.setAuthor(author);

        if (book.getPublisher() != null) {
            Publisher publisher = publisherRepository.validateExistingPublisher(book.getPublisher().getName());
            book.setPublisher(publisher);
        }

        List<Genre> genres = new ArrayList<>();
        for (Genre genre : book.getGenres()) {
            Genre validated = genreRepository.validateExistingGenre(genre.getName());
            genres.add(validated);
        }
        book.setGenres(genres);

        bookRepository.createBook(book);
        return null;
    }

    public List<Book> readBooksByTitle(String title) {
        List<Book> books = bookRepository.readBooksByTitle(title);

        for (Book book : books) {
            Author author = authorRepository.findById(book.getAuthorId());
            Publisher publisher = publisherRepository.findById(book.getPublisherId());
            List<Genre> genres = genreRepository.readGenresForBook(book.getId());

            book.setAuthor(author);
            book.setPublisher(publisher);
            book.setGenres(genres);
        }
        return books;
    }

    public Book readBooksById(int id) {
        Book book = bookRepository.readBookById(id);

        if (book == null) {
            return null;
        }

        Author author = authorRepository.findById(book.getAuthorId());

        Publisher publisher = null;
        if (book.getPublisherId() != null) {
            publisher = publisherRepository.findById(book.getPublisherId());
        }

        List<Genre> genres = genreRepository.readGenresForBook(book.getId());

        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setGenres(genres);

        return book;
    }

    public void updateBookById(int id, Book updatedBook) {
        Author validatedAuthor = authorRepository.validateExistingAuthor(updatedBook.getAuthor().getName());
        updatedBook.setAuthor(validatedAuthor);

        if (updatedBook.getPublisher() != null) {
            Publisher validatedPublisher = publisherRepository.validateExistingPublisher(updatedBook.getPublisher().getName());
            updatedBook.setPublisher(validatedPublisher);
        }

        List<Genre> validatedGenres = new ArrayList<>();
        for (Genre genre : updatedBook.getGenres()) {
            Genre validated = genreRepository.validateExistingGenre(genre.getName());
            validatedGenres.add(validated);
        }
        updatedBook.setGenres(validatedGenres);

        bookRepository.updateBookById(id, updatedBook);
    }

    public void deleteBook(int id) {
        bookRepository.deleteBook(id);
    }

    public List<Book> readAllBooks() {
        List<Book> books = bookRepository.readAllBooks();

        for (Book book : books) {
            Author author = authorRepository.findById(book.getAuthorId());
            Publisher publisher = publisherRepository.findById(book.getPublisherId());
            List<Genre> genres = genreRepository.readGenresForBook(book.getId());

            book.setAuthor(author);
            book.setPublisher(publisher);
            book.setGenres(genres);
        }
        return books;
    }
}