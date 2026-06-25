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
        System.out.println();
        System.out.println(Colors.PURPLE + Colors.BOLD + "═══════════════════════════════════════" + Colors.RESET);
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
            System.out.print(Colors.CYAN + "Format (paperback / hardcover / ebook / audiobook): " + Colors.RESET);
            try {
                format = Format.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println(Colors.RED + "❌ Invalid format. Please choose one of: paperback, hardcover, ebook, audiobook." + Colors.RESET);
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

        String error = bookController.createBook(book);
        if (error != null) {
            System.out.println(Colors.RED + "❌ " + error + Colors.RESET);
        } else {
            System.out.println(Colors.GREEN + "✅ Book created successfully." + Colors.RESET);
        }
    }

    public void deleteBook(Scanner scanner) {
        System.out.println();
        System.out.println(Colors.PURPLE + Colors.BOLD + "═══════════════════════════════════════" + Colors.RESET);
        System.out.println(Colors.PURPLE + Colors.BOLD + "          🗑️  DELETE A BOOK" + Colors.RESET);
        System.out.println(Colors.PURPLE + Colors.BOLD + "═══════════════════════════════════════" + Colors.RESET);

        System.out.print(Colors.CYAN + "Enter book ID: " + Colors.RESET);
        int id = Integer.parseInt(scanner.nextLine());

        Book book = bookController.readBooksById(id);

        if (book == null) {
            System.out.println(Colors.RED + "❌ No book found with ID: " + id + Colors.RESET);
            return;
        }

        System.out.println();
        System.out.println(Colors.YELLOW + "Are you sure you want to delete this book?" + Colors.RESET);
        System.out.println(Colors.YELLOW + "ID: " + book.getId() + " - Title: " + book.getTitle() + Colors.RESET);
        System.out.print(Colors.YELLOW + "Type 'yes' to confirm or 'no' to cancel: " + Colors.RESET);

        String confirmation = scanner.nextLine().trim().toLowerCase();

        switch (confirmation) {
            case "yes" -> {
                bookController.deleteBook(id);
                System.out.println(Colors.GREEN + "✅ Book deleted successfully." + Colors.RESET);
            }
            case "no" -> System.out.println(Colors.CYAN + "Delete cancelled. Returning to menu." + Colors.RESET);
            default -> System.out.println(Colors.RED + "❌ Invalid option. Returning to menu." + Colors.RESET);
        }
    }

    public void searchAllBooks() {
        System.out.println();
        System.out.println(Colors.PURPLE + Colors.BOLD + "═══════════════════════════════════════" + Colors.RESET);
        System.out.println(Colors.PURPLE + Colors.BOLD + "          📖 ALL BOOKS" + Colors.RESET);
        System.out.println(Colors.PURPLE + Colors.BOLD + "═══════════════════════════════════════" + Colors.RESET);

        List<Book> booksList = bookController.readAllBooks();

        if (booksList.isEmpty()) {
            System.out.println(Colors.RED + "❌ No books found in database." + Colors.RESET);
            return;
        }

        System.out.println();
        System.out.println(Colors.GREEN + "Books found: " + booksList.size() + Colors.RESET);
        printBooks(booksList, false);
    }

    public void searchByTitle(Scanner scanner) {
        System.out.println();
        System.out.println(Colors.PURPLE + Colors.BOLD + "═══════════════════════════════════════" + Colors.RESET);
        System.out.println(Colors.PURPLE + Colors.BOLD + "       🔍 SEARCH BOOK BY TITLE" + Colors.RESET);
        System.out.println(Colors.PURPLE + Colors.BOLD + "═══════════════════════════════════════" + Colors.RESET);

        System.out.print(Colors.CYAN + "Enter the exact title: " + Colors.RESET);
        List<Book> booksList = bookController.readBooksByTitle(scanner.nextLine());

        if (booksList.isEmpty()) {
            System.out.println(Colors.RED + "❌ No books found with that title." + Colors.RESET);
            return;
        }

        System.out.println();
        System.out.println(Colors.GREEN + "Books found: " + booksList.size() + Colors.RESET);
        printBooks(booksList, true);
    }

    public void searchById(Scanner scanner) {
        System.out.println();
        System.out.println(Colors.PURPLE + Colors.BOLD + "═══════════════════════════════════════" + Colors.RESET);
        System.out.println(Colors.PURPLE + Colors.BOLD + "         🔍 SEARCH BOOK BY ID" + Colors.RESET);
        System.out.println(Colors.PURPLE + Colors.BOLD + "═══════════════════════════════════════" + Colors.RESET);

        System.out.print(Colors.CYAN + "Enter book ID: " + Colors.RESET);
        Book book = bookController.readBooksById(Integer.parseInt(scanner.nextLine()));

        if (book == null) {
            System.out.println(Colors.RED + "❌ No book found with that ID." + Colors.RESET);
            return;
        }

        System.out.println(Colors.GREEN + "Book found:" + Colors.RESET);
        printBooks(List.of(book), true);
    }

    private void printBooks(List<Book> books, boolean showSummary) {
        for (Book book : books) {
            System.out.println();
            System.out.println(Colors.PURPLE + "================ BOOK ================" + Colors.RESET);
            System.out.println(Colors.CYAN + "ID:        " + Colors.RESET + book.getId());
            System.out.println(Colors.CYAN + "Title:     " + Colors.RESET + book.getTitle());
            System.out.println(Colors.CYAN + "Author:    " + Colors.RESET + (book.getAuthor() != null ? book.getAuthor().getName() : "N/A"));
            System.out.println(Colors.CYAN + "Publisher: " + Colors.RESET + (book.getPublisher() != null ? book.getPublisher().getName() : "N/A"));
            System.out.println(Colors.CYAN + "ISBN:      " + Colors.RESET + book.getIsbn());
            System.out.println(Colors.CYAN + "Year:      " + Colors.RESET + book.getPublishedYear());
            System.out.println(Colors.CYAN + "Format:    " + Colors.RESET + book.getFormat());

            if (showSummary) {
                System.out.println(Colors.CYAN + "Summary:   " + Colors.RESET + book.getSummary());
            }

            System.out.print(Colors.CYAN + "Genres:    " + Colors.RESET);
            List<Genre> genres = book.getGenres();
            for (int i = 0; i < genres.size(); i++) {
                System.out.print(genres.get(i).getName());
                if (i < genres.size() - 1) System.out.print(", ");
            }
            System.out.println("\n" + Colors.PURPLE + "======================================" + Colors.RESET);
        }
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