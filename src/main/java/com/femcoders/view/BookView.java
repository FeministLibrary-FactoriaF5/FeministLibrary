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
            System.out.println(Colors.CYAN + "Format (paperback / hardcover / ebook / audiobook): " + Colors.RESET);
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
}

    // revisar
        /* for (Book book : booksList) {

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
    } */