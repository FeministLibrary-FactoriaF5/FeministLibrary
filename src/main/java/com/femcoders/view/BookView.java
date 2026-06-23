package com.femcoders.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.femcoders.controller.BookController;
import com.femcoders.model.*;

public class BookView {
    private BookController bookController;

    public BookView (BookController bookController){
        this.bookController = bookController;
    }

    public void createBook(Scanner scanner){
        System.out.println("===Create book===");

        System.out.println("Introduce title: ");
        String title = scanner.nextLine();

        System.out.println("Introduce author name:");
        String authorName = scanner.nextLine();

        System.out.println("Introduce Publisher:");
        String publisherName = scanner.nextLine();

        System.out.println("Introduce ISBN (13 characters): ");
        String isbn = scanner.nextLine();

        System.out.println("Introduce year of publishing: ");
        Integer publishedYear = Integer.parseInt(scanner.nextLine());

        System.out.println("Introduce summary: (max 200 characters) ");
        String summary = scanner.nextLine();

        System.out.println("Introduce format: (paperback / hardcover / ebook / audiobook) ");
        Format format = Format.valueOf(scanner.nextLine().toUpperCase());

        System.out.println("Introduce genre (separated by comma, ex: horror, satire): ");
        String genresInput = scanner.nextLine();
        List<Genre> genres = new ArrayList<>();
        for (String genreName : genresInput.split(",")) {
            genres.add(new Genre(null, genreName.trim()));
        }

        Author author = new Author();
        author.setName(authorName);

        Publisher publisher = new Publisher();
        publisher.setName(publisherName);

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setIsbn(isbn);
        book.setPublishedYear(publishedYear);
        book.setSummary(summary);
        book.setFormat(format);
        book.setGenres(genres);

        bookController.createBook(book);

    }
}