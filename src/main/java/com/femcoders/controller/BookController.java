package com.femcoders.controller;

import com.femcoders.model.Author;
import com.femcoders.model.Book;
import com.femcoders.model.Genre;
import com.femcoders.model.Publisher;
import com.femcoders.repository.AuthorRepository;
import com.femcoders.repository.BookRepository;
import com.femcoders.repository.GenreRepository;
import com.femcoders.repository.PublisherRepository;
import com.femcoders.view.Colors;

import java.util.ArrayList;
import java.util.List;

public class BookController {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private PublisherRepository publisherRepository;
    private GenreRepository genreRepository;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository,
            PublisherRepository publisherRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.genreRepository = genreRepository;
    }

    public void createBook(Book book) {
        if (!book.getIsbn().matches("\\d{13}")) {
            System.out.println(Colors.RED + "❌ Invalid ISBN. It must contain exactly 13 numeric digits." + Colors.RESET);
            return;
        }

        if (bookRepository.validateExistingIsbn(book.getIsbn())) {
            System.out.println(Colors.RED + "❌ This ISBN already exists. This book can not be added." + Colors.RESET);
            return;
        }

        Author author = authorRepository.validateExistingAuthor(book.getAuthor().getName());
        book.setAuthor(author);

        if (book.getPublisher() != null) {
            Publisher publisher = publisherRepository.validateExistingPublisher(book.getPublisher().getName());
            book.setPublisher(publisher);
        }

        List<Genre> genres = new ArrayList<>();
        for (Genre genre : book.getGenres()){
            Genre validated = genreRepository.validateExistingGenre(genre.getName());
            genres.add(validated);
        }
        book.setGenres(genres);

        bookRepository.createBook(book);
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
        Book book=bookRepository.readBookById(id);

        Author author = authorRepository.findById(book.getAuthorId());

        Publisher publisher = publisherRepository.findById(book.getPublisherId());

        List<Genre> genres = genreRepository.readGenresForBook(book.getId());

        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setGenres(genres);

        return book;
    }

    public void updateBookById (int id, Book updatedBook) {

    }
}