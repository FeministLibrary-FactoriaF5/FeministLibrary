package com.femcoders.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.femcoders.controller.BookController;
import com.femcoders.model.*;

public class BookView {
    private BookController bookController;

    public BookView(BookController bookController) {
        this.bookController = bookController;
    }

    public void createBook(Scanner scanner) {
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

        Format format = null;
        while (format == null) {
            System.out.println("Introduce format (paperback / hardcover / ebook / audiobook): ");
            String formatInput = scanner.nextLine();
            try {
                format = Format.valueOf(formatInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid format. Please choose one of: paperback, hardcover, ebook, audiobook.");
            }
        }

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

    public void searchByTitle(Scanner scanner) {
        System.out.println("=== Search book by exact title ===");
        System.out.println("Introduce the exact title: ");

        List<Book> booksList = bookController.readBooksByTitle(scanner.nextLine());

        if (booksList.isEmpty()) {
            System.out.println("No books found with that exact title.");
            return;
        }

        System.out.println("Books Found:");

        for (Book book : booksList) {

            // System.out.println("id:" + book.getId());
            // System.out.println("title:" + book.getTitle());
            System.out.println("\n================ BOOK ================");
            System.out.println("ID:            " + book.getId());
            System.out.println("Title:         " + book.getTitle());
            System.out.println("Author:        " + (book.getAuthor() != null ? book.getAuthor().getName() : "N/A"));
            System.out
                    .println("Publisher:     " + (book.getPublisher() != null ? book.getPublisher().getName() : "N/A"));
            System.out.println("ISBN:          " + book.getIsbn());
            System.out.println("Year:          " + book.getPublishedYear());
            System.out.println("Format:        " + book.getFormat());
            System.out.println("Summary:       " + book.getSummary());

            System.out.print("Genres:        ");
            if (book.getGenres() != null && !book.getGenres().isEmpty()) {
                for (Genre g : book.getGenres()) {
                    System.out.print(g.getName() + " ");
                }
            } else {
                System.out.print("N/A");
            }

            System.out.println("\n======================================\n");

        }
    }

}