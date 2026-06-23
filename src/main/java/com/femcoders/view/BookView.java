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

    public void createBook(Scanner scanner){
        System.out.println("\n" + Colors.PURPLE + Colors.BOLD + "═══════════════════════════════════════" + Colors.RESET);
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

        /*Publisher publisher = new Publisher();
        publisher.setName(publisherName);*/

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