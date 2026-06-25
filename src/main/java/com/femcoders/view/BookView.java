package com.femcoders.view;

import java.awt.*;
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
        System.out
                .println("\n" + Colors.PURPLE + Colors.BOLD + "═══════════════════════════════════════" + Colors.RESET);
        System.out.println(Colors.PURPLE + Colors.BOLD + "          ➕ ADD A NEW BOOK" + Colors.RESET);
        System.out.println(Colors.PURPLE + Colors.BOLD + "═══════════════════════════════════════" + Colors.RESET);

        System.out.print(Colors.CYAN + "Title: " + Colors.RESET);
        String title = scanner.nextLine();

        System.out.print(Colors.CYAN + "Author name: " + Colors.RESET);
        String authorName = scanner.nextLine();

        System.out.print(Colors.CYAN + "Publisher (press Enter to skip): " + Colors.RESET);
        String publisherName = scanner.nextLine();

        System.out.print(Colors.CYAN + "ISBN (13 digits): " + Colors.RESET);
        String isbn = scanner.nextLine();

        System.out.print(Colors.CYAN + "Year of publication: " + Colors.RESET);
        Integer publishedYear = Integer.parseInt(scanner.nextLine());

        System.out.print(Colors.CYAN + "Summary (max. 200 characters): " + Colors.RESET);
        String summary = scanner.nextLine();

        Format format = null;
        while (format == null) {
            System.out.println(Colors.CYAN + "Format (paperback / hardcover / ebook / audiobook): " + Colors.RESET);
            String formatInput = scanner.nextLine();
            try {
                format = Format.valueOf(formatInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println(
                        Colors.RED + "❌ Invalid format. Please choose one of: paperback, hardcover, ebook, audiobook."
                                + Colors.RESET);
            }
        }

        System.out.print(Colors.CYAN + "Genres (separated by commas, e.g: horror, satire): " + Colors.RESET);
        String genresInput = scanner.nextLine();
        List<Genre> genres = new ArrayList<>();
        for (String genreName : genresInput.split(",")) {
            genres.add(new Genre(null, genreName.trim()));
        }

        Author author = new Author();
        author.setName(authorName);

        Publisher publisher = null;
        if (!publisherName.trim().isEmpty()) {
            publisher = new Publisher();
            publisher.setName(publisherName.trim());
        }

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

            System.out.println("\n================ BOOK ================");
            System.out.println("ID:            " + book.getId());
            System.out.println("Title:         " + book.getTitle());
            System.out.println("Author:        " + (book.getAuthor() != null ? book.getAuthor().getName() : "N/A"));
            System.out.println("Publisher:     " + (book.getPublisher() != null ? book.getPublisher().getName() : "N/A"));
            System.out.println("ISBN:          " + book.getIsbn());
            System.out.println("Year:          " + book.getPublishedYear());
            System.out.println("Format:        " + book.getFormat());
            System.out.println("Summary:       " + book.getSummary());

            System.out.print("Genres:        ");
            List<Genre> genres = book.getGenres();
            for (int i = 0; i < genres.size(); i++) {
                System.out.print(genres.get(i).getName());
                if (i < genres.size() - 1) {
                    System.out.print(", ");
                }
            }

            System.out.println("\n======================================\n");
        }
    }

    public void searchById(Scanner scanner) {
        System.out.println("=== Search book by id ===");

        Book book = bookController.readBooksById(scanner.nextInt());

        if (book == null) {
            System.out.println("No book found with that id.");
            return;
        }

        System.out.println("Book Found:");

        System.out.println("\n================ BOOK ================");
        System.out.println("ID:            " + book.getId());
        System.out.println("Title:         " + book.getTitle());
        System.out.println("Author:        " + (book.getAuthor() != null ? book.getAuthor().getName() : "N/A"));
        System.out.println("Publisher:     " + (book.getPublisher() != null ? book.getPublisher().getName() : "N/A"));
        System.out.println("ISBN:          " + book.getIsbn());
        System.out.println("Year:          " + book.getPublishedYear());
        System.out.println("Format:        " + book.getFormat());
        System.out.println("Summary:       " + book.getSummary());

        System.out.print("Genres:        ");
        List<Genre> genres = book.getGenres();
        for (int i = 0; i < genres.size(); i++) {
            System.out.print(genres.get(i).getName());
            if (i < genres.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("\n======================================\n");

    }

    public void updateBookById(Scanner scanner) {
        System.out.println("=== UPDATE BOOK BY ID ===");
        System.out.print("Enter book ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        Book existingBook = bookController.readBooksById(id);

        if (existingBook == null) {
            System.out.println(Colors.RED + "No book found with that id." + Colors.RESET);
            return;
        }

        System.out.println("\nLeave fields empty to keep current values.\n");

        // --- TITLE ---
        System.out.println("Current title: " + existingBook.getTitle());
        System.out.print("New title (Press Enter to keep current): ");
        String newTitle = scanner.nextLine();
        String finalTitle = newTitle.isEmpty() ? existingBook.getTitle() : newTitle;

        // --- AUTHOR ---
        System.out.println("Current author: " + existingBook.getAuthor().getName());
        System.out.print("New author (Press Enter to keep current): ");
        String newAuthor = scanner.nextLine();
        String finalAuthorName = newAuthor.isEmpty() ? existingBook.getAuthor().getName() : newAuthor;

        // --- PUBLISHER ---
        System.out.println("Current publisher: " +
                (existingBook.getPublisher() != null ? existingBook.getPublisher().getName() : "N/A"));
        System.out.print("New publisher (Press Enter to keep / 'none' to remove): ");
        String newPublisher = scanner.nextLine();

        Publisher finalPublisher = null;

        if (newPublisher.isEmpty()) {
            finalPublisher = existingBook.getPublisher(); // mantener
        } else if (newPublisher.equalsIgnoreCase("none")) {
            finalPublisher = null; // eliminar publisher
        } else {
            finalPublisher = new Publisher(null, newPublisher.trim()); // crear uno nuevo
        }

        // --- ISBN ---
        System.out.println("Current ISBN: " + existingBook.getIsbn());
        System.out.print("New ISBN (Press Enter to keep current): ");
        String newIsbn = scanner.nextLine();
        String finalIsbn = newIsbn.isEmpty() ? existingBook.getIsbn() : newIsbn;

        // --- YEAR ---
        System.out.println("Current year: " + existingBook.getPublishedYear());
        System.out.print("New year (Press Enter to keep current): ");
        String newYear = scanner.nextLine();
        Integer finalYear = newYear.isEmpty() ? existingBook.getPublishedYear() : Integer.parseInt(newYear);

        // --- FORMAT ---
        System.out.println("Current format: " + existingBook.getFormat());
        System.out.print("New format (Press Enter to keep current): ");
        String newFormat = scanner.nextLine();
        Format finalFormat = newFormat.isEmpty()
                ? existingBook.getFormat()
                : Format.valueOf(newFormat.toUpperCase());

        // --- SUMMARY ---
        System.out.println("Current summary: " + existingBook.getSummary());
        System.out.print("New summary (Press Enter to keep current): ");
        String newSummary = scanner.nextLine();
        String finalSummary = newSummary.isEmpty() ? existingBook.getSummary() : newSummary;

        // --- GENRES ---
        System.out.print("Current genres: ");
        for (Genre g : existingBook.getGenres()) {
            System.out.print(g.getName() + " ");
        }
        System.out.print("\nNew genres (comma separated, Press Enter to keep current): ");
        String newGenres = scanner.nextLine();

        List<Genre> finalGenres = new ArrayList<>();
        if (newGenres.isEmpty()) {
            finalGenres = existingBook.getGenres(); // mantener
        } else {
            for (String g : newGenres.split(",")) {
                finalGenres.add(new Genre(null, g.trim()));
            }
        }

        // --- BUILD UPDATED BOOK ---
        Book updatedBook = new Book();
        updatedBook.setTitle(finalTitle);

        Author authorObj = new Author();
        authorObj.setName(finalAuthorName);
        updatedBook.setAuthor(authorObj);

        updatedBook.setPublisher(finalPublisher);
        updatedBook.setIsbn(finalIsbn);
        updatedBook.setPublishedYear(finalYear);
        updatedBook.setSummary(finalSummary);
        updatedBook.setFormat(finalFormat);
        updatedBook.setGenres(finalGenres);

        // --- SEND TO CONTROLLER ---
        bookController.updateBookById(id, updatedBook);
    }

}